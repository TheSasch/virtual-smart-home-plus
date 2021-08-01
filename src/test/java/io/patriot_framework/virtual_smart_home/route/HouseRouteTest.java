package io.patriot_framework.virtual_smart_home.route;

import io.restassured.http.ContentType;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.catalina.connector.Response;
import org.hamcrest.Matchers;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;

@RunWith(CamelSpringBootRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class HouseRouteTest {

    private final String houseEndpoint = "house/";

    @Test
    public void contentTypeJSON() {
        given()
                .when().get(houseEndpoint)
                .then().assertThat().contentType(ContentType.JSON);
    }

    @Test
    public void getRequestStatusCode200() {
        given()
                .when().get(houseEndpoint)
                .then().assertThat().statusCode(Response.SC_OK);
    }

    @Test
    public void emptyGetRequest() throws JSONException {
        JSONObject expected = new JSONObject();
        expected.put("houseName", "house").put("devices", new JSONObject());

        given()
                .when().get(houseEndpoint)
                .then().assertThat().body(Matchers.equalTo(expected.toString()));
    }
}
