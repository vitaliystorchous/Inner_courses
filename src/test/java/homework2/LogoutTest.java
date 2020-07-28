package homework2;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class LogoutTest {

    //данный тест не запуститься так как он был написан для сайта (модуль компетенции), где использовалась
    //конфиденциальная информация, по этому пришлось эту информацию удалить перед переносом кода проекта в гит хаб
    public static void main(String[] args) throws InterruptedException {
        WebDriver wd = getDriver();
        wd.manage().window().maximize();
        wd.get("");
        Thread.sleep(1000);
        wd.findElement(By.name("loginv2")).sendKeys("");
        wd.findElement(By.name("passwordv2")).sendKeys("");
        wd.findElement(By.cssSelector("button[type='submit']")).click();
        Thread.sleep(1000);

        wd.findElement(By.cssSelector("a[data-toggle='dropdown']")).click();
        wd.findElement(By.cssSelector("span.s7-power")).click();
        Thread.sleep(1000);
        assert wd.getTitle().equals("");

        wd.quit();
    }

    public static WebDriver getDriver() {
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/test/resources/drivers/chromedriver.exe");
        return new ChromeDriver();
    }
}
