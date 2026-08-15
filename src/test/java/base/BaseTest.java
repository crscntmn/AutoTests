package base;

import auto.tests.config.Config;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

public class BaseTest {

    protected WebDriver driver;

    @BeforeEach
    void openPage() throws Exception {

        String browser = System.getenv().getOrDefault("BROWSER", "chrome").toLowerCase();
        String remoteUrl = System.getenv("SELENIUM_REMOTE_URL");

        boolean ci = System.getenv("CI") != null;

        driver = createDriver(browser, remoteUrl, ci);

        driver.manage().window().setSize(new Dimension(1920, 1080));
        driver.get(Config.URL);
    }

    private WebDriver createDriver(
            String browser,
            String remoteUrl,
            boolean ci
    ) throws Exception {

        if (remoteUrl != null && !remoteUrl.isBlank()) {
            return createRemoteDriver(browser, remoteUrl, ci);
        }

        return createLocalDriver(browser, ci);
    }

    private WebDriver createRemoteDriver(
            String browser,
            String remoteUrl,
            boolean ci
    ) throws Exception {

        return switch (browser) {

            case "chrome" -> {
                ChromeOptions options = new ChromeOptions();

                if (ci) {
                    options.addArguments("--headless=new");
                    options.addArguments("--no-sandbox");
                    options.addArguments("--disable-dev-shm-usage");
                }

                yield new RemoteWebDriver(
                        new URL(remoteUrl),
                        options
                );
            }

            case "firefox" -> {
                FirefoxOptions options = new FirefoxOptions();

                if (ci) {
                    options.addArguments("-headless");
                }

                yield new RemoteWebDriver(
                        new URL(remoteUrl),
                        options
                );
            }

            case "edge" -> {
                EdgeOptions options = new EdgeOptions();

                if (ci) {
                    options.addArguments("--headless=new");
                    options.addArguments("--no-sandbox");
                    options.addArguments("--disable-dev-shm-usage");
                }

                yield new RemoteWebDriver(
                        new URL(remoteUrl),
                        options
                );
            }

            default -> throw new IllegalArgumentException(
                    "Unsupported browser: " + browser
            );
        };
    }

    private WebDriver createLocalDriver(
            String browser,
            boolean ci
    ) {

        return switch (browser) {

            case "chrome" -> {
                WebDriverManager.chromedriver().setup();

                ChromeOptions options = new ChromeOptions();

                if (ci) {
                    options.addArguments("--headless=new");
                    options.addArguments("--no-sandbox");
                    options.addArguments("--disable-dev-shm-usage");
                }

                yield new ChromeDriver(options);
            }

            case "firefox" -> {
                WebDriverManager.firefoxdriver().setup();

                FirefoxOptions options = new FirefoxOptions();

                if (ci) {
                    options.addArguments("-headless");
                }

                yield new FirefoxDriver(options);
            }

            case "edge" -> {
                WebDriverManager.edgedriver().setup();

                EdgeOptions options = new EdgeOptions();

                if (ci) {
                    options.addArguments("--headless=new");
                    options.addArguments("--no-sandbox");
                    options.addArguments("--disable-dev-shm-usage");
                }

                yield new EdgeDriver(options);
            }

            default -> throw new IllegalArgumentException(
                    "Unsupported browser: " + browser
            );
        };
    }

    @AfterEach
    void endTest() {
        if (driver != null) {
            driver.quit();
        }
    }
}