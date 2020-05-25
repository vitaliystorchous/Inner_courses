package homework5.appmanager;

import org.apache.commons.lang3.RandomUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.apache.commons.lang3.RandomStringUtils.random;
import static org.apache.commons.lang3.RandomUtils.nextInt;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class CartHelper extends HelperBase {
    public CartHelper(WebDriver wd) {
        super(wd);
    }

    public void openOrderingForm() {
        wait.until(elementToBeClickable(By.cssSelector("a.btn-primary"))).click();
        wait.until(presenceOfElementLocated(By.cssSelector("#checkout-personal-information-step")));
    }

    public void submitFormWithRandomValues() {
        int gender = RandomUtils.nextInt(1, 3);
        wd.findElement(By.cssSelector("label:nth-child(" + gender + ") input[name=id_gender]")).click();

        String name = random(nextInt(5, 11), true, false);
        wd.findElement(By.cssSelector("input[name=firstname]")).click();
        wd.findElement(By.cssSelector("input[name=firstname]")).sendKeys(name);

        String lastname = random(nextInt(5, 11), true, false);
        wd.findElement(By.cssSelector("input[name=lastname]")).click();
        wd.findElement(By.cssSelector("input[name=lastname]")).sendKeys(lastname);

        String email = random(nextInt(5, 8), true, true) + "@"
                + random(nextInt(4, 8), true, false) + "."
                + random(nextInt(2, 4), true, false);
        wd.findElement(By.cssSelector("#checkout-guest-form input[name=email]")).click();
        wd.findElement(By.cssSelector("#checkout-guest-form input[name=email]")).sendKeys(email);

        wd.findElement(By.cssSelector("button[data-link-action=register-new-customer]")).click();
        wait.until(visibilityOfElementLocated(By.cssSelector("#checkout-personal-information-step .done")));

        if(wd.findElement(By.cssSelector("input[name=address1]")).isDisplayed()) {
            String address = random(nextInt(5, 11), true, false);
            wd.findElement(By.cssSelector("input[name=address1]")).click();
            wd.findElement(By.cssSelector("input[name=address1]")).sendKeys(address);

            String postalCode = random(nextInt(5, 6), false, true);
            wd.findElement(By.cssSelector("input[name=postcode]")).click();
            wd.findElement(By.cssSelector("input[name=postcode]")).sendKeys(postalCode);

            String city = random(nextInt(5, 11), true, false);
            wd.findElement(By.cssSelector("input[name=city]")).click();
            wd.findElement(By.cssSelector("input[name=city]")).sendKeys(city);
        }

        wait.until(elementToBeClickable(By.cssSelector("button[name=confirm-addresses]"))).click();
        wait.until(visibilityOfElementLocated(By.cssSelector("#checkout-addresses-step .done")));

        int deliveryMethod = RandomUtils.nextInt(1, 3);
        int tries = 3;
        while (true) {
            try {
                wd.findElement(By.cssSelector(".delivery-option:nth-child(" + deliveryMethod + ") input")).click();
                break;
            } catch (ElementClickInterceptedException ex) {
                if(ex.getLocalizedMessage().contains("Other element would receive the click: <span></span>")) { break; }
                if (--tries == 0) { throw ex; }
            }
        }

        wd.findElement(By.cssSelector("button[name=confirmDeliveryOption]")).click();
        wait.until(visibilityOfElementLocated(By.cssSelector("#checkout-delivery-step .done")));

        int paymentMethod = RandomUtils.nextInt(1, 3);
        wd.findElement(By.cssSelector("#payment-option-" + paymentMethod + "-container input")).click();
        wd.findElement(By.cssSelector("input[name='conditions_to_approve[terms-and-conditions]']")).click();
        wait.until(elementToBeClickable(By.cssSelector("#payment-confirmation button"))).click();
        wait.until(presenceOfElementLocated(By.cssSelector("#order-confirmation")));
    }

    public boolean isOrderConfirmed() {
        try {
            wd.findElement(By.cssSelector("#content-hook_order_confirmation"));
            wd.findElement(By.cssSelector("#content"));
            wd.findElement(By.cssSelector("#content-hook_payment_return"));
            return true;
        } catch (NoSuchElementException ex) {
            return false;
        }
    }
}
