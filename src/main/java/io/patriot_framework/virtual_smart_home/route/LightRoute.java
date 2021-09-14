package io.patriot_framework.virtual_smart_home.route;

import io.patriot_framework.virtual_smart_home.house.device.Light;
import org.springframework.stereotype.Component;

@Component
public class LightRoute extends AbstractDeviceRoute {

   public LightRoute() {
       super.setupRoute("light", Light.class);
   }
}
