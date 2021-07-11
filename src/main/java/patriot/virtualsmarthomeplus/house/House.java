package patriot.virtualsmarthomeplus.house;

import patriot.virtualsmarthomeplus.house.devices.Device;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class House {

    private String houseName;
    private Map<String, Device> devices;

    public House(String houseName) {
        this.houseName = houseName;
    }

    public void addDevice(String label, Device device) {
        devices.put(label, device);
    }

    public Device getDevice(String label) {
        return devices.get(label);
    }

    public void removeDevice(String label){
        devices.remove(label);
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public void setDevices(Map<String, Device> devices) {
        this.devices = devices;
    }

    public <T> List<T> getAllDevices(Class<T> type) {
        ArrayList<T> toReturn = new ArrayList<>();

        for (Device d : devices.values()) {
            if (type.isInstance(d)) {
                toReturn.add(type.cast(d));
            }
        }
        return toReturn;
    }


}
