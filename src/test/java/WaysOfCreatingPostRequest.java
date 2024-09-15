
import io.restassured.response.Response;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class WaysOfCreatingPostRequest {

    private static String studentId;

    //    @Test(priority = 1)
    void testPostUsingHaspMap() {

        HashMap data = new HashMap<>();
        data.put("name", "johnny");
        data.put("location", "Dallas");
        data.put("phone", "7896757898");
        String courseArr[] = {"Selenium", "C"};
        data.put("courses", courseArr);

        Response response = given().contentType("application/json")
                .body(data)
                .when()
                .post("http://localhost:3000/students");

        studentId = response.jsonPath().getString("id");
        response.then().assertThat().statusCode(201)
                .body("name", equalTo("johnny"))
                .body("location", equalTo("Dallas"))
                .body("phone", equalTo("7896757898"))
                .body("courses[0]", equalTo("Selenium"))
                .body("courses[1]", equalTo("C"))
                .header("Content-Type", "application/json")
                .log().all();
    }

    //    @Test(priority = 1)
    void testPostUsingJSONLibrary() {

        JSONObject data = new JSONObject();
        data.put("name", "Jackson");
        data.put("location", "France");
        data.put("phone", "7896757897");
        String courseArr[] = {"C++", "C"};
        data.put("courses", courseArr);

        Response response = given().contentType("application/json")
                .body(data.toString())
                .when()
                .post("http://localhost:3000/students");

        studentId = response.jsonPath().getString("id");
        response.then().assertThat().statusCode(201)
                .body("name", equalTo("Jackson"))
                .body("location", equalTo("France"))
                .body("phone", equalTo("7896757897"))
                .body("courses[0]", equalTo("C++"))
                .body("courses[1]", equalTo("C"))
                .header("Content-Type", "application/json")
                .log().all();
    }

//    @Test(priority = 1)
    void testPostUsingPOJO() {

        POJO_PostRequest data = new POJO_PostRequest();
        data.setName("Jackson");
        data.setLocation("France");
        data.setPhone("7896757897");
        String courses[] = {"C++", "C"};
        data.setCourses(courses);

        Response response = given().contentType("application/json")
                .body(data)
                .when()
                .post("http://localhost:3000/students");

        studentId = response.jsonPath().getString("id");


        response.then().assertThat().statusCode(201)
                .body("name", equalTo("Jackson"))
                .body("location", equalTo("France"))
                .body("phone", equalTo("7896757897"))
                .body("courses[0]", equalTo("C++"))
                .body("courses[1]", equalTo("C"))
                .header("Content-Type", "application/json")
                .log().all();
    }

    @Test(priority = 1)
    void testPostUsingExternalJSONFile() throws FileNotFoundException {

        File file = new File(".\\body.json");
        FileReader fr = new FileReader(file);
        JSONTokener jt = new JSONTokener(fr);
        JSONObject data = new JSONObject(jt);

        Response response = given().contentType("application/json")
                .body(data.toString())
                .when()
                .post("http://localhost:3000/students");

        studentId = response.jsonPath().getString("id");


        response.then().assertThat().statusCode(201)
                .body("name", equalTo("Jackson"))
                .body("location", equalTo("France"))
                .body("phone", equalTo("7896757897"))
                .body("courses[0]", equalTo("C++"))
                .body("courses[1]", equalTo("C"))
                .header("Content-Type", "application/json")
                .log().all();
    }

    @Test(priority = 2)
    void testDelete() {
        given()
                .when().delete("http://localhost:3000/students/" + studentId)
                .then().statusCode(200);
    }
}
