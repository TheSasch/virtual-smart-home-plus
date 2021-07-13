package io.patriot_framework.virtualsmarthomeplus.house;

public class HouseBean {

    private House house;

    private HouseBean(House house) {
        this.house = house;
    }

    public void fireplaceOn() {
        ((Fireplace) (house.getDevice("fireplace"))).fireUp();
    }
}
