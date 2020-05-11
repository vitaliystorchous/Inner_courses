package homework4.tests;

import homework4.model.Product;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.Set;

import static homework4.appmanager.DriverManager.getDriver;
import static java.lang.Long.parseLong;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class DeleteProductsTest {

    WebDriver wd;
    WebDriverWait wait;

    public DeleteProductsTest() {
        wd = getDriver("chrome");
        wd.manage().window().maximize();
        wait = new WebDriverWait(wd, 5);
    }

    @BeforeTest
    public void login() {
        wd.get("http://format.com/");
        WebElement logInTab = wait.until(presenceOfElementLocated(By.cssSelector("a[href=\"/login\"]")));
        logInTab.click();

        WebElement emailField = wait.until(presenceOfElementLocated(By.cssSelector("#login_form #email")));
        emailField.sendKeys("rufjtigk+89@gmail.com");
        wd.findElement(By.cssSelector("#login_form #password")).sendKeys("qweriuyt");
        wd.findElement(By.cssSelector("#login_form input[type='submit']")).click();
    }

    @Test
    public void deleteProducts() {
        WebElement storeTab = wait.until(presenceOfElementLocated(By.cssSelector("a[href=\"/site/store\"]")));
        storeTab.click();

        try {
            WebElement closeModalButton = wait.until(presenceOfElementLocated(By.cssSelector("a[class=\"f-overlay-close f-overlay-close-x\"]")));
            closeModalButton.click();
        } catch (TimeoutException ex) {
            System.out.println("No \"Connect to PayPal to Get Started\" modal");
        }

        int numberOfProductsBefore = wd.findElements(By.cssSelector(".product-list-item")).size();
        for(int i = 1; i <= numberOfProductsBefore; i++) {
            wait.until(elementToBeClickable(By.cssSelector("li.product-list-item .format-index-item__title"))).click();
            wait.until(elementToBeClickable(By.cssSelector("body.edit .tab-settings a"))).click();
            wait.until(elementToBeClickable(By.cssSelector("button.settings-delete-product"))).click();
            wait.until(elementToBeClickable(By.cssSelector("a.delete"))).click();
            wait.until(presenceOfElementLocated(By.cssSelector("div.products_tab")));

            try {
                WebElement closeModalButton = wait.until(presenceOfElementLocated(By.cssSelector("a[class=\"f-overlay-close f-overlay-close-x\"]")));
                closeModalButton.click();
            } catch (TimeoutException ex) {
                System.out.println("No \"Connect to PayPal to Get Started\" modal");
            }
        }
    }

    @AfterTest
    public void tearDown() {
        wd.quit();
    }
}
