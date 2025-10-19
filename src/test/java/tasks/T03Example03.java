package tasks;

import base_urls.DummyRestApiBaseUrl;
import base_urls.JPHBaseUrl;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

public class T03Example03 extends DummyRestApiBaseUrl {

    /*
    Given
    https://dummy.restapiexample.com/api/v1/employees

    When
    User sends GET request

    Then
    Status code is 200

    And
    There are 24 employees

    And
    "Tiger Nixon" and "Garrett Winters" are among them

    And
    Highest age = 66

    And
    Youngest = "Tatyana Fitzpatrick"

    And
    Total salary = 6,644,770
     */

    @Test
    void test(){
        Response response = given(spec).get("/api/v1/employees");
        //response.prettyPrint();

        response.then().statusCode(200);

        JsonPath jsonPath = response.jsonPath();

//        There are 24 employees
        List<Integer> idList = jsonPath.getList("data.id");
        System.out.println("Number of employees: " + idList.getLast());

//        "Tiger Nixon" and "Garrett Winters" are among them
        List<String> employeeNames = jsonPath.getList("data.employee_name");
        System.out.println("Tiger Nixon exists: " + employeeNames.contains("Tiger Nixon"));
        System.out.println("Garrett Winters exists: " + employeeNames.contains("Garrett Winters"));

//        Highest age = 66
        System.out.println("Max age: "
                + jsonPath.getString("data.max{it.employee_age}.employee_age"));

//        Youngest = "Tatyana Fitzpatrick"
        System.out.println("Youngest: "
                +jsonPath.getList("data.sort{it.employee_age}.employee_name").getFirst());//

//        Total salary = 6,644,770
        List<String> salaries = jsonPath.getList("data.employee_salary");
        int sum = 0;
        for (String s : salaries) {
            sum += Integer.parseInt(s);
        }
        System.out.println("sum = " + sum);

    }

    }

