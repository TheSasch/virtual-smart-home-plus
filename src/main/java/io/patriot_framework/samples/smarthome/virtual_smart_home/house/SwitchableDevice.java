package io.patriot_framework.samples.smarthome.virtual_smart_home.house;

public abstract class SwitchableDevice extends Device implements Switchable {

    private boolean enabled = false;

    public SwitchableDevice(Class<? extends Device> type, String label) {
        super(type, label);
    }

    @Override
    public void switchOn() {
        enabled = true;
    }

    @Override
    public void switchOff() {
        enabled = false;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
