package homework4.tests;

import homework4.dataproviders.ProductsGenerator;
import homework4.model.Product;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.File;
import java.util.*;

import static homework4.appmanager.DriverManager.getDriver;
import static java.lang.Double.parseDouble;
import static java.lang.Long.parseLong;
import static java.util.Collections.sort;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;
import static org.testng.Assert.assertEquals;

public class ProductCreationTest {

    WebDriver wd;
    WebDriverWait wait;

    @Parameters({"browser", "email", "password"})
    @BeforeTest
    public void login(String browser, String email, String password) {
        wd = getDriver(browser);
        wd.manage().window().maximize();
        wait = new WebDriverWait(wd, 5);

        wd.get("http://format.com/");
        WebElement logInTab = wait.until(presenceOfElementLocated(By.cssSelector("a[href=\"/login\"]")));
        logInTab.click();

        WebElement emailField = wait.until(presenceOfElementLocated(By.cssSelector("#login_form #email")));
        emailField.sendKeys(email);
        wd.findElement(By.cssSelector("#login_form #password")).sendKeys(password);
        wd.findElement(By.cssSelector("#login_form input[type='submit']")).click();
    }

    @Test (dataProvider = "testData", dataProviderClass = ProductsGenerator.class)
    public void createProductTest(String productName, double productPrice) {
        WebElement storeTab = wait.until(presenceOfElementLocated(By.cssSelector("a[href=\"/site/store\"]")));
        storeTab.click();

        try {
            WebElement closeModalButton = wait.until(presenceOfElementLocated(By.cssSelector("a[class=\"f-overlay-close f-overlay-close-x\"]")));
            closeModalButton.click();
        } catch (TimeoutException ex) {
            System.out.println("No \"Connect to PayPal to Get Started\" modal");
        }

        Set<Product> productsBefore = new HashSet<Product>();
        int numberOfProductsBefore = wd.findElements(By.cssSelector(".product-list-item")).size();
        for(int i = 1; i <= numberOfProductsBefore; i++) {
            long id = parseLong(wd.findElement(By.cssSelector("li.product-list-item:nth-child(" + i + ")")).getAttribute("data-product_id"));
            String name = wd.findElement(By.cssSelector("li:nth-child(" + i + ") span[class=\"format-index-item__title font-headings\"]"))
                    .getAttribute("innerText");
            String metadata = wd.findElement(By.cssSelector("li.product-list-item:nth-child(" + i + ") div.format-index-item__meta")).getAttribute("innerText");
            double price = parseDouble(metadata.substring(2, metadata.indexOf(" ", 3)));
            productsBefore.add(new Product().withId(id).withName(name).withPrice(price));
        }

        WebElement addProductButton = wait.until(elementToBeClickable(By.cssSelector("button.add-item-button")));
        addProductButton.click();

        WebElement productNameField = wait.until(elementToBeClickable(By.cssSelector(".page-type-modal-input")));
        productNameField.sendKeys(productName);
        wd.findElement(By.cssSelector("button[class=\"btn btn-primarycolor\"]")).click();

        WebElement productAvailabilityDropdown = wait.until(presenceOfElementLocated(By.cssSelector("#s2id_page_inventory_status a")));
        productAvailabilityDropdown.click();
        WebElement comingSoonOption = wait.until(elementToBeClickable(By.cssSelector("li.availability-option--soon")));
        comingSoonOption.click();

        wd.findElement(By.cssSelector("button.product-add-image")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#vfs_uploader")));
        File image = new File("src/test/resources/test_image.jpg");
        wd.findElement(By.cssSelector("input[name=\"file\"]")).clear();
        int attempts = 3;
        while (true) {
            try {
                wd.findElement(By.cssSelector("input[name=\"file\"]")).sendKeys(image.getAbsolutePath());
                wait.until(textToBePresentInElementLocated(By.cssSelector("p.filename"), image.getName()));
                break;
            } catch (TimeoutException ex) {
                if (--attempts == 0) { throw ex; }
            }
        }
        WebElement doneButton = wait.until(elementToBeClickable(By.cssSelector("a.control_button.btn-primarycolor")));
        doneButton.click();

        WebElement priceField = wait.until(elementToBeClickable(By.cssSelector("#page_subtotal_price")));
        priceField.clear();
        priceField.sendKeys(Double.toString(productPrice));

        wd.findElement(By.cssSelector("button.btn_save")).click();
        wait.until(visibilityOfElementLocated(By.cssSelector("button.btn-state-complete")));
        wd.findElement(By.cssSelector("button.page-breadcrumbs__back-button")).click();

        try {
            WebElement closeModalButton = wait.until(presenceOfElementLocated(By.cssSelector("a[class=\"f-overlay-close f-overlay-close-x\"]")));
            closeModalButton.click();
        } catch (TimeoutException ex) {
            System.out.println("No \"Connect to PayPal to Get Started\" modal");
        }

        Set<Product> productsAfter = new HashSet<Product>();
        int numberOfProductsAfter = wd.findElements(By.cssSelector(".product-list-item")).size();
        for(int i = 1; i <= numberOfProductsAfter; i++) {
            long id = parseLong(wd.findElement(By.cssSelector("li.product-list-item:nth-child(" + i + ")")).getAttribute("data-product_id"));
            String name = wd.findElement(By.cssSelector("li:nth-child(" + i + ") span[class=\"format-index-item__title font-headings\"]"))
                    .getAttribute("innerText");
            String metadata = wd.findElement(By.cssSelector("li.product-list-item:nth-child(" + i + ") div.format-index-item__meta")).getAttribute("innerText");
            double price = parseDouble(metadata.substring(2, metadata.indexOf(" ", 3)));
            productsAfter.add(new Product().withId(id).withName(name).withPrice(price));
        }

        productsBefore.add(new Product().withId(productsAfter.stream().mapToLong(Product::getId).max().getAsLong()).withName(productName).withPrice(productPrice));
        assertEquals(productsBefore, productsAfter);
    }

    @Test (dependsOnMethods = "createProductTest")
    public void checkProductPresenceTest() throws InterruptedException {

        //Thread.sleep(120000); // сайт обновляется не сразу а через некоторое время, по этому перед тем как приступить ко второму тесту нужно немного подождать

        WebElement storeTab = wait.until(presenceOfElementLocated(By.cssSelector("a[href=\"/site/store\"]")));
        storeTab.click();

        try {
            WebElement closeModalButton = wait.until(presenceOfElementLocated(By.cssSelector("a[class=\"f-overlay-close f-overlay-close-x\"]")));
            closeModalButton.click();
        } catch (TimeoutException ex) {
            System.out.println("No \"Connect to PayPal to Get Started\" modal");
        }

        List<String> productsNamesCMS = new ArrayList<String>();
        int numberOfProductsCMS = wd.findElements(By.cssSelector(".product-list-item")).size();
        for(int i = 1; i <= numberOfProductsCMS; i++) {
            String name = wd.findElement(By.cssSelector("li:nth-child(" + i + ") span[class=\"format-index-item__title font-headings\"]"))
                    .getAttribute("innerText");
            productsNamesCMS.add(name);
        }

        wait.until(elementToBeClickable(By.cssSelector(".format-simple-sidebar__view-site"))).click();
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));
        List<String> tabs = new ArrayList<String>(wd.getWindowHandles());
        wd.switchTo().window(tabs.get(1));
        wait.until(elementToBeClickable(By.cssSelector("a[href='/store']"))).click();
        wait.until(presenceOfElementLocated(By.cssSelector(".product-list")));

        List<String> productsNamesLive = new ArrayList<String>();
        int numberOfProductsLive = wd.findElements(By.cssSelector(".product")).size();
        for(int i = 1; i <= numberOfProductsLive; i++) {
            String name = wd.findElement(By.cssSelector(".product:nth-child(" + i + ") .product-name"))
                    .getAttribute("innerText");
            productsNamesLive.add(name);
        }

        sort(productsNamesCMS);
        sort(productsNamesLive);

        assertEquals(productsNamesLive, productsNamesCMS);
    }

    @AfterTest
    public void tearDown() {
        wd.quit();
    }
}
