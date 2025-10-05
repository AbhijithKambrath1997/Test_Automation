package rest.endpoints;

import com.fasterxml.jackson.databind.JsonNode;
import commonUtils.fileReaders.PropertyReader;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.Properties;

public class PetEndpoints {

    PropertyReader propertyReader = new PropertyReader();
    Properties properties = propertyReader.getProperties("src/main/resources/endpoints.properties");

    public Response createPet(JsonNode payload) {
        return RestAssured
                .given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(payload)
                .log()
                .all()
                .when()
                .post(properties.getProperty("createPet"))
                .then()
                .log().all()
                .extract()
                .response();
    }
}
