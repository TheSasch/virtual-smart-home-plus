package io.patriot_framework.samples.smarthome.virtual_smart_home.house;

public final class RGBLed extends SwitchableDevice {

    private int red;   // values [0, 100] ???
    private int green; // values [0, 100] ???
    private int blue;  // values [0, 100] ???

    public RGBLed(String label) {
        super(RGBLed.class, label);
    }

    public RGBLed(String label, int red, int green, int blue) {
        super(RGBLed.class, label);
        this.red = proper(red);
        this.green = proper(green);
        this.blue = proper(blue);
    }

    public int getRed() {
        return red;
    }

    public void setRed(int red) {
        this.red = proper(red);
    }

    public int getGreen() {
        return green;
    }

    public void setGreen(int green) {
        this.green = proper(green);
    }

    public int getBlue() {
        return blue;
    }

    public void setBlue(int blue) {
        this.blue = proper(blue);
    }

    private int proper(int value) {
        if (value < 0) {
            return 0;
        } else if (value > 100) {
            return 100;
        }
        return value;
    }
}
