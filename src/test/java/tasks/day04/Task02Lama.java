package tasks.day04;

import base_urls.BookStoreBaseUrl;
import base_urls.BookStoreBaseUrl;
import com.fasterxml.jackson.databind.JsonNode;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static utilities.ObjectMapperUtils.getJsonNode;

public class Task02Lama extends BookStoreBaseUrl {

    public static String token;
    public static String userId;

    // Create User
    @Test(priority = 1)
    void createUserTest() {
        JsonNode payload = getJsonNode("book_store");
        Response response = given(spec)
                .contentType("application/json")
                .body(payload)
                .when()
                .post("/Account/v1/User");

        response.prettyPrint();

        response.then()
                .statusCode(201)
                .body("username", equalTo(payload.get("userName").asText()));

        userId = response.jsonPath().getString("userID");
        System.out.println("User created successfully with ID: " + userId);
    }

    // Generate Token for the user
    @Test(priority = 2)
    void generateTokenTest() {
        JsonNode payload = getJsonNode("Bookstore_User");
        Response response = given(spec)
                .contentType("application/json")
                .body(payload)
                .when()
                .post("/Account/v1/GenerateToken");


        response.prettyPrint();

        response.then()
                .statusCode(200)
                .body("status", equalTo("Success"));

        token = response.jsonPath().getString("token");
        System.out.println("Token generated successfully: " + token);
    }

    // Get all books
    @Test(priority = 3)
    void getAllBooksTest() {
        Response response = given(spec)
                .when()
                .get("/BookStore/v1/Books");

        response.prettyPrint();

        response.then()
                .statusCode(200)
                .body("books", not(empty()));
        System.out.println("All books retrieved successfully!");
    }


    // Assign Books to User
    @Test(priority = 4)
    void assignBooksToUserTest() {

    }
}