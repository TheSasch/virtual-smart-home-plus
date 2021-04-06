package io.patriot_framework.samples.smarthome.virtual_smart_home.house;

public abstract class Device {

    private final String type;
    private final String label;

    public Device(Class<? extends Device> type, String label) {
        this.type = type.getSimpleName();
        this.label = label;
    }

    public String getType() {
        return type;
    }

    public String getLabel() {
        return label;
    }
}
