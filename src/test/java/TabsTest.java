import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class TabsTest {

    public static void main(String[] args) throws InterruptedException {
        WebDriver wd = getDriver();
        Actions actions = new Actions(wd);
        wd.manage().window().maximize();
        wd.get("https://portal.web100.com.ua/competence/login");
        Thread.sleep(1000);

        wd.findElement(By.name("loginv2")).sendKeys("vitali.storchous");
        wd.findElement(By.name("passwordv2")).sendKeys("Vs1996test");
        wd.findElement(By.cssSelector("button[type='submit']")).click();
        Thread.sleep(1000);
        assert wd.getTitle().equals("В процессе обучения - QATestLab");

        wd.findElement(By.cssSelector("a[href='/competence/documents/1796']")).click();
        Thread.sleep(2000);
        assert wd.getTitle().equals("Учебные материалы - Инструменты / Tools - QATestLab");

        actions.moveToElement(wd.findElement(By.cssSelector("a[href='/competence/documents/1796']"))).perform();
        wd.findElement(By.cssSelector("a[href='/competence/documents/1802']")).click();
        Thread.sleep(2000);
        assert wd.getTitle().equals("Учебные материалы - Знания иностранного языка / Foreign language knowledge - QATestLab");

        actions.moveToElement(wd.findElement(By.cssSelector("a[href='/competence/documents/1796']"))).perform();
        wd.findElement(By.cssSelector("a[href='/competence/documents/1793']")).click();
        Thread.sleep(2000);
        assert wd.getTitle().equals("Учебные материалы - Технические навыки в тестировании / Technical skills - QATestLab");

        actions.moveToElement(wd.findElement(By.cssSelector("a[href='/competence/documents/1796']"))).perform();
        wd.findElement(By.cssSelector("a[href='/competence/documents/1799']")).click();
        Thread.sleep(2000);
        assert wd.getTitle().equals("Учебные материалы - Лидерские навыки/ Эффективность / Effectiveness - QATestLab");

        actions.moveToElement(wd.findElement(By.cssSelector("a[href='/competence/documents/1796']"))).perform();
        wd.findElement(By.cssSelector("a[href='/competence/documents/1797']")).click();
        Thread.sleep(2000);
        assert wd.getTitle().equals("Учебные материалы - Технологии - Technology skills - QATestLab");

        actions.moveToElement(wd.findElement(By.cssSelector("a[href='/competence/documents/1796']"))).perform();
        wd.findElement(By.cssSelector("a[href='/competence/documents/1794']")).click();
        Thread.sleep(2000);
        assert wd.getTitle().equals("Учебные материалы - Процесс тестирования / Testing Process - QATestLab");

        wd.quit();
    }

    public static WebDriver getDriver() {
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/test/resources/drivers/chromedriver.exe");
        return new ChromeDriver();
    }
}
