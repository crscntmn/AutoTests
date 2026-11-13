package auto.tests.framework.base;

import auto.tests.framework.config.Config;
import auto.tests.framework.driver.DriverFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;

public class BaseTest {

    protected WebDriver driver;

    @BeforeEach
    void openPage() throws Exception {
        driver = DriverFactory.createDriver();
        driver.manage().window().setSize(new Dimension(1920, 1080));
        driver.get(Config.getBaseUrl());
    }

    @AfterEach
    void endTest() {

        if (driver != null) {
            driver.quit();
        }
    }
}