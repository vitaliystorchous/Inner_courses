import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class MyClass {

    public static void main(String[] args) {
        WebDriver wd = getDriver();
        wd.manage().window().maximize();
        wd.get("https://www.bing.com/");

        WebElement searchInput = wd.findElement(By.id("sb_form_q"));
        searchInput.sendKeys("Selenium");
        searchInput.submit();

        System.out.println("Page title is: " + wd.getTitle());

        wd.quit();
    }

    public static WebDriver getDriver() {
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/test/resources/drivers/chromedriver.exe");
        return new ChromeDriver();
    }
}
