package io.patriot_framework.samples.smarthome.virtual_smart_home.routes;

import io.patriot_framework.samples.smarthome.virtual_smart_home.house.HouseBean;
import org.apache.camel.model.dataformat.JsonLibrary;

public abstract class DeviceRouteBuilder extends IntelligentHouseRouteBuilder {

    private final String endpoint;
    private final String type;

    protected DeviceRouteBuilder(String endpoint, String type) {
        this.endpoint = endpoint;
        this.type = type;
    }

    protected void configureDevice() {
        configureView();
        configureJSON(type);
    }

    private void configureView() {
        // View device by label.
        from(restBaseUri() + "?httpMethodRestrict=GET")
                .setBody(simple("${header.label}"))
                .to("direct:" + endpoint);

        // View all devices.
        from(restBaseUri() + "/all?httpMethodRestrict=GET")
                .to("direct:" + endpoint + "-all");
    }

    private void configureJSON(String type) {
        HouseBean houseBean = HouseBean.getInstance();

        from("direct:" + endpoint)
                .setProperty("type", simple(type))
                .setProperty("label", simple("${header.label}"))
                .bean(houseBean, "objectInfo")
                .setHeader("content-type", constant("application/json"))
                .marshal().json(JsonLibrary.Jackson);

        from("direct:" + endpoint + "-all")
                .setProperty("type", simple(type))
                .setProperty("label", simple("all"))
                .bean(houseBean, "objectInfo")
                .setHeader("content-type", constant("application/json"))
                .marshal().json(JsonLibrary.Jackson);
    }

    public String getEndpoint() {
        return endpoint;
    }

    @Override
    public String restBaseUri() {
        return super.restBaseUri() + "/" + endpoint;
    }
}
