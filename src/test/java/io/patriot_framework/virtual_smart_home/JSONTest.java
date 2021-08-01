package io.patriot_framework.virtual_smart_home;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static org.hamcrest.number.OrderingComparison.lessThan;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class JSONTest {

    @Test
    public void getResponseTime() {
        given()
                .when().get("http://localhost:8080/house/device/fireplace")
                .then().statusCode(200).time(lessThan(1000L));
    }
}
