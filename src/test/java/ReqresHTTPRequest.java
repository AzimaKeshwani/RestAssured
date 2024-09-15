import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

/*given() - Pre-request before sending request
content type, cookies, authorization, parameters, headers
when() - Request URL (get, post, put, patch, delete)
then() - validate status code, extract response, header & cookies and response body
*/
public class ReqresHTTPRequest {
    int id;

    @Test(priority = 1)
    void getUser() {
        given().
                when().get("https://reqres.in/api/users?page=2")
                .then().assertThat().statusCode(200)
                .log().all()
                .body("page", equalTo(2))
                .body("data.id[0]", equalTo(7));
    }

    @Test(priority = 2)
    void createUser() {
        HashMap hm = new HashMap();
        hm.put("name", "Franky");
        hm.put("job", "Leadership");

        id = given()
                .contentType("application/json")
                .body(hm).
                when()
                .post("https://reqres.in/api/users")
                .jsonPath().getInt("id");


    }

    @Test(priority = 3, dependsOnMethods = "createUser")
    void updateUser() {
        HashMap data = new HashMap();
        data.put("name", "Franky");
        data.put("job", "Leadership");
        given().contentType("application/json").body(data)
                .when().put("https://reqres.in/api/users/" + id).then().statusCode(200).log().all();
    }

    @Test(priority = 4)
    void deleteUser(){
        given().when().delete("https://reqres.in/api/users/"+id).then().statusCode(204).log().all();
    }
}
