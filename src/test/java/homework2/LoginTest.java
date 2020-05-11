package homework2;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class LoginTest {

    public static void main(String[] args) throws InterruptedException {
        WebDriver wd = getDriver();
        wd.manage().window().maximize();
        wd.get("");
        Thread.sleep(1000);
        wd.findElement(By.name("loginv2")).sendKeys("");
        wd.findElement(By.name("passwordv2")).sendKeys("");
        wd.findElement(By.cssSelector("button[type='submit']")).click();
        Thread.sleep(1000);
        assert wd.getTitle().equals("");

        wd.quit();
    }

    public static WebDriver getDriver() {
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/test/resources/drivers/chromedriver.exe");
        return new ChromeDriver();
    }
}
