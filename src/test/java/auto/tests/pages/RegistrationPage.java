package auto.tests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class RegistrationPage extends BasePage {

    public RegistrationPage(WebDriver driver) {
        super(driver);
    }

    //Локаторы
    private final By registerButton = By.cssSelector(".ico-register");
    private final By gender = By.id("gender-male");
    private final By firstNameField = By.id("FirstName");
    private final By lastNameField = By.id("LastName");
    private final By emailField = By.id("Email");
    private final By passwordField = By.id("Password");
    private final By confirmPasswordField = By.id("ConfirmPassword");
    private final By endRegisterButton = By.id("register-button");
    private final By continueButton = By.xpath("//input[@class='button-1 register-continue-button']");
    private final By successMessage = By.className("result");
    private final By errorMessage = By.className("field-validation-error");

    public void endRegistration() {
        click(endRegisterButton);
    }

    public void registerUser(String firstName, String lastName, String email, String password, String confirmPassword) {
        click(gender);
        type(firstNameField, firstName);
        type(lastNameField, lastName);
        type(emailField, email);
        type(passwordField, password);
        type(confirmPasswordField, confirmPassword);
        click(endRegisterButton);
    }

    public void clickContinue() {
        click(continueButton);
    }

    public boolean isRegistrationSuccessful() {
        return getText(successMessage).contains("Your registration completed");
    }

    public int getErrorCount() {
        return findAll(errorMessage).size();
    }

    public boolean isPasswordDoNotMatch() {
        return getText(errorMessage).contains("The password and confirmation password do not match.");
    }

    public boolean isWrongEmail() {
        return getText(errorMessage).contains("Wrong email");
    }

    public boolean isEmptyName() {
        return getText(errorMessage).contains("First name is required.");
    }

    public boolean isEmptyLastName() {
        return getText(errorMessage).contains("Last name is required.");
    }

    public boolean isEmptyEmail() {
        return getText(errorMessage).contains("Email is required.");
    }

    public boolean isPasswordInvalidate() {
        return getText(errorMessage).contains("The password should have at least 6 characters.");
    }
}
