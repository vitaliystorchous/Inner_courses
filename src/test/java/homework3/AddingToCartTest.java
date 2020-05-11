package homework3;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static homework3.DriverManager.getDriver;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;

public class AddingToCartTest {

    public static void main(String[] args) {
        WebDriver wd = getDriver("chrome");
        wd.manage().window().maximize();
        WebDriverWait wait = new WebDriverWait(wd, 10);
        Actions actions = new Actions(wd);
        wd.get("http://prestashop-automation.qatestlab.com.ua/ru/");

        WebElement women = wd.findElement(By.cssSelector("#category-3"));
        WebElement tops = wd.findElement(By.cssSelector("a[href=\"http://prestashop-automation.qatestlab.com.ua/ru/4-tops\"]"));
        actions.moveToElement(women).click(tops).build().perform();

        WebElement tShirtsCheckbox = wait.until(presenceOfElementLocated(By.cssSelector("input[data-search-url=\"http://prestashop-automation.qatestlab.com.ua/ru/4-tops?q=%D0%9A%D0%B0%D1%82%D0%B5%D0%B3%D0%BE%D1%80%D0%B8%D0%B8-T--shirts\"]")));
        tShirtsCheckbox.click();
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector(".total-products p"), "Товаров: 1."));

        WebElement tShirtProdContainer = wait.until(presenceOfElementLocated(By.cssSelector("article[data-id-product-attribute=\"1\"]")));
        WebElement tShireThumbnail = tShirtProdContainer.findElement(By.cssSelector("img"));
        actions.moveToElement(tShireThumbnail).build().perform();
        WebElement quickViewButton = wait.until(visibilityOfElementLocated(By.cssSelector("article[data-id-product-attribute=\"1\"] a[data-link-action=\"quickview\"]")));
        quickViewButton.click();

        WebElement modalView = wait.until(visibilityOfElementLocated(By.cssSelector("#quickview-modal-1-1.in")));
        WebElement inCartButton = modalView.findElement(By.cssSelector(".add-to-cart"));
        inCartButton.click();

        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.cssSelector("#myModalLabel"), "Товар добавлен в корзину"));

        wd.quit();
    }
}
