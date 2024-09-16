import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import io.restassured.http.*;

@Test
public class Headers {

//    @Test
    void headers() {
        Response response = given()
                .when().get("https://www.google.com/");

        response.then().header("Content-Type", "text/html; charset=ISO-8859-1")
                .header("Content-Encoding", "gzip")
                .header("Server", "gws")
                .log().headers();

}

}
