package baseTest;
import com.microsoft.playwright.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    protected Playwright playwright;
    protected Browser browser;
    protected Page page;

    @BeforeMethod
    public void setUp() {

        playwright = Playwright.create();

        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions()
                        .setHeadless(false)
        );
        page = browser.newPage();
        page.navigate("https://tbcbank.ge/ka/other-products/money-transfers");
        //page.locator(".tbcx-pw-card__logo-and-text-info").first().waitFor();
    }

    @AfterMethod
    public void tearDown() {
        browser.close();
        playwright.close();
    }
}