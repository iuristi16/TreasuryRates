import org.example.api.TreasuryApi;
import org.example.models.ForwardRatesResponse;
import org.example.models.Rate;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class TreasuryApiTest {

    @Test
    public void validateResponseStructure() {

        SoftAssert softAssert = new SoftAssert();

        ForwardRatesResponse response = TreasuryApi.getForwardRates();


        softAssert.assertNotNull(response, "Response is null");
        softAssert.assertNotNull(response.getRates(), "Rates is null");
        softAssert.assertNotNull(response.getUpdateDate(), "Update date is null");

        softAssert.assertAll();

    }

    @Test
    public void validateUSDGELPair() {

        SoftAssert softAssert = new SoftAssert();

        ForwardRatesResponse response = TreasuryApi.getForwardRates();

        Rate usdRate = response.getRates()
                .stream()
                .filter(r -> "USD".equals(r.getIso()))
                .findFirst()
                .orElse(null);

        softAssert.assertNotNull(usdRate, "USD rate not found");

        if (usdRate != null) {
            usdRate.getForwardRates().forEach(rate -> {
                softAssert.assertEquals(rate.getIso1(), "USD", "iso1 mismatch");
                softAssert.assertEquals(rate.getIso2(), "GEL", "iso2 mismatch");
            });
        }

        softAssert.assertAll();
    }

    @Test
    public void validateNumericFields() {

        SoftAssert softAssert = new SoftAssert();

        ForwardRatesResponse response = TreasuryApi.getForwardRates();

        response.getRates().forEach(rate -> {
            rate.getForwardRates().forEach(fr -> {

                softAssert.assertTrue(fr.getBidForwardRate() > 0, "Invalid bid rate");
                softAssert.assertTrue(fr.getAskForwardRate() > 0, "Invalid ask rate");

                softAssert.assertTrue(fr.getBidForwardInterest() >= 0, "Invalid bid interest");
                softAssert.assertTrue(fr.getAskForwardInterest() >= 0, "Invalid ask interest");

                softAssert.assertTrue(fr.getDay() > 0, "Invalid day");
            });
        });

        softAssert.assertAll();
    }

}