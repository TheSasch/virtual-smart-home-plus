package patriot.virtualsmarthomeplus.house.devices;

public abstract class Device {

    private final String label;

    public Device(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}
