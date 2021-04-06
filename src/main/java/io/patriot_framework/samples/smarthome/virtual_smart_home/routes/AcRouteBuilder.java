package io.patriot_framework.samples.smarthome.virtual_smart_home.routes;

import io.patriot_framework.samples.smarthome.virtual_smart_home.house.Ac;
import io.patriot_framework.samples.smarthome.virtual_smart_home.house.HouseBean;
import org.apache.camel.LoggingLevel;

public final class AcRouteBuilder extends SwitchableDeviceRouteBuilder {

    public AcRouteBuilder() {
        super("ac", Ac.class.getSimpleName());
    }

    @Override
    public void configure() {
        HouseBean houseBean = HouseBean.getInstance();

        configureSwitchableDevice();

        from("direct:" + getEndpoint() + "-on")
                .bean(houseBean, "acOn")
                .log(LoggingLevel.INFO, "Air conditioning (${header.label}): ON"); // TODO incorrect logs

        from("direct:" + getEndpoint() + "-off")
                .bean(houseBean, "acOff")
                .log(LoggingLevel.INFO, "Air conditioning (${header.label}): OFF"); // TODO incorrect logs
    }
}
