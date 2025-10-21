package tasks.day02;

import base_urls.ReqresBaseUrl;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import pojos.ReqresPojo;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.testng.Assert.assertEquals;

public class T01 extends ReqresBaseUrl {

    /*
        Given: Base URL: https://reqres.in/api/users
        Request Body:
        {
        "name": "morpheus",
        "job": "leader"
        }
        When: Send a POST request to the URL
        Then: Assert the status code is 201
        Verify the response body matches the structure:
        {
        "name": "morpheus",
        "job": "leader",
        "id": "496",
        "createdAt": "2022-10-04T15:18:56.372Z"
        }
*/

    @Test
    void testUsingPojo(){

        //prepare expected data
        ReqresPojo payload = new ReqresPojo("morpheus","leader");

        //send post request
        Response response = given(spec).header("x-api-key","reqres-free-v1").body(payload).post("/api/users");
        response.prettyPrint();

        //do assertion
        //1st way
        ReqresPojo actualData = response.as(ReqresPojo.class);
        assertEquals(response.statusCode(), 201);
        assertEquals(actualData.getName(), payload.getName());
        assertEquals(actualData.getJob(), payload.getJob());

        //2nd way
        response.then()
                .statusCode(201)
                .body("name", equalTo(payload.getName()),
                "job",equalTo(payload.getJob()));
    }


    @Test
    void testUsingMap(){

        //create map
        Map<String, String> payload = new HashMap<>();
        payload.put("name", "morpheus");
        payload.put("job", "leader");

        //send request
        Response response = given(spec).header("x-api-key","reqres-free-v1").body(payload).post("/api/users");
        response.prettyPrint();

        // do assertion
        Map<String,Object> responseMap = response.as(Map.class);
        assertEquals(response.statusCode(), 201);
        assertEquals(responseMap.get("name"), payload.get("name"));
        assertEquals(responseMap.get("job"), payload.get("job"));

    }

}
