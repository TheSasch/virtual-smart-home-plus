package io.patriot_framework.samples.smarthome.virtual_smart_home.routes;

import io.patriot_framework.samples.smarthome.virtual_smart_home.house.HouseBean;
import io.patriot_framework.samples.smarthome.virtual_smart_home.house.RGBLed;
import org.apache.camel.LoggingLevel;

public final class RGBLedRouteBuilder extends SwitchableDeviceRouteBuilder {

    public RGBLedRouteBuilder() {
        super("led", RGBLed.class.getSimpleName());
    }

    @Override
    public void configure() {
        HouseBean houseBean = HouseBean.getInstance();

        configureSwitchableDevice();

        from("direct:" + getEndpoint() + "-on")
                .bean(houseBean, "ledOn")
                .log(LoggingLevel.INFO, "RGB Led Light (${header.label}): ON");

        from("direct:" + getEndpoint() + "-off")
                .bean(houseBean, "ledOff")
                .log(LoggingLevel.INFO, "RGB Led Light (${header.label}): OFF");

        configureColors();

        from("direct:led-set")
                .bean(houseBean, "setLed")
                .to("direct:led");
    }

    private void configureColors() {
        from(restBaseUri() + "/setrgb?httpMethodRestrict=GET")
                .setBody(simple("${header.label}" +
                        ";${header.r}" +
                        ";${header.g}" +
                        ";${header.b}"))
                .to("direct:led-set");

        from(restBaseUri() + "/setrgb/all?httpMethodRestrict=GET")
                .setBody(simple("all" +
                        ";${header.r}" +
                        ";${header.g}" +
                        ";${header.b}"))
                .to("direct:led-set");
    }
}
