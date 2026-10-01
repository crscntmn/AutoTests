package auto.tests.api;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class ApiConfig {

    public static final String BASE_URL = "https://jsonplaceholder.typicode.com";

    public static RequestSpecification requestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setContentType("application/json")
                .setAccept("application/json")
                .build();
    }

    public static ResponseSpecification responseSpec() {
        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .build();
    }

    public static ResponseSpecification createdResponseSpec() {
        return new ResponseSpecBuilder()
                .expectStatusCode(201)
                .build();
    }
}
