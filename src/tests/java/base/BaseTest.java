package base;

import auto.tests.config.Config;
import auto.tests.pages.LoginPage;
import auto.tests.pages.RegistrationPage;
import auto.tests.testdata.TestData;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BaseTest {

    protected WebDriver driver;

    @BeforeEach
    void openPage() {
        driver = new ChromeDriver();
        driver.manage().window().setSize(new Dimension(1920, 1080));
        driver.get(Config.URL);
    }

    @AfterEach
    void endTest() {
        driver.quit();
    }

    protected String registerNewUser() {
        RegistrationPage registrationPage = new RegistrationPage(driver);
        LoginPage loginPage = new LoginPage(driver);
        String email = TestData.generateEmail();
        registrationPage.openRegistration();
        registrationPage.registerUser(TestData.NAME, TestData.LASTNAME, email, TestData.PASSWORD, TestData.PASSWORD);
        registrationPage.clickContinue();
        loginPage.logout();
        return email;
    }
}
