package auto.tests.framework.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

public final class DriverFactory {

    private DriverFactory() {
    }

    public static WebDriver createDriver() throws Exception {
        String browser = System.getenv().getOrDefault("BROWSER", "chrome").toLowerCase();
        String remoteUrl = System.getenv("SELENIUM_REMOTE_URL");
        boolean ci = System.getenv("CI") != null;

        if (remoteUrl != null && !remoteUrl.isBlank()) {
            return createRemoteDriver(browser, remoteUrl);
        }
        return createLocalDriver(browser, ci);
    }

    private static WebDriver createLocalDriver(
            String browser, boolean ci) {

        switch (browser) {

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

    private static WebDriver createRemoteDriver(
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

                throw new IllegalArgumentException(
                        "Unsupported remote browser: " + browser
                );
        }
    }
}