package io.patriot_framework.samples.smarthome.virtual_smart_home.house;

public final class Fireplace extends SwitchableDevice {

    public Fireplace(String label) {
        super(Fireplace.class, label);
    }

    public void fireUp() {
        switchOn();
    }

    public void extinguish() {
        switchOff();
    }
}
