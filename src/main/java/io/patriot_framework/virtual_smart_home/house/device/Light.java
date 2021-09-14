package io.patriot_framework.virtual_smart_home.house.device;

import com.fasterxml.jackson.annotation.JsonProperty;

// TODO add actual functionality and figure out accessibility to those functions with REST.
public final class Light extends Actuator {

    public Light(@JsonProperty("label") String label) {
        super(label);
    }
}
