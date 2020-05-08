import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class LogoutTest {

    public static void main(String[] args) throws InterruptedException {
        WebDriver wd = getDriver();
        wd.manage().window().maximize();
        wd.get("https://portal.web100.com.ua/competence/login");
        Thread.sleep(1000);
        wd.findElement(By.name("loginv2")).sendKeys("vitali.storchous");
        wd.findElement(By.name("passwordv2")).sendKeys("Vs1996test");
        wd.findElement(By.cssSelector("button[type='submit']")).click();
        Thread.sleep(1000);

        wd.findElement(By.cssSelector("a[data-toggle='dropdown']")).click();
        wd.findElement(By.cssSelector("span.s7-power")).click();
        Thread.sleep(1000);
        assert wd.getTitle().equals("Вход в систему - QATestLab");

        wd.quit();
    }

    public static WebDriver getDriver() {
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/test/resources/drivers/chromedriver.exe");
        return new ChromeDriver();
    }
}
