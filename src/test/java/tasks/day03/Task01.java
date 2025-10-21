package tasks.day03;

import base_urls.RandomUserBaseUrl;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import pojos.DataItem;
import pojos.GoRestUsersPojo;
import pojos.random_user.RandomUserPojo;
import utilities.ObjectMapperUtils;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class Task01 extends RandomUserBaseUrl {
/*
    Send a GET request to https://randomuser.me/api
    The response will contain random user information in nested JSON structure
    Deserialize the response into a POJO class
    Assert that the following fields are NOT null:

        Email
        Username
        Password
        Medium picture URL

 */

    @Test
    void test(){

        //send the request
        Response response = given(spec).get("/api");
        response.prettyPrint();

        //Do assertion
        RandomUserPojo actualData = ObjectMapperUtils.convertJasonToJava(response.asString(),RandomUserPojo.class);
        assertNotNull(actualData.getResults().get(0).getEmail());
        assertNotNull(actualData.getResults().get(0).getLogin().getUsername());
        assertNotNull(actualData.getResults().get(0).getLogin().getPassword());
        assertNotNull(actualData.getResults().get(0).getPicture().getMedium());

    }
}
