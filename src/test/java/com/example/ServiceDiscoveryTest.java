package com.example;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
public class ServiceDiscoveryTest {
    @Test
    void shouldAddAndGetService() throws InterruptedException {
        given().contentType(ContentType.JSON)
                .body("{\"serviceName\": \"my-super-service\", \"url\": \"http://my-service:1283\", \"labels\": [\"v1\"]}")
                .when().post("/services")
                .then().statusCode(204);

        Response response = when().get("/services/my-super-service");
        response.then().statusCode(200);
        JsonArray entries = new JsonArray(response.print());
        assertThat(entries).hasSize(1);
        JsonObject service = (JsonObject) entries.iterator().next();
        assertThat(service.getString("serviceName")).isEqualTo("my-super-service");
        assertThat(service.getString("url")).isEqualTo("http://my-service:1283");
        JsonArray labels = service.getJsonArray("labels");
        assertThat(labels).hasSize(1);
        assertThat(labels.iterator().next()).isEqualTo("v1");
    }

    @Test
    void shouldNotGetServiceIfNothingRegisteredForName() {
        given().contentType(ContentType.JSON)
                .body("{\"serviceName\": \"my-normal-service\", \"url\": \"http://my-service:1283\", \"labels\": [\"v1\"]}")
                .when().post("/services")
                .then().statusCode(204);

        Response response = when().get("/services/my-super-service");
        response.then().statusCode(200);
        JsonArray entries = new JsonArray(response.print());
        assertThat(entries).hasSize(0);
    }
}
