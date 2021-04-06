package io.patriot_framework.samples.smarthome.virtual_smart_home.house;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public final class House {

    private String name;
    private Map<String, Device> devices;

    public House(String name, Map<String, Device> devices) {
        this.name = name;
        this.devices = devices;
    }

    public Device getDevice(String label) {
        return devices.get(label);
    }

    public <T> List<T> getAllDevices(Class<T> type) {
        return devices.values().stream()
                .filter(type::isInstance)
                .map(type::cast)
                .collect(Collectors.toList());
    }

    public String getName() {
        return name;
    }

    // For testing purposes.
    public Map<String, Device> getDevices() {
        return devices;
    }

    public static class Builder {

        private String name = "";
        private Map<String, Device> devices = new ConcurrentHashMap<>();

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withDevice(Device device) {
            devices.put(device.getLabel(), device);
            return this;
        }

        public Builder withDevices(List<Device> devices) {
            devices.forEach(this::withDevice);
            return this;
        }

        public House build() {
            return new House(name, devices);
        }
    }
}
