package base;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeMethod;
import static utils.ConfigReader.get;

public class TestBase {


    @BeforeMethod

    public void setUp(){
        RestAssured.baseURI = get("baseUrl");


        System.out.println("✅ Test setup completed");
    }
}
