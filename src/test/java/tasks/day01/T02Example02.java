package tasks.day01;

import base_urls.JPHBaseUrl;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class T02Example02 extends JPHBaseUrl {

    /*
    Given
    https://jsonplaceholder.typicode.com/todos

    When
    I send GET request

    Then
    1) Status code = 200
    2) Print all ids > 190 (10 total)
    3) Print userIds with ids < 5 (4 total)
    4) Verify title “quis eius est sint explicabo”
    5) Find id where title = "quo adipisci enim quam ut ab"
     */

    @Test
    void test(){
        Response response = given(spec).get("/todos");
        //response.prettyPrint();

        response.then().statusCode(200);

        JsonPath jsonPath = response.jsonPath();

        System.out.println("id>190: " + jsonPath.getList("findAll{it.id >190}.id"));
        System.out.println("id<5: " + jsonPath.getList("findAll{it.id <5}.userId"));
        System.out.println(jsonPath.getBoolean("any{it.title.contains('quis eius est sint explicabo')}"));
        System.out.println("id with title quo adipisci enim quam ut ab: "+jsonPath.getInt("find{it.title=='quo adipisci enim quam ut ab'}.id"));

    }
}
