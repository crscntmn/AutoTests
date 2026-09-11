package auto.tests.api;

import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class ApiTest {

    @Test
    @DisplayName("1.1 GET запрос поста с id 1")
    void getPost() {
        Response response = given()
                .when()
                .get("https://jsonplaceholder.typicode.com/posts/1");
        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals(1, response.jsonPath().getInt("id"));
        Assertions.assertEquals(1, response.jsonPath().getInt("userId"));
        Assertions.assertEquals("sunt aut facere repellat provident occaecati excepturi optio reprehenderit", response.jsonPath().getString("title"));
    }

    @Test
    @DisplayName("1.2 POST запрос поста")
    void sendPost() {
        Response response = given()
                .contentType("application/json")
                .body("""
                        
                                {
                                        "title": "MyTest",
                                        "body": "REST Assured",
                                        "userId": 1
                                }
                        """)
                .when()
                .post("https://jsonplaceholder.typicode.com/posts");
        Assertions.assertEquals(201, response.statusCode());
        Assertions.assertEquals("MyTest", response.jsonPath().getString("title"));
        Assertions.assertEquals("REST Assured", response.jsonPath().getString("body"));
        Assertions.assertEquals(1, response.jsonPath().getInt("userId"));


    }

    @Test
    @DisplayName("1.3 GET запрос несуществующего поста")
    void getNonExistPost() {
        Response response = given()
                .when()
                .get("https://jsonplaceholder.typicode.com/posts/999999");
        Assertions.assertEquals(404, response.statusCode());
    }

    @Test
    @DisplayName("1.4 GET запрос всех постов")
    void getPosts() {
        Response response = given()
                .when()
                .get("https://jsonplaceholder.typicode.com/posts");
        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals(100, response.jsonPath().getList("$").size());
        Assertions.assertEquals(100, response.jsonPath().getInt("[99].id"));
    }

    @Test
    @DisplayName("1.5 DELETE запрос на удаление поста с id 1")
    void deletePost() {
        Response response = given()
                .when()
                .delete("https://jsonplaceholder.typicode.com/posts/1");
        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals("{}", response.getBody().asString());
    }

    @Test
    @DisplayName("1.6 PUT запрос существующего поста")
    void updatePost() {
        Response response = given()
                .contentType("application/json")
                .body("""
                                    {
                                        "id": 1,
                                        "title": "Updated title",
                                        "body": "Updated body",
                                        "userId": 1
                                    }
                        """)
                .when()
                .put("https://jsonplaceholder.typicode.com/posts/1");
        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals("Updated title", response.jsonPath().getString("title"));
        Assertions.assertEquals("Updated body", response.jsonPath().getString("body"));
    }

    @Test
    @DisplayName("1.7 PATCH запрос существующего поста")
    void patchPost() {
        Response response = given()
                .contentType("application/json")
                .body("""
                                {
                                     "title": "Patched title"
                                }
                        """)
                .when()
                .patch("https://jsonplaceholder.typicode.com/posts/1");
        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals("Patched title", response.jsonPath().getString("title"));
        Assertions.assertEquals(1, response.jsonPath().getInt("id"));
    }

    @Test
    @DisplayName("1.8 GET запрос с query")
    void getPostByUser() {
        Response response = given()
                .queryParam("userId", 1)
                .when()
                .get("https://jsonplaceholder.typicode.com/posts");
        Assertions.assertEquals(200, response.statusCode());
        List<Integer> userIds = response.jsonPath().getList("userId");
        Assertions.assertTrue(userIds.stream().allMatch(id -> id == 1));
        }

        @Test
        @DisplayName("1.9 GET запрос с заголовками")
        void checkResponseHeader() {
            Response response = given()
                    .when()
                    .header("Accept", "application/json")
                    .get("https://jsonplaceholder.typicode.com/posts/1");
            String contentType = response.getHeader("Content-Type");
            Assertions.assertTrue(contentType.contains("application/json"));

        }
    }