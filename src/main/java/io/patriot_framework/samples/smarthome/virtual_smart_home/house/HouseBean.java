package io.patriot_framework.samples.smarthome.virtual_smart_home.house;

import org.apache.camel.Exchange;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class HouseBean {

    private static HouseBean houseBean;
    private House house;

    public HouseBean() {
        // Hard coded House.
        house = new House.Builder()
                .withName("house")
                .withDevice(new Ac("air-conditioner"))
                .withDevice(new Ac("klimatizacia"))
                .withDevice(new Fireplace("fireplace"))
                .withDevice(new Fireplace("krb"))
                .withDevices(Arrays.asList(
                        new Light("kitchen"),
                        new Light("kuchyna")
                ))
                .withDevices(Arrays.asList(
                        new RGBLed("colorful-light"),
                        new RGBLed("farebne-svetlo")
                ))
                .build();
    }

    public static HouseBean getInstance() {
        if (houseBean == null) {
            houseBean = new HouseBean();
        }
        return houseBean;
    }

    public void acOn(String label) {
        try {
            if (label.equals("all")) {
                house.getAllDevices(Ac.class).forEach(Ac::switchOn);
                return;
            }
            ((Ac) house.getDevice(label)).switchOn();
        } catch (Exception e) {
            e.printStackTrace(); // TODO
        }
    }

    public void acOff(String label) {
        try {
            if (label.equals("all")) {
                house.getAllDevices(Ac.class).forEach(Ac::switchOff);
                return;
            }
            ((Ac) house.getDevice(label)).switchOff();
        } catch (Exception e) {
            e.printStackTrace(); // TODO
        }
    }

    public void fireplaceOn(String label) {
        try {
            if (label.equals("all")) {
                house.getAllDevices(Fireplace.class).forEach(Fireplace::fireUp);
                return;
            }
            ((Fireplace) house.getDevice(label)).fireUp();
        } catch (Exception e) {
            e.printStackTrace(); // TODO
        }
    }

    public void fireplaceOff(String label) {
        try {
            if (label.equals("all")) {
                house.getAllDevices(Fireplace.class).forEach(Fireplace::extinguish);
                return;
            }
            ((Fireplace) house.getDevice(label)).extinguish();
        } catch (Exception e) {
            e.printStackTrace(); // TODO
        }
    }

    public void lightOn(String label) {
        try {
            if (label.equals("all")) {
                house.getAllDevices(Light.class).forEach(Light::switchOn);
                return;
            }
            ((Light) house.getDevice(label)).switchOn();
        } catch (Exception e) {
            e.printStackTrace(); // TODO
        }
    }

    public void lightOff(String label) {
        try {
            if (label.equals("all")) {
                house.getAllDevices(Light.class).forEach(Light::switchOff);
                return;
            }
            ((Light) house.getDevice(label)).switchOff();
        } catch (Exception e) {
            e.printStackTrace(); // TODO
        }
    }

    public void ledOn(String label) {
        try {
            if (label.equals("all")) {
                house.getAllDevices(RGBLed.class).forEach(RGBLed::switchOn);
                return;
            }
            ((RGBLed) house.getDevice(label)).switchOn();
        } catch (Exception e) {
            e.printStackTrace(); // TODO
        }
    }

    public void ledOff(String label) {
        try {
            if (label.equals("all")) {
                house.getAllDevices(RGBLed.class).forEach(RGBLed::switchOff);
                return;
            }
            ((RGBLed) house.getDevice(label)).switchOff();
        } catch (Exception e) {
            e.printStackTrace(); // TODO
        }
    }

    public void setLed(String msg) { // TODO exception handling
        String[] parts = msg.split(";");
        String label = parts[0];

        List<RGBLed> leds = label.equals("all") ?
                house.getAllDevices(RGBLed.class) :
                Collections.singletonList((RGBLed) house.getDevice(label));

        int red = Integer.parseInt(parts[1]);
        int green = Integer.parseInt(parts[2]);
        int blue = Integer.parseInt(parts[3]);

        leds.forEach(rgbLed -> {
            rgbLed.setRed(red);
            rgbLed.setGreen(green);
            rgbLed.setBlue(blue);
        });
    }

    public void objectInfo(Exchange exchange) throws ClassNotFoundException {
        String type = exchange.getProperty("type").toString();
        Object labelObject = exchange.getProperty("label");

        if (labelObject == null) {
            exchange.getOut().setBody(null);
            return;
        }

        String label = labelObject.toString();
        Object object = label.equals("all") ?
                house.getAllDevices(Class.forName("io.patriot_framework.samples.smarthome.virtual_smart_home.house." + type)) : // TODO
                house.getDevice(label);

        exchange.getOut().setBody(object);
    }

    public void houseView(Exchange exchange) {
        exchange.getOut().setBody(house);
    }
}
