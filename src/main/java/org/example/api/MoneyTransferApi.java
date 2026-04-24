package org.example.api;
import io.restassured.RestAssured;
import org.example.models.MoneyTransferSystem;
import java.util.Arrays;
import java.util.List;
import org.example.data.Constants;

public class MoneyTransferApi {



    public static List<MoneyTransferSystem> getMoneyTransferSystems() {

        MoneyTransferSystem[] response = RestAssured
                .given()
                .baseUri(Constants.BASE_URL)
                .basePath(Constants.SYSTEMS_ENDPOINT)
                .queryParam("locale", "ka-GE")
                .header("accept", "application/json")
                .when()
                .get()
                .then()
                .statusCode(200)
                .contentType("application/json")
                .extract()
                .as(MoneyTransferSystem[].class);

        return Arrays.asList(response);
    }
}