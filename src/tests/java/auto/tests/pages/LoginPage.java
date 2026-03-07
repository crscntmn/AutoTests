package auto.tests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage extends BasePage {

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    //Локаторы
    private By loginButton = By.cssSelector(".ico-login");
    private By emailField = By.id("Email");
    private By passwordField = By.id("Password");
    private By rememberMe = By.id("RememberMe");
    private By endLoginButton = By.cssSelector(".button-1.login-button");
    private By forgotPasswordButton = By.cssSelector(".forgot-password");
    private By accountLink = By.cssSelector(".header-links a.account");
    private By logout = By.cssSelector(".ico-logout");

    public void openAuthorization() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(emailField));
    }

    public void loginUser(String email, String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(emailField)).sendKeys(email);
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(rememberMe).click();
        driver.findElement(endLoginButton).click();
    }

    public void logout(){
        driver.findElement(logout).click();
    }

    public String isAuthorizationSuccessful() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(accountLink)).getText();
    }
}
