package io.patriot_framework.virtual_smart_home.house.device;

public class Actuator extends Device {

    private boolean enabled = false;

    public Actuator(String label) {
        super(label);
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
