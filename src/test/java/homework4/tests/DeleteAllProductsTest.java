package homework4.tests;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static homework4.appmanager.DriverManager.getDriver;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class DeleteAllProductsTest {

    WebDriver wd;
    WebDriverWait wait;

    public DeleteAllProductsTest() {
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
        //для теста использовался триальный 14-дневный аккаунт. по истечению этого периода нужно создать
        //новый аккаут и внести сюда логин...
        emailField.sendKeys("rufjtigk+100@gmail.com");
        //... а сюда пароль
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

        Assert.assertEquals(wd.findElements(By.cssSelector(".product-list-item")).size(), 0);
    }

    @AfterTest
    public void tearDown() {
        wd.quit();
    }
}
