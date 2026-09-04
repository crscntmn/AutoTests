package auto.tests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.math.BigDecimal;
import java.util.List;

public class CartPage extends BasePage {

    public CartPage(WebDriver driver) {
        super(driver);
    }

    //Локаторы (/cart)
    private final By checkoutButton = By.cssSelector(".button-1.checkout-button");
    private final By successfulAddToCart = By.cssSelector(".bar-notification.success");
    private final By termsOfServiceError = By.xpath("//p[contains(text(),'Please accept the terms of service')]");
    private final By termsOfServiceCheckbox = By.id("termsofservice");
    private final By loginTitle = By.xpath("//h1[text()='Welcome, Please Sign In!']");
    private final By qty = By.cssSelector(".qty-input");
    private final By addToCartButtonFromDetail = By.cssSelector("input[id^='add-to-cart-button']");
    private final By recipientsName = By.id("giftcard_2_RecipientName");
    private final By recipientsEmail = By.id("giftcard_2_RecipientEmail");
    private final By senderName = By.id("giftcard_2_SenderName");
    private final By senderEmail = By.id("giftcard_2_SenderEmail");
    //private final By updateShoppingCartButton = By.xpath("//input[@class='button-2 update-cart-button']");
    private final By updateShoppingCartButton = By.name("updatecart");
    private final By removeCheckboxInCart = By.name("removefromcart");
    private final By unitPrice = By.cssSelector(".product-unit-price");
    private final By totalPrice = By.cssSelector(".product-subtotal");


    public void clickCheckout() {
        click(checkoutButton);
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
        type(qty, String.valueOf(quantity));
        click(addToCartButtonFromDetail);
    }

    public void addGiftCardFromDetail(String firstName, String firstEmail, String secondName, String secondEmail) {
        type(recipientsName, firstName);
        type(recipientsEmail, firstEmail);
        type(senderName, secondName);
        type(senderEmail, secondEmail);
    }

    public int getCountItemsInCart() {
        List<WebElement> items = driver.findElements(By.cssSelector("tr.cart-item-row"));
        return items.size();
    }

    public int getProductQuantity() {
        return Integer.parseInt(getAttribute(qty, "value"));
    }

    public void changeCountInCart(int quantity) {
        type(qty, String.valueOf(quantity));
        click(updateShoppingCartButton);
    }

    public void removeFromCart() {
        click(removeCheckboxInCart);
        click(updateShoppingCartButton);
    }

    public BigDecimal getProductUnitPrice(String productName) {
        By locator = By.xpath("//a[text()='" + productName + "']/ancestor::tr[@class='cart-item-row']//span[@class='product-unit-price']");
        return new BigDecimal(getText(locator).replace("$", ""));
    }

    public int getProductQuantity(String productName) {
        By locator = By.xpath("//a[text()='" + productName + "']/ancestor::tr[@class='cart-item-row']//input[contains(@class,'qty-input')]");
        return Integer.parseInt(getAttribute(locator, "value"));
    }

    public BigDecimal getProductTotalPrice(String productName) {
        By locator = By.xpath("//a[text()='" + productName + "']/ancestor::tr[@class='cart-item-row']//span[@class='product-subtotal']");
        return new BigDecimal(getText(locator).replace("$", ""));
    }
}
