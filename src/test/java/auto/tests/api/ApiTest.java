package auto.tests.api;

import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class ApiTest {

    @Test
    void getPost() {
        given()
                .when()
                .get("https://jsonplaceholder.typicode.com/posts/1")
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("userId", equalTo(1))
                .body("userId", equalTo(1))
                .body("title", equalTo("sunt aut facere repellat provident occaecati excepturi optio reprehenderit"));
    }

    @Test
    void sendPost() {
        given()
                .contentType("application/json")
                .body("""

                        {
                                "title": "MyTest",
                                "body": "REST Assured",
                                "userId": 1
                        }
                """)
                .when()
                .post("https://jsonplaceholder.typicode.com/posts")
                .then()
                .statusCode(201)
                .body("title", equalTo("MyTest"))
                .body("body", equalTo("REST Assured"))
                .body("userId", equalTo(1));
    }

    @Test
    void getNonExistPost() {
        given()
                .when()
                .get("https://jsonplaceholder.typicode.com/posts/999999")
                .then()
                .statusCode(404);
    }

    @Test
    void getPostResponse() {
        Response response = given()
                .when()
                .get("https://jsonplaceholder.typicode.com/posts/1");
        int id = response.jsonPath().getInt("id");
        String title = response.jsonPath().getString("title");
        System.out.println("ID: " + id);
        System.out.println("Title: " + title);
        Assertions.assertEquals(1, id);
        Assertions.assertEquals("sunt aut facere repellat provident occaecati excepturi optio reprehenderit", title);
    }

    @Test
    void getPosts() {
        Response response = given()
                .when()
                .get("https://jsonplaceholder.typicode.com/posts");
        Assertions.assertEquals(200, response.statusCode());
        int count = response.jsonPath().getList("$").size();
        int lastPostId = response.jsonPath().getInt("[99].id");
        Assertions.assertEquals(100, count);
        Assertions.assertEquals(100, lastPostId);
    }

    @Test
    void deletePost() {
        Response response = given()
                .when()
                .delete("https://jsonplaceholder.typicode.com/posts/1");
                Assertions.assertEquals(200, response.statusCode());
                String body = response.getBody().asString();
                Assertions.assertEquals("{}", body);
    }
}