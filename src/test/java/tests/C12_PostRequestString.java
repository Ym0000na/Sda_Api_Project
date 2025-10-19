package tests;

import base_urls.JPHBaseUrl;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class C12_PostRequestString extends JPHBaseUrl {
    /*
    Given:
    1) URL: https://jsonplaceholder.typicode.com/todos
    2) Request Body:
       {
           "userId": 55,
           "title": "Tidy your room",
           "completed": false
       }

    When:
    A POST request is sent to the URL.

    Then:
    - The status code should be 201.
    - The response body should match:
       {
           "userId": 55,
           "title": "Tidy your room",
           "completed": false,
           "id": 201
       }
*/

    @Test
    void postRequestStringTest() {

        //set the URL
        spec.pathParams("first", "todos");

        //set the payload
        //String payload is not recommended bcs in assertion u can't retrieve the specific data in string
        String payload = """
                {
                     "userId": 55,
                     "title": "Tidy your room",
                     "completed": false
                }
                """;


        //send the request
        Response response = given(spec).body(payload).post("{first}");
        response.prettyPrint();

        //Do assertion
        response
                .then()
                .statusCode(201)
                .contentType(ContentType.JSON)
                .body("userId", Matchers.equalTo(55))
                .body("title", Matchers.equalTo("Tidy your room"))
                .body("completed", Matchers.equalTo(false))
                ;

    }

}
