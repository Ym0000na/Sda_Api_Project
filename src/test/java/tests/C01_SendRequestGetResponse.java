package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class C01_SendRequestGetResponse {

    public static void main(String[] args) {


        Response response = RestAssured.get("https://restful-booker.herokuapp.com/booking"); // sends get request

        response.prettyPrint(); // Print the json response body in a pretty format

        int statusCode = response.getStatusCode(); // Get the status code from the response
        System.out.println("Status Code: " + statusCode); // Print the status code

        System.out.println("response.statusLine() = " + response.statusLine()); // Print the status line

        System.out.println("response.getContentType() = " + response.getContentType()); // Print the content type

        System.out.println("response.time() = " + response.time()); // Print the response time taken in milliseconds

        System.out.println("response.header(\"Server\") = " + response.header("Server")); // Get the value of the "Server" header)

        System.out.println("All Headers");
        System.out.println(response.headers()); // Print all headers
    }
}
