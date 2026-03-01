package base;

import auto.tests.config.Config;
import auto.tests.testdata.TestData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class BaseTest {

    protected WebDriver driver;
    protected WebDriverWait wait;

    @BeforeEach
    void openPage() {
        driver = new ChromeDriver();
        driver.manage().window().setSize(new Dimension(1920, 1080));
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get(Config.URL);
    }

    @AfterEach
    void endTest() {
        driver.quit();
    }
}
