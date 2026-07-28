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
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(emailField));
    }

    public void loginUser(String email, String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(emailField)).sendKeys(email);
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(rememberMe).click();
        driver.findElement(endLoginButton).click();
    }

    public void logout() {
        wait.until(ExpectedConditions.elementToBeClickable(logout)).click();
        //Нажатие кнопки logout
    }

    public void clickEndLoginButton() {
        wait.until(ExpectedConditions.elementToBeClickable(endLoginButton)).click();
        //Нажатие кнопки Log in
    }

    public void clickForgotPassword() {
        wait.until(ExpectedConditions.elementToBeClickable(forgotPasswordButton)).click();
        //Нажатие кнопки ForgotPassword
    }

    public void inputEmailInForgotPassword(String email) {
        driver.findElement(emailField).sendKeys(email);
    }

    public void clickRecoverButton() {
        driver.findElement(RecoverButton).click();
    }

    public String isAuthorizationSuccessful() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(accountLink)).getText();
        //Успешная авторизация
    }

    public boolean isAuthorizationUnsuccessful() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessageIncorrectData)).isDisplayed();
        //Не успешная авторизация
    }

    public boolean isInvalidEmailErrorDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessageInvalidEmail)).isDisplayed();
        //Отображение ошибки неверного email
    }

    public boolean emailIsNotFound() {
        String text = wait.until(ExpectedConditions.visibilityOfElementLocated(errorEmailNotFound)).getText();
        return text.contains("Email not found.");
    }

    public boolean sentInstructionInEmail() {
        String text = wait.until(ExpectedConditions.visibilityOfElementLocated(errorEmailNotFound)).getText();
        return text.contains("Email with instructions has been sent to you.");
    }
}
