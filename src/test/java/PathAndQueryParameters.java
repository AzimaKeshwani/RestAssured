import io.restassured.response.Response;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class PathAndQueryParameters {
    @Test
    void testQueryAndPathParameters() {
        Response response =
                given().pathParams("mypath", "users")
                        .queryParam("page", 2)
                        .queryParam("id", 5)
                        .when().get("https://reqres.in/api/{mypath}");

        response.then().statusCode(200)
                .body("data.id", equalTo(5))
                .body("data.email", equalTo("charles.morris@reqres.in"))
                .body("data.first_name", equalTo("Charles"))
                .body("data.last_name", equalTo("Morris"))
                .log().all();

    }
}
