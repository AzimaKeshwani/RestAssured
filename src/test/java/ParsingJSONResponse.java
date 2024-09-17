import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class ParsingJSONResponse {

    @Test(priority = 1)
    void testJSONResponseData() {

        Response response = given().contentType(ContentType.JSON)
                .when().get("http://localhost:3000/books");

        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200 but got " + response.getStatusCode());
        Assert.assertEquals(response.getHeader("Content-Type"), "application/json");
        Assert.assertEquals(response.jsonPath().get("[3].title").toString(), "The Lord of the Rings");

        //if dynamic and want to find if title is present
        JSONArray js = new JSONArray(response.asString());
        boolean foundBook = false;
        for (int i = 0; i < js.length(); i++) {
            String bookTitle = js.getJSONObject(i).get("title").toString();
            String bookPrice = js.getJSONObject(i).get("price").toString();

            if (bookTitle.equals("The Lord of the Rings")) {
                foundBook = true;
                System.out.println(bookTitle + " " + bookPrice);
                break;
            }
        }

        Assert.assertTrue(foundBook, "Book is not found!");

        double sum = 0;
        for (int i = 0; i < js.length(); i++) {
            String bookPrice = js.getJSONObject(i).get("price").toString();
            sum += Double.parseDouble(bookPrice);
        }
        Assert.assertEquals(sum, 411.0);
    }
}
