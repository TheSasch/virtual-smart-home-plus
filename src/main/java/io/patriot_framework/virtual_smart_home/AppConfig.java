package io.patriot_framework.virtual_smart_home;

import io.patriot_framework.virtual_smart_home.house.House;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "house.properties")
public class AppConfig {

    @Value("${house.name}")
    private String houseName;

    @Bean
    public House houseProducer() {
        return new House(houseName);
    }
}
