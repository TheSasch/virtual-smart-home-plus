package io.patriot_framework.virtualsmarthomeplus.House.DeviceTests;

import io.patriot_framework.virtualsmarthomeplus.house.Fireplace;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class FirePlaceTests {

    Fireplace fireplace = new Fireplace("fireplace");

    @Test
    public void fireUp() {
        fireplace.fireUp();
        assertThat(fireplace.isEnabled(), equalTo(true));
    }

    @Test
    public void extinguish() {
        fireplace.extinguish();
        assertThat(fireplace.isEnabled(), equalTo(false));
    }
}
