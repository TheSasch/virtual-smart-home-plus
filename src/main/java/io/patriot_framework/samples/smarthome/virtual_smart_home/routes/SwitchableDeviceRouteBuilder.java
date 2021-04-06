package io.patriot_framework.samples.smarthome.virtual_smart_home.routes;

public abstract class SwitchableDeviceRouteBuilder extends DeviceRouteBuilder {

    public SwitchableDeviceRouteBuilder(String endpoint, String type) {
        super(endpoint, type);
    }

    protected void configureSwitchableDevice() {
        configureDevice();
        configureSingle();
        configureAll();
    }

    private void configureSingle() {
        // Enable device by label.
        from(restBaseUri() + "/on?httpMethodRestrict=GET")
                .setBody(simple("${header.label}"))
                .to("direct:" + getEndpoint() + "-on")
                .to("direct:" + getEndpoint());

        // Disable device by label.
        from(restBaseUri() + "/off?httpMethodRestrict=GET")
                .setBody(simple("${header.label}"))
                .to("direct:" + getEndpoint() + "-off")
                .to("direct:" + getEndpoint());
    }

    private void configureAll() {
        // Enable all devices.
        from(restBaseUri() + "/on/all?httpMethodRestrict=GET")
                .setProperty("label", simple("all"))
                .setBody(simple("all"))
                .to("direct:" + getEndpoint() + "-on")
                .to("direct:" + getEndpoint() + "-all");

        // Disable all devices.
        from(restBaseUri() + "/off/all?httpMethodRestrict=GET")
                .setProperty("label", simple("all"))
                .setBody(simple("all"))
                .to("direct:" + getEndpoint() + "-off")
                .to("direct:" + getEndpoint() + "-all");
    }
}
