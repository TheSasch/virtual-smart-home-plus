package io.patriot_framework.virtual_smart_home.route;

import io.patriot_framework.virtual_smart_home.house.device.Fireplace;

class FireplaceModel {

    private String label;
    private boolean enabled;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Fireplace toFireplace() {
        Fireplace fireplace = new Fireplace(label);
        if (enabled) {
            fireplace.fireUp();
        }

        return fireplace;
    }
}
