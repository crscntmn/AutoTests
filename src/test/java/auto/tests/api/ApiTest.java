package auto.tests.api;

import auto.tests.api.model.Post;
import auto.tests.api.model.User;
import auto.tests.api.testdata.ApiTestData;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
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
                .spec(ApiConfig.requestSpec())
                .log().ifValidationFails()
                .pathParam("id", 1)
                .when()
                .get("/posts/{id}")
                .then()
                .log().ifValidationFails()
                .spec(ApiConfig.responseSpec())
                .extract()
                .response();
        Post post = response.as(Post.class);
        Assertions.assertEquals(1, post.getId());
        Assertions.assertEquals(1, post.getUserId());
        Assertions.assertEquals("sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
                post.getTitle());
    }

    @Test
    @DisplayName("1.2 POST запрос поста")
    void sendPost() {
        Response response = given()
                .spec(ApiConfig.requestSpec())
                .body("""
                        
                                {
                                        "title": "MyTest",
                                        "body": "REST Assured",
                                        "userId": 1
                                }
                        """)
                .when()
                .post("/posts")
                .then()
                .spec(ApiConfig.createdResponseSpec())
                .extract()
                .response();
        Assertions.assertEquals("MyTest", response.jsonPath().getString("title"));
        Assertions.assertEquals("REST Assured", response.jsonPath().getString("body"));
        Assertions.assertEquals(1, response.jsonPath().getInt("userId"));


    }

    @Test
    @DisplayName("1.3 GET запрос несуществующего поста")
    void getNonExistPost() {
        Response response = given()
                .spec(ApiConfig.requestSpec())
                .when()
                .get("/posts/999999");
        Assertions.assertEquals(404, response.statusCode());
    }

    @Test
    @DisplayName("1.4 GET запрос всех постов")
    void getPosts() {
        Response response = given()
                .spec(ApiConfig.requestSpec())
                .when()
                .get("/posts")
                .then()
                .spec(ApiConfig.responseSpec())
                .extract()
                .response();
        Assertions.assertEquals(100, response.jsonPath().getList("$").size());
        Assertions.assertEquals(100, response.jsonPath().getInt("[99].id"));
    }

    @Test
    @DisplayName("1.5 DELETE запрос на удаление поста с id 1")
    void deletePost() {
        Response response = given()
                .spec(ApiConfig.requestSpec())
                .when()
                .delete("/posts/1")
                .then()
                .spec(ApiConfig.responseSpec())
                .extract()
                .response();
        Assertions.assertEquals("{}", response.getBody().asString());
    }

    @Test
    @DisplayName("1.6 PUT запрос существующего поста")
    void updatePost() {
        Response response = given()
                .spec(ApiConfig.requestSpec())
                .body("""
                                    {
                                        "id": 1,
                                        "title": "Updated title",
                                        "body": "Updated body",
                                        "userId": 1
                                    }
                        """)
                .when()
                .put("/posts/1")
                .then()
                .spec(ApiConfig.responseSpec())
                .extract()
                .response();
        Assertions.assertEquals("Updated title", response.jsonPath().getString("title"));
        Assertions.assertEquals("Updated body", response.jsonPath().getString("body"));
    }

    @Test
    @DisplayName("1.7 PATCH запрос существующего поста")
    void patchPost() {
        Response response = given()
                .spec(ApiConfig.requestSpec())
                .body("""
                                {
                                     "title": "Patched title"
                                }
                        """)
                .when()
                .patch("/posts/1")
                .then()
                .spec(ApiConfig.responseSpec())
                .extract()
                .response();
        Assertions.assertEquals("Patched title", response.jsonPath().getString("title"));
        Assertions.assertEquals(1, response.jsonPath().getInt("id"));
    }

    @Test
    @DisplayName("1.8 GET запрос с query")
    void getPostByUser() {
        Response response = given()
                .spec(ApiConfig.requestSpec())
                .queryParam("userId", 1)
                .when()
                .get("/posts")
                .then()
                .spec(ApiConfig.responseSpec())
                .extract()
                .response();
        List<Integer> userIds = response.jsonPath().getList("userId");
        Assertions.assertTrue(userIds.stream().allMatch(id -> id == 1));
    }

    @Test
    @DisplayName("1.9 GET запрос с заголовками")
    void checkResponseHeader() {
        Response response = given()
                .spec(ApiConfig.requestSpec())
                .when()
                .get("/posts/1");
        String contentType = response.getHeader("Content-Type");
        Assertions.assertTrue(contentType.contains("application/json"));
    }

    @Test
    @DisplayName("1.10 GET запрос поста с передачей id в pathParam")
    void getPostById() {
        Response response = given()
                .spec(ApiConfig.requestSpec())
                .pathParam("id", 1)
                .when()
                .get("/posts/{id}")
                .then()
                .spec(ApiConfig.responseSpec())
                .extract()
                .response();
        Assertions.assertEquals(1, response.jsonPath().getInt("id"));
    }

    @Test
    @DisplayName("1.11 POST запрос на создание пользователя")
    void createUser() {
        User user = new User(ApiTestData.USER_NAME, ApiTestData.USER_USERNAME, ApiTestData.USER_EMAIL);
        Response response = given()
                .spec(ApiConfig.requestSpec())
                .body(user)
                .when()
                .post("/users")
                .then()
                .spec(ApiConfig.createdResponseSpec())
                .extract()
                .response();
        User responseUser = response.as(User.class);
        Assertions.assertEquals(ApiTestData.USER_NAME, responseUser.getName());
        Assertions.assertEquals(ApiTestData.USER_USERNAME, responseUser.getUsername());
        Assertions.assertEquals(ApiTestData.USER_EMAIL, responseUser.getEmail());
        Assertions.assertTrue(responseUser.getId() > 0);
    }

    @Test
    @DisplayName("1.12 GET запрос пользователя с преобразованием ответа в User")
    void getUser() {
        Response response = given()
                .spec(ApiConfig.requestSpec())
                .when()
                .pathParam("id", 1)
                .get("/users/{id}")
                .then()
                .spec(ApiConfig.responseSpec())
                .extract()
                .response();
        User user = response.as(User.class);
        Assertions.assertEquals("Leanne Graham", user.getName());
        Assertions.assertEquals(1, user.getId());
        Assertions.assertEquals("Bret", user.getUsername());
        Assertions.assertEquals("Sincere@april.biz", user.getEmail());
    }

    @Test
    @DisplayName("1.13 PUT обновление пользователя через POJO")
    void updateUser() {

        User user = new User("Updated name", "Updated username", "Updated@mail.ru");

        Response response = given()
                .spec(ApiConfig.requestSpec())
                .body(user)
                .when()
                .pathParam("id", 1)
                .put("/users/{id}")
                .then()
                .spec(ApiConfig.responseSpec())
                .extract()
                .response();

        User responseUser = response.as(User.class);
        Assertions.assertEquals("Updated name", responseUser.getName());
        Assertions.assertEquals("Updated username", responseUser.getUsername());
        Assertions.assertEquals("Updated@mail.ru", responseUser.getEmail());
    }
}