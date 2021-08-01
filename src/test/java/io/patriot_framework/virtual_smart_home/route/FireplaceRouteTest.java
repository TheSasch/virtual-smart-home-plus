package io.patriot_framework.virtual_smart_home.route;

import io.restassured.http.ContentType;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.catalina.connector.Response;
import org.hamcrest.Matchers;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;

@RunWith(CamelSpringBootRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT) // TODO: restart endpoint for every test
public class FireplaceRouteTest {                                            // TODO: (avoid DELETE after each POST)

    private final String fireplaceEndpoint = "house/device/fireplace";
    private JSONObject defaultFireplaceJson = null;

    {
        try {
            defaultFireplaceJson = new JSONObject()
                    .put("label", "fireplace")
                    .put("enabled", false);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // === GET ===

    @Test
    public void getRequestContentTypeJson() {
        given()
                .when().get(fireplaceEndpoint)
                .then().contentType(ContentType.JSON);
    }

    @Test
    public void getRequestNotFound() {
        given()
                .when().get(fireplaceEndpoint + "/notFound")
                .then().statusCode(Response.SC_NOT_FOUND); // 404
    }

    @Test
    public void getRequestNonExistentEndpoint() {
        given()
                .when().get(fireplaceEndpoint + "/nonExistent")
                .then().body(Matchers.equalTo(""));
    }

    @Test
    public void getRequestWithInvalidParam() {
        given()
                .param("param", "param")
                .when().get(fireplaceEndpoint)
                .then().body(Matchers.equalTo(new JSONArray().toString()));
    }

    @Test
    public void getRequestStatusCode200() {
        given()
                .when().get(fireplaceEndpoint)
                .then().statusCode(Response.SC_OK);
    }

    @Test
    public void emptyGetRequest() throws JSONException {
        given()
                .param("label", defaultFireplaceJson.get("label"))
                .when().get(fireplaceEndpoint)
                .then().body(Matchers.equalTo(new JSONArray().toString()));
    }

    @Test
    public void getUniqueFireplace() throws JSONException {
        // TODO: how to properly test rest service (POST, GET, DELETE)
        given()
                .contentType(ContentType.JSON)
                .body(defaultFireplaceJson.toString())
                .post(fireplaceEndpoint);

        given()
                .when().get(fireplaceEndpoint + "/" + defaultFireplaceJson.getString("label"))
                .then().body(Matchers.equalTo(defaultFireplaceJson.toString()));

        deleteFireplace(defaultFireplaceJson.getString("label"));
    }

    // === POST ===

    @Test
    public void postRequestWithoutBody() {
        given()
                .when().post(fireplaceEndpoint)
                .then().statusCode(Response.SC_BAD_REQUEST); // 400
    }

    @Test
    public void postRequestWithIncorrectJsonBody() throws JSONException {
        JSONObject invalidRequestBody = new JSONObject().put("json", "json");

        given()
                .contentType(ContentType.JSON)
                .body(invalidRequestBody.toString())
                .when().post(fireplaceEndpoint)
                .then().statusCode(Response.SC_BAD_REQUEST); // 400
    }

    @Test
    public void postRequestWithNonJsonBody() {
        given()
                .contentType(ContentType.TEXT)
                .body("body")
                .when().post(fireplaceEndpoint)
                .then().statusCode(Response.SC_BAD_REQUEST); // 400
    }

    @Test
    public void postRequestConflict() throws JSONException {
        given()
                .contentType(ContentType.JSON)
                .body(defaultFireplaceJson.toString())
                .post(fireplaceEndpoint);

        given()
                .contentType(ContentType.JSON)
                .body(defaultFireplaceJson.toString())
                .when().post(fireplaceEndpoint)
                .then().statusCode(Response.SC_CONFLICT); // 409

        deleteFireplace(defaultFireplaceJson.getString("label"));
    }

    @Test
    public void postRequestStatusCode201() throws JSONException {
        given()
                .contentType(ContentType.JSON)
                .body(defaultFireplaceJson.toString())
                .when().post(fireplaceEndpoint)
                .then().statusCode(Response.SC_CREATED);

        deleteFireplace(defaultFireplaceJson.getString("label"));
    }

    @Test
    public void simplePostRequest() throws JSONException {
        given()
                .contentType(ContentType.JSON)
                .body(defaultFireplaceJson.toString())
                .when().post(fireplaceEndpoint)
                .then().body(Matchers.equalTo(defaultFireplaceJson.toString()));

        given()
                .when().get(fireplaceEndpoint + "/" + defaultFireplaceJson.getString("label"))
                .then().body(Matchers.equalTo(defaultFireplaceJson.toString()));

        deleteFireplace(defaultFireplaceJson.getString("label"));
    }

    // === PUT ===

    @Test
    public void putRequestWithoutBody() {
        given()
                .when().put(fireplaceEndpoint)
                .then().statusCode(Response.SC_BAD_REQUEST); // 400
    }

    @Test
    public void putRequestWithInvalidBody() throws JSONException {
        JSONObject invalidRequestBody = new JSONObject().put("json", "json");

        given()
                .contentType(ContentType.JSON)
                .body(invalidRequestBody.toString())
                .when().put(fireplaceEndpoint)
                .then().statusCode(Response.SC_BAD_REQUEST); // 400
    }

    @Test
    public void putRequestStatusCode200() throws JSONException {
        given()
                .contentType(ContentType.JSON)
                .body(defaultFireplaceJson.toString())
                .post(fireplaceEndpoint);

        given() 
                .contentType(ContentType.JSON)
                .body(defaultFireplaceJson.toString())
                .when().put(fireplaceEndpoint)
                .then().statusCode(Response.SC_OK);

        deleteFireplace(defaultFireplaceJson.getString("label"));
    }

    @Test
    public void simplePutRequest() throws JSONException {
        given()
                .contentType(ContentType.JSON)
                .body(defaultFireplaceJson.toString())
                .post(fireplaceEndpoint);

        JSONObject enabledFireplace = new JSONObject(defaultFireplaceJson.toString());
        enabledFireplace.put("enabled", true);

        given()
                .contentType(ContentType.JSON)
                .body(enabledFireplace.toString())
                .put(fireplaceEndpoint);

        given()
                .when().get(fireplaceEndpoint + "/" + defaultFireplaceJson.getString("label"))
                .then().body(Matchers.equalTo(enabledFireplace.toString()));

        deleteFireplace(defaultFireplaceJson.getString("label"));
    }

    // === PATCH ===

    @Test
    public void patchRequestWithoutBody() {
        given()
                .when().patch(fireplaceEndpoint)
                .then().statusCode(Response.SC_BAD_REQUEST); // 400
    }

    @Test
    public void patchRequestStatusCode200() throws JSONException {
        given()
                .contentType(ContentType.JSON)
                .body(defaultFireplaceJson.toString())
                .post(fireplaceEndpoint);

        given()
                .contentType(ContentType.JSON)
                .body(defaultFireplaceJson.toString())
                .when().patch(fireplaceEndpoint)
                .then().statusCode(Response.SC_OK);

        deleteFireplace(defaultFireplaceJson.getString("label"));
    }

    // === DELETE ===

    @Test
    public void deleteRequestWithoutQueryParam() {
        given()
                .when().delete(fireplaceEndpoint)
                .then().statusCode(Response.SC_BAD_REQUEST); // 400
    }

    @Test
    public void deleteRequestStatusCode200() throws JSONException {
        given()
                .contentType(ContentType.JSON)
                .body(defaultFireplaceJson.toString())
                .post(fireplaceEndpoint);

        given()
                .queryParam("label", defaultFireplaceJson.getString("label"))
                .when().delete(fireplaceEndpoint)
                .then().statusCode(Response.SC_OK);
    }

    @Test
    public void simpleDeleteRequest() throws JSONException {
        given()
                .contentType(ContentType.JSON)
                .body(defaultFireplaceJson.toString())
                .post(fireplaceEndpoint);

        given()
                .param("label", defaultFireplaceJson.get("label"))
                .delete(fireplaceEndpoint);

        given()
                .param("label", defaultFireplaceJson.get("label"))
                .when().get(fireplaceEndpoint)
                .then().body(Matchers.equalTo(new JSONArray().toString()));
    }

    private void deleteFireplace(String label) { // Cleanup.
        given()
                .queryParam("label", label)
                .delete(fireplaceEndpoint);
    }
}
