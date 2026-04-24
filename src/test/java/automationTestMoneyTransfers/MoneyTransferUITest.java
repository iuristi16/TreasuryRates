package automationTestMoneyTransfers;
import org.example.api.MoneyTransferApi;
import org.example.models.MoneyTransferSystem;
import baseTest.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import java.util.List;
import java.util.stream.Collectors;

public class MoneyTransferUITest  extends BaseTest {

    @Test
    public void compareNameUIWithAPI() {

        SoftAssert softAssert = new SoftAssert();


        List<MoneyTransferSystem> apiSystems =
                MoneyTransferApi.getMoneyTransferSystems();




            List<String> uiNames = page
                    .locator(".tbcx-pw-card__logo-and-text-info")
                    .allTextContents()
                    .stream()
                    .map(String::trim)
                    .collect(Collectors.toList());

            System.out.println("UI SYSTEM NAMES: " + uiNames);


            List<String> apiNames = apiSystems.stream()
                    .map(MoneyTransferSystem::getName)
                    .collect(Collectors.toList());

            System.out.println("API SYSTEM NAMES: " + apiNames);


            softAssert.assertTrue(
                    uiNames.size() > 0,
                    "UI systems list is empty"
            );

            softAssert.assertEquals(
                    uiNames.size(),
                    apiNames.size(),
                    "UI vs API systems count mismatch"
            );


            for (int i = 0; i < Math.min(uiNames.size(), apiNames.size()); i++) {

                String uiName = uiNames.get(i);
                String apiName = apiNames.get(i);

                softAssert.assertEquals(
                        uiName,
                        apiName,
                        "System name mismatch at index " + i
                );
            }

            apiNames.forEach(apiName ->
                    softAssert.assertTrue(
                            uiNames.contains(apiName),
                            "UI missing system: " + apiName
                    )
            );

            List<String> uiCurrencies = page
                    .locator(".tbcx-pw-card__caption.ng-star-inserted")
                    .allTextContents();
            System.out.println();
            boolean allNotEmpty = uiCurrencies.stream()
                    .allMatch(text -> !text.trim().isEmpty());

            if (!allNotEmpty) {
                throw new AssertionError("Some currency texts are empty!");
            }

            List<String> currencyNames = apiSystems.stream()
                    .map(e->String.join("/",e.getCurrencies()))
                    .collect(Collectors.toList());

            List<String> uiCurrencyNames = page
                    .locator("//span[@class='tbcx-pw-card__caption ng-star-inserted']")
                    .allTextContents().stream()
                    .map(e->e.trim().split("-")[1].trim())
                    .collect(Collectors.toList());
            System.out.println("UI Currencies: " + uiCurrencies);

            Assert.assertEquals(uiCurrencyNames, currencyNames);
        }
    }
