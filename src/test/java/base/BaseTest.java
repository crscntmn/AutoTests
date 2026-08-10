package base;

import auto.tests.config.Config;
import auto.tests.testdata.TestData;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.chrome.ChromeOptions;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class BaseTest {

    protected WebDriver driver;

    @BeforeEach
    void openPage() throws Exception {

        ChromeOptions options = new ChromeOptions();

        // Headless для CI и Docker
        if (System.getenv("CI") != null || System.getenv("SELENIUM_REMOTE_URL") != null) {
            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
        }

        String remoteUrl = System.getenv("SELENIUM_REMOTE_URL");

        if (remoteUrl != null && !remoteUrl.isBlank()) {
            // Docker / Selenium Grid
            driver = new RemoteWebDriver(new URL(remoteUrl), options);
        } else {
            // Локальный запуск
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver(options);
        }

        driver.manage().window().setSize(new Dimension(1920, 1080));
        driver.get(Config.URL);
    }

    @AfterEach
    void endTest() {
        if (driver != null){
            driver.quit();
        }
    }
}
