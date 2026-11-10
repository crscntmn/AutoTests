package auto.tests.api;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public final class ApiConfig {

    private ApiConfig() {
    }

    private static String getConfigValue(String name, String defaultValue) {
        String systemProperty = System.getProperty(name);

        if (systemProperty != null && !systemProperty.isBlank()) {
            return systemProperty;
        }

        String environmentVariable = System.getenv(name);

        if (environmentVariable != null && !environmentVariable.isBlank()) {
            return environmentVariable;
        }

        return defaultValue;
    }

    public static String getBaseUrl() {
        return getConfigValue("API_BASE_URL", "https://jsonplaceholder.typicode.com");
    }

    public static RequestSpecification requestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(getBaseUrl())
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
