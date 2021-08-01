package io.patriot_framework.virtual_smart_home.route;

import org.springframework.stereotype.Component;

@Component
public class DeviceRoute extends HouseRoute {

    @Override
    public void configure() throws Exception {
        rest(getRoute())
                .get()
                    .produces("application/json")
                    .route()
                    .process(exchange -> exchange.getMessage().setBody(house.getDevices()))
                    .endRest();
    }

    @Override
    protected String getRoute() {
        return super.getRoute() + "device/";
    }
}
