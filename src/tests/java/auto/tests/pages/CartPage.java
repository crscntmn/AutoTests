package auto.tests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CartPage extends BasePage {

    public CartPage(WebDriver driver) {
        super(driver);
    }

    //Локаторы
    private final By checkoutButton = By.cssSelector(".button-1.checkout-button");
    private final By cartButton = By.cssSelector(".cart-qty");
    private final By successfulAddToCart = By.cssSelector(".bar-notification.success");
    private final By termsOfServiceError = By.xpath("//p[contains(text(),'Please accept the terms of service')]");
    private final By termsOfServiceCheckbox = By.id("termsofservice");
    private final By loginTitle = By.xpath("//h1[text()='Welcome, Please Sign In!']");
    private final By qty = By.cssSelector(".qty-input");
    private final By addToCartButtonFromDetail = By.cssSelector("input[id^='add-to-cart-button']");


    public void openCart() {
        wait.until(ExpectedConditions.elementToBeClickable(cartButton)).click();
    }

    public void clickCheckout() {
        wait.until(ExpectedConditions.elementToBeClickable(checkoutButton)).click();
    }

    public void addProductToCart(String productName) {
        By addToCart = By.xpath("//a[text()='" + productName + "']/ancestor::div[@class='item-box']//input[@value='Add to cart']");
        wait.until(ExpectedConditions.elementToBeClickable(addToCart)).click();
    }

    public boolean addProductToCartSuccessful() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(successfulAddToCart)).isDisplayed();
    }

    public boolean showTermsOfService() {
        return wait.until(ExpectedConditions.elementToBeClickable(termsOfServiceError)).isDisplayed();
    }

    public void clickCheckbox() {
        wait.until(ExpectedConditions.elementToBeClickable(termsOfServiceCheckbox)).click();
    }

    public boolean needAuthorization() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(loginTitle)).isDisplayed();
    }

    public boolean isProductDetailOpened() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(qty)).isDisplayed();
    }

    public void addProductFromDetail(int quantity) {
        WebElement inputQty = wait.until(ExpectedConditions.visibilityOfElementLocated(qty));
        inputQty.clear();
        inputQty.sendKeys(String.valueOf(quantity));
        wait.until(ExpectedConditions.elementToBeClickable(addToCartButtonFromDetail)).click();
    }
}
