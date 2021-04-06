package io.patriot_framework.samples.smarthome.virtual_smart_home.routes;

import org.apache.camel.builder.RouteBuilder;

public abstract class IntelligentHouseRouteBuilder extends RouteBuilder {

    public String restBaseUri() {
        return "jetty:http://" + System.getProperty("iot.host", "0.0.0.0:8282");
    }
}
