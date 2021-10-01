package io.patriot_framework.virtual_smart_home.route;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import io.patriot_framework.virtual_smart_home.house.device.Device;
import org.apache.camel.Exchange;
import org.apache.catalina.connector.Response;

public abstract class AbstractDeviceRoute extends HouseRoute {

    private String endpoint;
    private Class<? extends Device> deviceType;

    public void setupRoute(String endpoint, Class<? extends Device> deviceType) {
        this.endpoint = endpoint;;
        this.deviceType = deviceType;
    }

    @Override
    public void configure() {
        rest(getRoute())
                .get("{label}")
                    .produces("application/json")
                    .to("direct:read" + endpoint)

                .get()
                    .produces("application/json")
                    .to("direct:read" + endpoint + "s")

                .post()
                    .type(deviceType)
                    .consumes("application/json")
                    .to("direct:create" + endpoint)

                .put()
                    .type(deviceType)
                    .consumes("application/json")
                    .to("direct:update" + endpoint)

                .patch()
                    .type(deviceType)
                    .consumes("application/json")
                    .to("direct:update" + endpoint)

                .delete()
                    .to("direct:delete" + endpoint);

        onException(UnrecognizedPropertyException.class)
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(Response.SC_BAD_REQUEST))
                .handled(true);

        onException(JsonParseException.class)
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(Response.SC_BAD_REQUEST))
                .handled(true);

        handleGet();
        handlePost();
        handlePut();
        handleDelete();
    }

    private void handleGet() {
        from("direct:read" + endpoint)
                .routeId("read-" + endpoint + "-route")
                .process(exchange -> {
                    String label = exchange.getMessage().getHeader("label").toString();
                    Device retrievedDevice = house.getDevicesOfType(deviceType).get(label);
                    exchange.getMessage().setBody(retrievedDevice);

                    if (retrievedDevice == null) {
                        exchange.getMessage().setHeader(Exchange.HTTP_RESPONSE_CODE, Response.SC_NOT_FOUND); // 404
                    }
                })
                .endRest();

        from("direct:read" + endpoint + "s")
                .routeId("read-" + endpoint + "s-route")
                .process(exchange -> exchange.getMessage().setBody(house.getDevicesOfType(deviceType).values()))
                .endRest();
    }

    private void handlePost() {
        from("direct:create" + endpoint)
                .routeId("create-" + endpoint + "-route")
                .choice()
                    .when(body().isNotNull())
                        .process(exchange -> {
                            Device deviceToAdd = exchange.getMessage().getBody(deviceType);
                            Device checkForConflict = house.getDevicesOfType(deviceType).get(deviceToAdd.getLabel());

                            if (checkForConflict != null) {
                                exchange.getMessage().setHeader(Exchange.HTTP_RESPONSE_CODE, Response.SC_CONFLICT);
                                // 409
                                return;
                            }
                            exchange.getMessage().setHeader(Exchange.HTTP_RESPONSE_CODE, Response.SC_CREATED); // 201
                            house.addDevice(deviceToAdd.getLabel(), deviceToAdd);
                            exchange.getMessage().setHeader("label", deviceToAdd.getLabel());
                        })
                        .setBody(body()) // Respond with request body.
                        .choice()
                            .when(simple("${header.CamelHttpResponseCode} != 409"))
                                .log("Created route path for \"${header.label}\" device")
                        .endChoice()
                    .otherwise()
                        .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(Response.SC_BAD_REQUEST)) // 400
                .endChoice()
                .endRest();
    }

    private void handlePut() {
        from("direct:update" + endpoint)
                .routeId("update-" + endpoint + "-route")
                .choice()
                    .when(body().isNotNull())
                        .process(exchange -> {
                            Device deviceToUpdate = exchange.getMessage().getBody(deviceType);
                            Device checkIfExists = house.getDevicesOfType(deviceType).get(deviceToUpdate.getLabel());

                            if (checkIfExists == null) {
                                exchange.getMessage().setHeader(Exchange.HTTP_RESPONSE_CODE, Response.SC_NOT_FOUND);
                                // 404
                                return;
                            }
                            house.updateDevice(deviceToUpdate.getLabel(), deviceToUpdate);
                            exchange.getMessage().setHeader("label", deviceToUpdate.getLabel());
                        })
                        .setBody(body()) // Respond with request body.
                        .choice()
                            .when(simple("${header.CamelHttpResponseCode} != 404"))
                                .log("Updated fireplace \"${header.label}\"")
                        .endChoice()
                    .otherwise()
                        .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(Response.SC_BAD_REQUEST)) // 400
                .endChoice()
                .endRest();
    }

    private void handleDelete() {
        from("direct:delete" + endpoint)
                .routeId("delete-" + endpoint + "-route")
                .choice()
                    .when(header("label").isNotNull())
                        .process(exchange -> {
                            String label = exchange.getMessage().getHeader("label").toString();
                            Device deviceToDelete = house.getDevicesOfType(deviceType).get(label);

                            if (deviceToDelete == null) {
                                exchange.getMessage().setHeader(Exchange.HTTP_RESPONSE_CODE, Response.SC_NOT_FOUND);
                                // 404
                                return;
                            }
                            house.removeDevice(exchange.getMessage().getHeader("label").toString());
                        })
                        .choice()
                            .when(simple("${header.CamelHttpResponseCode} != 404"))
                                .log("Removed device \"${header.label}\"")
                        .endChoice()
                    .otherwise()
                        .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(Response.SC_BAD_REQUEST)) // 400
                .endChoice()
                .endRest();
    }

    @Override
    protected String getRoute() {
        return super.getRoute() + "device/" + endpoint;
    }
}
