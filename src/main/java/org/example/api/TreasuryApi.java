package org.example.api;
import io.restassured.RestAssured;
import org.example.models.ForwardRatesResponse;
import org.example.data.Constants;

public class TreasuryApi {



    public static ForwardRatesResponse getForwardRates() {
        return RestAssured
                .given()
                .baseUri(Constants.BASE_URL)
                .basePath(Constants.FORWARD_RATES_ENDPOINT)
                .queryParam("locale", "ka-GE")
                .header("accept", "application/json")
                .when()
                .get()
                .then()
                .statusCode(200)
                .contentType("application/json")
                .extract()
                .as(ForwardRatesResponse.class);
    }
}