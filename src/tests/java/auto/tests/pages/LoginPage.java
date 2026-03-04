package auto.tests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    //Локаторы
    private By LoginButton = By.cssSelector(".ico-login");
    private By EmailField = By.id("Email");
    private By PasswordField = By.id("Password");
    private By RememberMe = By.id("RememberMe");
    private By EndLoginButton = By.cssSelector(".button-1.login-button");
    private By ForgotPasswordButton = By.cssSelector(".forgot-password");
    private By accountLink = By.cssSelector(".header-links a.account");

    public void openAuthorization() {
        wait.until(ExpectedConditions.elementToBeClickable(LoginButton)).click();
        wait.until(ExpectedConditions.elementToBeClickable(EmailField));
    }

    public void LoginUser(String email, String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(EmailField)).sendKeys(email);
        driver.findElement(PasswordField).sendKeys(password);
        driver.findElement(RememberMe).click();
        driver.findElement(EndLoginButton).click();
    }
}
