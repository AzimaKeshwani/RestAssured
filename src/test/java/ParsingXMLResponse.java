import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ParsingXMLResponse {

    @Test
    void testXMLResponse() {
        /*given().
                when().get("http://restapi.adequateshop.com/api/Traveler?page=1")
                .then().statusCode(200)
                .header("Content-type", "application/xml")
                .body("TravelerinformationResponse.page", equalTo(1)).
                body("TravelerinformationResponse.travelers.Travelinformation[0].id", equalTo(3246));;
    */

        Response response = given().
                when().get("http://restapi.adequateshop.com/api/Traveler?page=1");

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.header("Content-Type"), equalTo("application/xml"));
        Assert.assertEquals(response.xmlPath().get("TravelerinformationResponse.page").toString(), 1);
        Assert.assertEquals(response.xmlPath().get("TravelerinformationResponse.travelers.Travelinformation[0].id").toString(), equalTo(3246));

    }

    @Test
    void testXMLResponseBody() {
        Response response = given().
                when().get("http://restapi.adequateshop.com/api/Traveler?page=1");

        XmlPath xmlPath = new XmlPath(response.asString());

        //Verify total number of travellers
        List<String> travellers = xmlPath.getList("TravelerinformationResponse.travelers.Travelinformation"); //will get multiple nodes
        Assert.assertEquals(travellers.size(), 10);
    }

    @Test
    void testDynamicResponse() {
        Response response = given().
                when().get("http://restapi.adequateshop.com/api/Traveler?page=1");

        XmlPath xmlPath = new XmlPath(response.asString());
        List<String> travellers_Names = xmlPath.getList("TravelerinformationResponse.travelers.Travelinformation.name");
        boolean status = false;
        for (String name : travellers_Names) {
            if (name.equals("Vijay Bharat Reddy")) {
                status = true;
                break;
            }
        }
        Assert.assertTrue(status, "Name not found");

    }

}
