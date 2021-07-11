package patriot.virtualsmarthomeplus.House;

import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import patriot.virtualsmarthomeplus.InitialApplication;
import patriot.virtualsmarthomeplus.house.Fireplace;
import patriot.virtualsmarthomeplus.house.House;
import patriot.virtualsmarthomeplus.house.devices.Device;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(CamelSpringBootRunner.class)
@SpringBootTest(classes = InitialApplication.class)
public class BasicHouseTests {
    House house = new House("test-house");
    Map<String, Device> devices = new ConcurrentHashMap<>();
    Fireplace fireplace = new Fireplace("fireplace");

    @Test
    public void houseNameTests() {
        assertThat(house.getHouseName(), equalTo("test-house"));
        house.setHouseName("new-house");
        assertThat(house.getHouseName(), equalTo("new-house"));

    }

    @Test
    public void addDevice() {
        devices.put("fireplace", fireplace);
        house.setDevices(devices);
        assertThat(house.getDevice("fireplace"), equalTo(fireplace));
    }

    @Test
    public void removeDevice() {
        devices.put("fireplace", fireplace);
        house.setDevices(devices);
        house.removeDevice("fireplace");
        assertThat(house.getDevice("fireplace"), equalTo(null));
    }

}
