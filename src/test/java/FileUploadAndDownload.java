import org.testng.annotations.Test;
import java.io.File;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class FileUploadAndDownload {

    @Test
    void singleFileUpload() {

        File myfile = new File("C:\\Users\\azima\\OneDrive\\Documents\\students.json");
        given()
                //form-data
                .multiPart("file", myfile).contentType("multipart/form-data")
                .when().post("http://upload").
                then().statusCode(200).body("filename", equalTo("students.json")).log().all();
    }

    @Test
    void multiFileUpload() {

        File myfile = new File("C:\\Users\\azima\\OneDrive\\Documents\\students.json");
        File myfile2 = new File("C:\\Users\\azima\\OneDrive\\Documents\\students.json");

     //   File file[] = {myfile2, myfile};
        given()
                /*.multiPart("files", file) - */

                //form-data
                .multiPart("files", myfile)
                .multiPart("files", myfile2).contentType("multipart/form-data")
                .when().post("http://upload").
                then().statusCode(200).body("filename", equalTo("students.json")).log().all();
    }
}
