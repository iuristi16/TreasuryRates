package automationTestTreasury;
import com.microsoft.playwright.*;
import org.example.api.TreasuryApi;
import org.example.models.ForwardRate;
import org.example.models.ForwardRatesResponse;
import org.example.models.Rate;
import org.testng.asserts.SoftAssert;
import org.testng.annotations.Test;

import java.util.List;

public class TreasuryUiTest {

    @Test
    public void compareUIWithAPI() {

        SoftAssert softAssert = new SoftAssert();

        ForwardRatesResponse apiData = TreasuryApi.getForwardRates();

        try (Playwright playwright = Playwright.create()) {

            Browser browser = playwright.chromium()
                    .launch(new BrowserType.LaunchOptions().setHeadless(false));

            Page page = browser.newPage();
            page.navigate("https://tbcbank.ge/ka/treasury-products");

            page.waitForTimeout(3000);

            List<String> allPeriods = page
                    .locator(".tbcx-pw-table-cell__content__title")
                    .allTextContents()
                    .stream()
                    .map(String::trim)
                    .filter(text -> text.matches("\\d+\\s+(კვირა|თვე|წელი)"))
                    .toList();
            List<String> uiPeriods = allPeriods.subList(9, 18); // USD
            System.out.println("USD PERIODS: " + uiPeriods);


            List<String> allRates = page
                    .locator(".tbcx-pw-table-cell__content__title")
                    .allTextContents()
                    .stream()
                    .map(String::trim)
                    // 🔥 ვტოვებთ მხოლოდ რიცხვებს
                    .filter(text -> text.matches("\\d+\\.\\d+"))
                    .toList();
            System.out.println("ALL RATES: " + allRates);


            // ✔ USD rate
            Rate usdRate = apiData.getRates()
                    .stream()
                    .filter(r -> "USD".equals(r.getIso()))
                    .findFirst()
                    .orElse(null);

            softAssert.assertNotNull(usdRate, "USD data not found in API");

            if (usdRate != null) {

                List<ForwardRate> apiRates = usdRate.getForwardRates();

                // ✅ 1. SIZE validation
                softAssert.assertTrue(
                        uiPeriods.size() > 0,
                        "UI periods list is empty"
                );

                softAssert.assertEquals(
                        uiPeriods.size(),
                        apiRates.size(),
                        "UI vs API size mismatch"
                );

                for (int i = 0; i < Math.min(uiPeriods.size(), apiRates.size()); i++) {

                    ForwardRate apiRate = apiRates.get(i);
                    System.out.println(
                            "Currency Pair: " +
                                    apiRate.getIso1() + "/" + apiRate.getIso2()
                    );

                    String expectedPeriod = apiRate.getPeriod();
                    String actualPeriod = uiPeriods.get(i);

                    // ✅ 2. PERIOD validation
                    softAssert.assertEquals(
                            actualPeriod.trim(),
                            expectedPeriod.trim(),
                            "Period mismatch at index " + i
                    );

                    // ✅ 3. CURRENCY PAIR validation
                    softAssert.assertEquals(apiRate.getIso1(), "USD", "iso1 mismatch");
                    softAssert.assertEquals(apiRate.getIso2(), "GEL", "iso2 mismatch");

                    // ✅ 4. NUMERIC validation
                    softAssert.assertTrue(apiRate.getBidForwardRate() > 0, "Invalid bid rate");
                    softAssert.assertTrue(apiRate.getAskForwardRate() > 0, "Invalid ask rate");
                    softAssert.assertTrue(apiRate.getDay() > 0, "Invalid day value");
                }
            }

            browser.close();
        }

        softAssert.assertAll();
    }
}