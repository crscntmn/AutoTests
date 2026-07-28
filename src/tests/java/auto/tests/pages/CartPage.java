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
        click(cartButton);
    }

    public void clickCheckout() {
        click(checkoutButton);
    }

    public void addProductToCart(String productName) {
        By addToCart = By.xpath("//a[text()='" + productName + "']/ancestor::div[@class='item-box']//input[@value='Add to cart']");
        click(addToCart);
    }

    public boolean addProductToCartSuccessful() {
        return isDisplayed(successfulAddToCart);
    }

    public boolean showTermsOfService() {
        return isDisplayed(termsOfServiceError);
    }

    public void clickCheckbox() {
        click(termsOfServiceCheckbox);
    }

    public boolean needAuthorization() {
        return isDisplayed(loginTitle);
    }

    public boolean isProductDetailOpened() {
        return isDisplayed(qty);
    }

    public void addProductFromDetail(int quantity) {
        WebElement inputQty = wait.until(ExpectedConditions.visibilityOfElementLocated(qty));
        inputQty.clear();
        type(qty, String.valueOf(quantity));
        click(addToCartButtonFromDetail);
    }
}
