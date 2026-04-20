package org.example.api;
import io.restassured.RestAssured;
import org.example.models.ForwardRatesResponse;

public class TreasuryApi {

    private static final String BASE_URL = "https://apigw.tbcbank.ge";
    private static final String FORWARD_RATES_ENDPOINT =
            "/api/v1/forwardRates/getForwardRates";

    public static ForwardRatesResponse getForwardRates() {
        return RestAssured
                .given()
                .baseUri(BASE_URL)
                .basePath(FORWARD_RATES_ENDPOINT)
                .queryParam("locale", "ka-GE")
                .header("accept", "application/json")
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract()
                .as(ForwardRatesResponse.class);
    }
}