package automationTestTreasury;
import basetreasury.TreasuryBaseTest;
import com.microsoft.playwright.*;
import org.example.api.TreasuryApi;
import org.example.models.ForwardRate;
import org.example.models.ForwardRatesResponse;
import org.example.models.Rate;
import org.testng.asserts.SoftAssert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.stream.Collectors;

public class TreasuryUiTest extends TreasuryBaseTest {

    @Test
    public void compareUIWithAPI() {
        SoftAssert softAssert = new SoftAssert();
        ForwardRatesResponse apiData = TreasuryApi.getForwardRates();




            List<String> uiPeriods = page
                    .locator(".tbcx-pw-table-cell__content__title")
                    .allTextContents()
                    .stream()
                    .map(String::trim)
                    .filter(text -> text.matches("\\d+\\s+(კვირა|თვე|წელი)"))
                    .collect(Collectors.toList());
            System.out.println("UI PERIODS: " + uiPeriods);


            List<Double> uiRates = page
                    .locator(".tbcx-pw-table-cell__content__title")
                    .allTextContents()
                    .stream()
                    .map(String::trim)
                    .filter(text -> text.matches("\\d+\\.\\d+"))
                    .map(Double::parseDouble)
                    .collect(Collectors.toList());
            System.out.println("UI RATES: " + uiRates);


            boolean hasUsdGel = page.content().contains("USD/GEL");
            softAssert.assertTrue(
                    hasUsdGel,
                    "USD/GEL pair not found in UI"
            );

            Rate usdRate = apiData.getRates()
                    .stream()
                    .filter(r -> "USD".equals(r.getIso()))
                    .findFirst()
                    .orElse(null);

            softAssert.assertNotNull(usdRate, "USD data not found in API");

            if (usdRate != null) {

                List<ForwardRate> apiRates = usdRate.getForwardRates();
                apiRates.forEach(apiRate -> {
                    softAssert.assertTrue(
                            uiPeriods.contains(apiRate.getPeriod()),
                            "Missing period in UI: " + apiRate.getPeriod()
                    );
                });

                List<String> uiPairs = page
                        .locator(".business-treasury-product-table__title.ng-star-inserted")
                        .allTextContents()
                        .stream()
                        .map(String::trim)
                        .toList();
                System.out.println("UI PAIRS: " + uiPairs);


                List<String> apiPairs = apiRates.stream()
                        .map(r -> r.getIso1() + "/" + r.getIso2())
                        .distinct()
                        .toList();
                System.out.println("API PAIRS: " + apiPairs);


                apiPairs.forEach(pair -> {
                    softAssert.assertTrue(
                            uiPairs.contains(pair),
                            "Missing currency pair in UI: " + pair);});

                List<Double> apiBidRates = apiRates.stream()
                        .map(ForwardRate::getBidForwardRate)
                        .collect(Collectors.toList());

                softAssert.assertTrue(
                        uiRates.containsAll(apiBidRates),
                        "UI missing some bid rates"
                );


                apiRates.forEach(rate -> {
                    softAssert.assertTrue(rate.getBidForwardRate() > 0);
                    softAssert.assertTrue(rate.getAskForwardRate() > 0);
                    softAssert.assertTrue(rate.getDay() > 0);});}


        }


    }
