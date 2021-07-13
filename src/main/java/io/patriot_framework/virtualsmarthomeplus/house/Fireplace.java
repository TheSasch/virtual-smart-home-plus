package io.patriot_framework.virtualsmarthomeplus.house;

import io.patriot_framework.virtualsmarthomeplus.house.devices.Actuator;

public class Fireplace extends Actuator {

    public Fireplace(String label) {
        super(label);
    }

    public void fireUp() {
        this.switchIt(true);
    }

    public void extinguish() {
        this.switchIt(false);
    }
}
