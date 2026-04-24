package org.example.api;
import io.restassured.RestAssured;
import org.example.models.MoneyTransferSystem;
import java.util.Arrays;
import java.util.List;

public class MoneyTransferApi {

    private static final String BASE_URL = "https://apigw.tbcbank.ge";
    private static final String SYSTEMS_ENDPOINT =
            "/api/v1/moneyTransfer/systems";

    public static List<MoneyTransferSystem> getMoneyTransferSystems() {

        MoneyTransferSystem[] response = RestAssured
                .given()
                .baseUri(BASE_URL)
                .basePath(SYSTEMS_ENDPOINT)
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