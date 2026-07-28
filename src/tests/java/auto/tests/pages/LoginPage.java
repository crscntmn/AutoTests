package auto.tests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage {

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    //Локаторы
    private final By loginButton = By.cssSelector(".ico-login");
    private final By emailField = By.id("Email");
    private final By passwordField = By.id("Password");
    private final By rememberMe = By.id("RememberMe");
    private final By endLoginButton = By.cssSelector(".button-1.login-button");
    private final By forgotPasswordButton = By.xpath("//a[text()='Forgot password?']");
    private final By accountLink = By.cssSelector(".header-links a.account");
    private final By logout = By.cssSelector(".ico-logout");
    private final By errorMessageIncorrectData = By.cssSelector(".validation-summary-errors");
    private final By errorMessageInvalidEmail = By.cssSelector(".field-validation-error");
    private final By RecoverButton = By.name("send-email");
    private final By errorEmailNotFound = By.cssSelector(".result");

    public void openAuthorization() {
        click(loginButton);
        waitVisible(emailField);
    }

    public void loginUser(String email, String password) {
        type(emailField, email);
        type(passwordField, password);
        click(rememberMe);
        click(endLoginButton);
    }

    public void logout() {
        click(logout);
    }

    public void clickEndLoginButton() {
        click(endLoginButton);
    }

    public void clickForgotPassword() {
        click(forgotPasswordButton);
    }

    public void inputEmailInForgotPassword(String email) {
        type(emailField, email);
    }

    public void clickRecoverButton() {
        click(RecoverButton);
    }

    public String isAuthorizationSuccessful() {
        return getText(accountLink);
    }

    public boolean isAuthorizationUnsuccessful() {
        return isDisplayed(errorMessageIncorrectData);
    }

    public boolean isInvalidEmailErrorDisplayed() {
        return isDisplayed(errorMessageInvalidEmail);
    }

    public boolean emailIsNotFound() {
        return getText(errorEmailNotFound).contains("Email not found.");
    }

    public boolean sentInstructionInEmail() {
        return getText(errorEmailNotFound).contains("Email with instructions has been sent to you.");
    }
}
