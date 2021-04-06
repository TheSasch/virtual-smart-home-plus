package io.patriot_framework.samples.smarthome.virtual_smart_home.routes;

import io.patriot_framework.samples.smarthome.virtual_smart_home.house.Fireplace;
import io.patriot_framework.samples.smarthome.virtual_smart_home.house.HouseBean;
import org.apache.camel.LoggingLevel;

public final class FireplaceRouteBuilder extends SwitchableDeviceRouteBuilder {

    public FireplaceRouteBuilder() {
        super("fireplace", Fireplace.class.getSimpleName());
    }

    @Override
    public void configure() {
        HouseBean houseBean = HouseBean.getInstance();

        configureSwitchableDevice();

        from("direct:" + getEndpoint() + "-on")
                .bean(houseBean, "fireplaceOn")
                .log(LoggingLevel.INFO, "Fireplace (${header.label}): BURNING");

        from("direct:" + getEndpoint() + "-off")
                .bean(houseBean, "fireplaceOff")
                .log(LoggingLevel.INFO, "Fireplace (${header.label}): EXTINGUISHED");
    }
}
