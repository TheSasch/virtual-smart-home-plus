package io.patriot_framework.samples.smarthome.virtual_smart_home.routes;

import io.patriot_framework.samples.smarthome.virtual_smart_home.house.HouseBean;
import org.apache.camel.LoggingLevel;
import org.apache.camel.model.dataformat.JsonLibrary;

public class HouseRouteBuilder extends IntelligentHouseRouteBuilder {

    @Override
    public void configure() {
        HouseBean houseBean = HouseBean.getInstance();

        from(restBaseUri() + "/house?httpMethodRestrict=GET")
                .to("direct:house");

        from(restBaseUri() + "/reset?httpMethodRestrict=GET")
                .setProperty("label", simple("all"))
                .setBody(simple("all"))
                .log(LoggingLevel.INFO, "Restarting...")
                .to("direct:reset");

        from("direct:reset")
                .to("direct:ac-off")
                .to("direct:fireplace-off")
                .to("direct:light-off")
                .to("direct:led-off")
                .to("direct:house");

        from("direct:house")
                .bean(houseBean, "houseView")
                .setHeader("content-type", constant("application/json"))
                .marshal().json(JsonLibrary.Jackson);
    }
}
