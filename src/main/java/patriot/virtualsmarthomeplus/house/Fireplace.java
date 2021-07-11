package patriot.virtualsmarthomeplus.house;

import patriot.virtualsmarthomeplus.house.devices.Actuator;

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
