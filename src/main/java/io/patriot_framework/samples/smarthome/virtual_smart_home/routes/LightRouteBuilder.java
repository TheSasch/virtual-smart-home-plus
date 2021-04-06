package io.patriot_framework.samples.smarthome.virtual_smart_home.routes;

import io.patriot_framework.samples.smarthome.virtual_smart_home.house.HouseBean;
import io.patriot_framework.samples.smarthome.virtual_smart_home.house.Light;
import org.apache.camel.LoggingLevel;

public class LightRouteBuilder extends SwitchableDeviceRouteBuilder {

    public LightRouteBuilder() {
        super("light", Light.class.getSimpleName());
    }

    @Override
    public void configure() {
        HouseBean houseBean = HouseBean.getInstance();

        configureSwitchableDevice();

        from("direct:" + getEndpoint() + "-on")
                .bean(houseBean, "lightOn")
                .log(LoggingLevel.INFO, "Light (${header.label}): ON");

        from("direct:" + getEndpoint() + "-off")
                .bean(houseBean, "lightOff")
                .log(LoggingLevel.INFO, "Light (${header.label}): OFF");
    }
}
