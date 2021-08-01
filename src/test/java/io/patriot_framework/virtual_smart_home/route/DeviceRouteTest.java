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
public class DeviceRouteTest {

    private final String deviceEndpoint = "house/device";

    @Test
    public void getRequestContentTypeJson() {
        given()
                .when().get(deviceEndpoint)
                .then().contentType(ContentType.JSON);
    }

    @Test
    public void getRequestStatusCode200() {
        given()
                .when().get(deviceEndpoint)
                .then().statusCode(Response.SC_OK);
    }

    @Test
    public void emptyGetRequest() {
        given()
                .when().get(deviceEndpoint)
                .then().body(Matchers.equalTo(new JSONObject().toString()));
    }

    @Test
    public void simpleGetRequest() throws JSONException {
        JSONObject defaultFireplaceJson = new JSONObject()
                .put("label", "fireplace")
                .put("enabled", false);

        given()
                .contentType(ContentType.JSON)
                .body(defaultFireplaceJson.toString())
                .post(deviceEndpoint + "/fireplace");

        given()
                .when().get(deviceEndpoint)
                .then().body(Matchers.equalTo(new JSONObject().put(defaultFireplaceJson.getString("label"),
                    defaultFireplaceJson).toString()));

        given()
                .queryParam("label", defaultFireplaceJson.getString("label"))
                .delete(deviceEndpoint + "/fireplace");
    }
}
