package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class C02_Assertion {


    @Test
    void assertionTest(){

        Response response = RestAssured.get("https://restful-booker.herokuapp.com/booking"); // sends get request


        Assert.assertEquals(response.statusCode(),200);

        Assert.assertTrue(response.contentType().contains("application/json"));

        Assert.assertEquals(response.statusLine(),("HTTP/1.1 200 OK"));
    }

}
