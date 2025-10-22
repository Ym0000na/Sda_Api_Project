package tasks.day04;

import base_urls.BookStoreBaseUrl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import utilities.ObjectMapperUtils;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static utilities.ObjectMapperUtils.getJsonNode;

public class Task02 extends BookStoreBaseUrl {
    /*
    Create a user
    Assign books to user
    Get user's info
    Get all books
     */

    public static String userId;
    public static String token;
    public static String isbn1;
    public static String isbn2;


    @Test
    void r01createUser() throws IOException{

        //prepare payload
        JsonNode payload = getJsonNode("book_store");

        //send the request
        Response response = given(spec).body(payload).post("/Account/v1/User");
        response.prettyPrint();

        //do assertion
        response
                .then()
                .statusCode(201)
                .body(
                        "username", equalTo(payload.get("userName").asText())
                );

        userId = response.jsonPath().getString("userID");

        //save id into file
        JsonNode nn = ObjectMapperUtils.getJsonNode("book_add");
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode object = (ObjectNode) nn;


        object.put("userId", userId);

        // write changes back to the file (pretty-printed)
        Path jsonPath = Paths.get("src/test/resources/test_data/book_add.json");
        mapper.writerWithDefaultPrettyPrinter().writeValue(jsonPath.toFile(), object);

    }

    @Test
    void r02getToken(){

        //prepare payload
        JsonNode payload = getJsonNode("book_store");

        //send the request
        Response response = given(spec).body(payload).post("/Account/v1/GenerateToken");
        response.prettyPrint();

        //do assertions
        response
                .then()
                .statusCode(200)
                .body(
                        "token", Matchers.notNullValue(),
                        "status", Matchers.containsString("Success"),
                        "result", Matchers.containsString("User authorized successfully.")
                );

        token = response.jsonPath().getString("token");
    }

    @Test
    void r03getBooks() throws IOException {

        //send request
        Response response = given(spec).get("/BookStore/v1/Books");
        response.prettyPrint();

        //do assertions
        response
                .then()
                .statusCode(200)
                .body(
                        "books.isbn[0]", Matchers.notNullValue()
                );

        //save isbn into file
        isbn1 = response.jsonPath().getString("books.isbn[1]");
        isbn2 = response.jsonPath().getString("books.isbn[2]");

        JsonNode nn = ObjectMapperUtils.getJsonNode("book_add");
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode object = (ObjectNode) nn;


        // replace collectionOfIsbns array with new values
        ArrayNode arr = object.withArray("collectionOfIsbns");
        arr.removeAll();
        arr.add(mapper.createObjectNode().put("isbn", isbn1));
        arr.add(mapper.createObjectNode().put("isbn", isbn2));

        // write changes back to the file (pretty-printed)
        Path jsonPath = Paths.get("src/test/resources/test_data/book_add.json");
        mapper.writerWithDefaultPrettyPrinter().writeValue(jsonPath.toFile(), object);

    }

    @Test
    void r04booksToUser(){

        //prepare payload
        JsonNode payload = getJsonNode("book_add");

        //send request
        Response response = given(spec).header("Authorization", "Bearer "+token).body(payload).post("/BookStore/v1/Books");
        response.prettyPrint();

        //do assertion
        response
                .then()
                .statusCode(201)
                .body(
                        "books.isbn[0]", equalTo(payload.get("collectionOfIsbns").get(0).get("isbn").textValue()),
                        "books.isbn[1]", equalTo(payload.get("collectionOfIsbns").get(1).get("isbn").textValue())
                );

    }

    @Test
    void r05getUserInfo(){

        //send the request
        Response response = given(spec).header("Authorization", "Bearer "+token).get("/Account/v1/User/"+userId);
        response.prettyPrint();

        //prepare expected data
        JsonNode expectedData1 = ObjectMapperUtils.getJsonNode("book_store");
        JsonNode expectedData2 = ObjectMapperUtils.getJsonNode("book_add");

        //do assertions
        response
                .then()
                .statusCode(200)
                .body(
                       "userId" , equalTo(userId),
                        "username", equalTo(expectedData1.get("userName").asText()),
                        "books.isbn[0]", equalTo(expectedData2.get("collectionOfIsbns").get(0).get("isbn").textValue()),
                        "books.isbn[1]", equalTo(expectedData2.get("collectionOfIsbns").get(1).get("isbn").textValue())
                );

    }

}
