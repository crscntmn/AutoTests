package auto.tests.cart;

import auto.tests.pages.CartPage;
import auto.tests.pages.LoginPage;
import auto.tests.pages.RegistrationPage;
import base.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CartTest extends BaseTest {
    CartPage cartPage;
    RegistrationPage registrationPage;
    LoginPage loginPage;

    @BeforeEach
    void Setup() {
        cartPage = new CartPage(driver);
    }

    @Test
    @DisplayName("1.1 Оформление заказа аномимом без условий соглашения")
    void unknownWithoutCheckbox() {
        //1. Добавление товара в корзину
        cartPage.addProductToCart("14.1-inch Laptop");
        //Ожидаемое поведение: товар успешно добавлен в корзину
        Assertions.assertTrue(cartPage.addProductToCartSuccessful());
        //2. Переход в корзину
        cartPage.openCart();
        //3. Нажатие на кнопку "Checkout"
        cartPage.clickCheckout();
        //Ожидаемое поведение: отображение окна об обсутствии принятия соглашения
        Assertions.assertTrue(cartPage.showTermsOfService());
    }

    @Test
    @DisplayName("1.2 Оформление заказа анонимом с условиями соглашения")
    void unknownWithCheckbox() {
        //1. Добавление товара в корзину
        cartPage.addProductToCart("14.1-inch Laptop");
        //Ожидаемое поведение: товар успешно добавлен в корзину
        Assertions.assertTrue(cartPage.addProductToCartSuccessful());
        //2. Переход в корзину
        cartPage.openCart();
        //3. Прожатие чекбокса
        cartPage.clickCheckbox();
        //4. Нажатие на кнопку "Checkout"
        cartPage.clickCheckout();
        Assertions.assertTrue(cartPage.needAuthorization());

    }
}
