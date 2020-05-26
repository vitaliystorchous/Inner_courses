package homework5.tests;

import org.apache.commons.lang3.RandomUtils;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class TestBuyProductAsGuest extends TestBase {

    @BeforeTest
    public void ensurePreconditions() {
        app.goTo().mainpage();
    }

    @Test (testName = "Buy a product")
    public void buyProductTest() {
        int numberOfProducts = app.mainpage().numOfPopularProducts();
        int productIndex = RandomUtils.nextInt(1, numberOfProducts + 1);
        app.mainpage().openProduct(productIndex);
        app.product().addToCart();
        app.product().openCartThroughModal();
        app.cart().openOrderingForm();
        app.cart().submitFormWithRandomValues();
        assertTrue(app.cart().isOrderConfirmed(), "Order is not confirmed");
        assertEquals(app.cart().getConfirmationMessage(), "ваш заказ подтверждён", "Order confirmation message is not expected");
    }
}
