package base;

import auto.tests.config.Config;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.net.URL;

public class BaseTest {

    protected WebDriver driver;

    @BeforeEach
    void openPage() throws Exception {

        String browser = System.getenv().getOrDefault("BROWSER", "chrome").toLowerCase();
        String remoteUrl = System.getenv("SELENIUM_REMOTE_URL");
        boolean ci = System.getenv("CI") != null;
        if (remoteUrl != null && !remoteUrl.isBlank()) {
            driver = createRemoteDriver(browser, remoteUrl);
        } else {
            driver = createLocalDriver(browser, ci);
        }

        driver.manage().window().setSize(new Dimension(1920, 1080));
        driver.get(Config.URL);
    }

    private WebDriver createLocalDriver(String browser, boolean ci) {

        switch (browser) {

            case "safari":

                return new SafariDriver();

            case "chrome":

                ChromeOptions chromeOptions = new ChromeOptions();

                if (ci) {
                    chromeOptions.addArguments("--headless=new");
                    chromeOptions.addArguments("--no-sandbox");
                    chromeOptions.addArguments("--disable-dev-shm-usage");
                }

                return new ChromeDriver(chromeOptions);

            default:

                throw new IllegalArgumentException(
                        "Unsupported browser: " + browser
                );
        }
    }

    private WebDriver createRemoteDriver(
            String browser,
            String remoteUrl
    ) throws Exception {

        switch (browser) {
            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();
                return new RemoteWebDriver(
                        new URL(remoteUrl),
                        chromeOptions
                );

            default:
                throw new IllegalArgumentException("Unsupported remote browser: " + browser);
        }
    }

    @AfterEach
    void endTest() {

        if (driver != null) {
            driver.quit();
        }
    }
}