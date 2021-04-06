package io.patriot_framework.samples.smarthome.virtual_smart_home.house;

public final class Tv extends SwitchableDevice { // WIP

    private String channel = "none";
    // TODO volume

    public Tv(String label) {
        super(Tv.class, label);
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }
}
