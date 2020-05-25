package homework5.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class ProductHelper extends HelperBase {
    public ProductHelper(WebDriver wd) {
        super(wd);
    }


    public void addToCart() {
        wait.until(elementToBeClickable(By.cssSelector("button.add-to-cart"))).click();
        wait.until(visibilityOfElementLocated(By.cssSelector(".modal-header")));
    }

    public void openCartThroughModal() {
        wait.until(elementToBeClickable(By.cssSelector(".modal-dialog .btn-primary"))).click();
        wait.until(presenceOfElementLocated(By.cssSelector("#cart")));
    }
}
