package io.patriot_framework.virtualsmarthomeplus.house.devices;

public class Actuator extends Device implements Switchable {

    private boolean enabled = false;

    public Actuator(String label) {
        super(label);
    }

    @Override
    public void switchIt(boolean on) {
        enabled = on;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
