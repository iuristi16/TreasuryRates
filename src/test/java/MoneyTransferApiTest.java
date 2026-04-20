import org.example.api.MoneyTransferApi;
import org.example.models.MoneyTransferSystem;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;

public class MoneyTransferApiTest {

    @Test
    public void validateResponseStructure() {

        SoftAssert softAssert = new SoftAssert();

        List<MoneyTransferSystem> systems =
                MoneyTransferApi.getMoneyTransferSystems();

        softAssert.assertNotNull(systems, "Response is null");
        softAssert.assertFalse(systems.isEmpty(), "Systems list is empty");

        systems.forEach(system -> {

            // ✅ FIELD VALIDATION (ეს აკლდა სწორად)
            softAssert.assertNotNull(system.getMtSystem(), "mtSystem is null");
            softAssert.assertFalse(system.getMtSystem().isBlank(), "mtSystem is empty");

            softAssert.assertNotNull(system.getName(), "name is null");
            softAssert.assertFalse(system.getName().isBlank(), "name is empty");

            softAssert.assertNotNull(system.getImageUrl(), "imageUrl is null");
            softAssert.assertFalse(system.getImageUrl().isBlank(), "imageUrl is empty");

            softAssert.assertNotNull(system.getCurrencies(), "currencies is null");
            softAssert.assertFalse(system.getCurrencies().isEmpty(), "currencies is empty");
        });

        softAssert.assertAll();
    }

    @Test
    public void validateBusinessRules() {

        SoftAssert softAssert = new SoftAssert();

        List<MoneyTransferSystem> systems =
                MoneyTransferApi.getMoneyTransferSystems();

        systems.forEach(system -> {

            // mtSystem == name
            softAssert.assertEquals(
                    system.getMtSystem(),
                    system.getName(),
                    "mtSystem and name mismatch"
            );

            // currencies არ უნდა იყოს ცარიელი
            softAssert.assertTrue(
                    system.getCurrencies().size() > 0,
                    "Currencies list is empty"
            );

            // image უნდა იყოს jpg
            softAssert.assertTrue(
                    system.getImageUrl().endsWith(".jpg"),
                    "Invalid image format"
            );
        });

        softAssert.assertAll();
    }

    @Test
    public void validateCurrenciesContent() {

        SoftAssert softAssert = new SoftAssert();

        List<MoneyTransferSystem> systems =
                MoneyTransferApi.getMoneyTransferSystems();

        systems.forEach(system -> {

            // ყველა სისტემას უნდა ჰქონდეს მინიმუმ EUR ან USD
            softAssert.assertTrue(
                    system.getCurrencies().contains("EUR") ||
                            system.getCurrencies().contains("USD"),
                    "Missing main currency"
            );

            // კონკრეტული case (მაგ: ZolotayaKorona)
            if ("ZolotayaKorona".equals(system.getName())) {
                softAssert.assertTrue(
                        system.getCurrencies().contains("RUB"),
                        "ZolotayaKorona should support RUB"
                );
            }
        });

        softAssert.assertAll();
    }

    @Test
    public void validateSystemCount() {

        SoftAssert softAssert = new SoftAssert();

        List<MoneyTransferSystem> systems =
                MoneyTransferApi.getMoneyTransferSystems();

        // შენი response-ის მიხედვით
        softAssert.assertEquals(
                systems.size(),
                7,
                "Unexpected number of systems"
        );

        softAssert.assertAll();
    }

    @Test
    public void validateStatusCode() {

        int statusCode = io.restassured.RestAssured
                .given()
                .baseUri("https://tbcbank.ge")
                .basePath("/api/money-transfer/systems")
                .when()
                .get()
                .getStatusCode();

        org.testng.Assert.assertEquals(statusCode, 200);
    }

    @Test
    public void debugResponse() {

        var response = io.restassured.RestAssured
                .given()
                .baseUri("https://apigw.tbcbank.ge")
                .basePath("/api/v1/moneyTransfer/systems?locale=ka-GE")
                .when()
                .get();

        System.out.println("STATUS: " + response.getStatusCode());
        System.out.println("CONTENT TYPE: " + response.getContentType());
        System.out.println("BODY: " + response.getBody().asString());
    }
}