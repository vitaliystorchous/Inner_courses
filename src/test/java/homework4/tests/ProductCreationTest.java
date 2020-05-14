package homework4.tests;

import homework4.dataproviders.ProductsGenerator;
import homework4.model.Product;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static homework4.appmanager.DriverManager.getConfiguredDriver;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;
import static java.util.Collections.sort;
import static org.openqa.selenium.Keys.chord;
import static org.openqa.selenium.support.ui.ExpectedConditions.*;
import static org.testng.Assert.assertEquals;

public class ProductCreationTest {

    WebDriver wd;
    WebDriverWait wait;

    @Parameters({"browser", "email", "password"})
    @BeforeTest
    public void login(String browser, String email, String password) {
        wd = getConfiguredDriver(browser);
        wait = new WebDriverWait(wd, 5);

        wd.get("http://format.com/");
        WebElement logInTab = wait.until(presenceOfElementLocated(By.cssSelector("a[href=\"/login\"]")));
        logInTab.click();

        WebElement emailField = wait.until(presenceOfElementLocated(By.cssSelector("#login_form #email")));
        emailField.sendKeys(email);
        wd.findElement(By.cssSelector("#login_form #password")).sendKeys(password);
        wd.findElement(By.cssSelector("#login_form input[type='submit']")).click();
    }

    @Test(dataProvider = "single product generator", dataProviderClass = ProductsGenerator.class)
    public void createProductTest(String productName, double productPrice, int numberOfProducts) {
        goToStoreCMS();
        closeModal();
        Set<Product> productsBefore = getProducts();
        createProduct(productName, productPrice, numberOfProducts);
        goBackToStore();
        closeModal();
        Set<Product> productsAfter = getProducts();

        productsBefore.add(new Product().withId(productsAfter.stream().mapToLong(Product::getId).max().getAsLong())
                .withName(productName)
                .withPrice(productPrice)
                .withAmount(numberOfProducts));
        assertEquals(productsBefore, productsAfter, "Products list after is not equal to product list before with added newly created product");
    }

    @Test (dependsOnMethods = "createProductTest")
    public void checkProductPresenceTest() {
        goToStoreCMS();
        closeModal();
        List<String> productsNamesCMS = getProductsNamesCMS();
        openLiveSite();
        goToStoreLive();
        List<String> productsNamesLive = getProductsNamesLive();

        sort(productsNamesCMS);
        sort(productsNamesLive);
        assertEquals(productsNamesLive, productsNamesCMS, "Products list in CMS is not equal to products list on live site");
    }

    @AfterTest
    public void tearDown() {
        wd.quit();
    }


    private void closeModal() {
        try {
            WebElement closeModalButton = wait.until(presenceOfElementLocated(By.cssSelector("a[class=\"f-overlay-close f-overlay-close-x\"]")));
            closeModalButton.click();
        } catch (TimeoutException ex) {
            System.out.println("No \"Connect to PayPal to Get Started\" modal");
        }
    }

    private Set<Product> getProducts() {
        Set<Product> products = new HashSet<Product>();
        int numberOfProducts = wd.findElements(By.cssSelector(".product-list-item")).size();
        for (int i = 1; i <= numberOfProducts; i++) {
            long id = parseLong(wd.findElement(By.cssSelector("li.product-list-item:nth-child(" + i + ")")).getAttribute("data-product_id"));
            String name = wd.findElement(By.cssSelector("li:nth-child(" + i + ") span[class=\"format-index-item__title font-headings\"]"))
                    .getAttribute("innerText");
            String metadata = wd.findElement(By.cssSelector("li.product-list-item:nth-child(" + i + ") div.format-index-item__meta")).getAttribute("innerText");
            double price = parseDouble(metadata.substring(2, metadata.indexOf(" ", 3)));


            String amountMetadata = wd.findElement(By.cssSelector("li.product-list-item:nth-child(" + i + ") .format-index-item__inventory-copy")).getAttribute("innerText");
            int amount;
            if(amountMetadata.toLowerCase().equals("unlimited")) {
                amount = -1;
            } else {
                amount = parseInt(amountMetadata.substring(0, amountMetadata.indexOf(" ", 1)));
            }
            products.add(new Product().withId(id).withName(name).withPrice(price).withAmount(amount));
        }
        return products;
    }

    private List<String> getProductsNamesLive() {
        wait.until(presenceOfElementLocated(By.cssSelector(".product")));
        List<String> productsNamesLive = new ArrayList<String>();
        int numberOfProductsLive = wd.findElements(By.cssSelector(".product")).size();
        for(int i = 1; i <= numberOfProductsLive; i++) {
            String name = wd.findElement(By.cssSelector(".product:nth-child(" + i + ") .product-name"))
                    .getAttribute("innerText");
            productsNamesLive.add(name);
        }
        return productsNamesLive;
    }

    private List<String> getProductsNamesCMS() {
        wait.until(presenceOfElementLocated(By.cssSelector("li.product-list-item")));
        List<String> productsNamesCMS = new ArrayList<String>();
        int numberOfProductsCMS = wd.findElements(By.cssSelector(".product-list-item")).size();
        for(int i = 1; i <= numberOfProductsCMS; i++) {
            String name = wd.findElement(By.cssSelector("li:nth-child(" + i + ") span[class=\"format-index-item__title font-headings\"]"))
                    .getAttribute("innerText");
            String visibility = wd.findElement(By.xpath("//li[@class='product-list-item'][" + i + "]//*[@class='format-index-item__visibility']//span[@class='SVGInline']/.."))
                    .getAttribute("class");
            if(visibility.equals("visibility-soon")) {
                productsNamesCMS.add(name);
            }
        }

        return productsNamesCMS;
    }

    private void createProduct(String productName, double productPrice, int numberOfProducts) {
        wait.until(elementToBeClickable(By.cssSelector("button.add-item-button"))).click();

        wait.until(elementToBeClickable(By.cssSelector(".page-type-modal-input"))).sendKeys(productName);
        wd.findElement(By.cssSelector("button[class=\"btn btn-primarycolor\"]")).click();

        wait.until(presenceOfElementLocated(By.cssSelector("#s2id_page_inventory_status a"))).click();
        wait.until(elementToBeClickable(By.cssSelector("li.availability-option--soon"))).click();

        wd.findElement(By.cssSelector("button.product-add-image")).click();
        wait.until(elementToBeClickable(By.cssSelector("#vfs_uploader")));
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
        wait.until(elementToBeClickable(By.cssSelector("a.control_button.btn_danger")));
        wait.until(textToBePresentInElementLocated(By.cssSelector("a.ss-icon.remove_image"), "check"));
        attempts = 3;
        while (true) {
            try {
                wait.until(elementToBeClickable(By.cssSelector("a.control_button.btn-primarycolor"))).click();
                wait.until(numberOfElementsToBe(By.cssSelector("div[class='ModalMessage_container vfs_container vfs-next-visible']"), 0));
                break;
            } catch (TimeoutException ex) {
                if (--attempts == 0) { throw ex; }
            }
        }

        WebElement priceField = wait.until(elementToBeClickable(By.cssSelector("#page_subtotal_price")));
        priceField.clear();
        priceField.sendKeys(Double.toString(productPrice));

        attempts = 3;
        while (true) {
            try {
                wait.until(elementToBeClickable(By.xpath("//*[@class='quantity-editor']//label[contains(., 'Limited Quantity')]//span[@class='format-radio-button__indicator-ring']")))
                        .click();
                break;
            } catch (ElementClickInterceptedException ex) {
                System.out.println(attempts);
                if (--attempts == 0) { throw ex; }
            }
        }

        WebElement quantityField = wait.until(elementToBeClickable(By.cssSelector(".product-options__primary-controls #inventory-quantity")));
        attempts = 3;
        while (true) {
            try {
                quantityField.click();
                quantityField.sendKeys(chord(Keys.CONTROL,"a", Keys.DELETE));
                wait.until(attributeToBe(quantityField, "value", ""));
                break;
            } catch (TimeoutException ex) {
                if (--attempts == 0) { throw ex; }
            }
        }
        quantityField.sendKeys(Integer.toString(numberOfProducts));

        wait.until(elementToBeClickable(By.cssSelector("button.btn_save"))).click();
        wait.until(visibilityOfElementLocated(By.cssSelector("button.btn-state-complete")));
    }

    private void goToStoreCMS() {
        wait.until(presenceOfElementLocated(By.cssSelector("a[href=\"/site/store\"]"))).click();
    }

    private void goBackToStore() {
        wd.findElement(By.cssSelector("button.page-breadcrumbs__back-button")).click();
    }

    private void goToStoreLive() {
        for(int attempts = 3; attempts > 0; attempts--) {
            try {
                wait.until(elementToBeClickable(By.cssSelector("a[href='/store']"))).click();
                break;
            } catch (TimeoutException ex) {
                if (attempts == 1) { throw ex; }
            }
        }
        wait.until(presenceOfElementLocated(By.cssSelector(".product-list")));
    }

    private void openLiveSite() {
        wait.until(elementToBeClickable(By.cssSelector(".format-simple-sidebar__view-site"))).click();
        wait.until(numberOfWindowsToBe(2));
        List<String> tabs = new ArrayList<String>(wd.getWindowHandles());
        wd.switchTo().window(tabs.get(1));
    }
}
