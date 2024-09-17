import io.restassured.response.Response;
import org.testng.annotations.Test;
import java.util.Map;
import static io.restassured.RestAssured.given;

public class Cookies {

    @Test
    void getCookiesInfo() {
        Response response = given()
                .when().get("https://www.google.com/");

        String Cookie = response.getCookie("AEC");
        Map<String, String> Cookies = response.getCookies();

        for(String a: Cookies.keySet()){
            String cookie = response.getCookie(a);
            System.out.println(a + " " + cookie);
        }

        response.then()
                .statusCode(200).log().cookies();
    }
}
