package homework4.appmanager;

import homework4.listeners.EventHandler;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import java.util.concurrent.TimeUnit;

public class DriverManager {

    public static WebDriver getDriver(String browser) {
        switch (browser.toLowerCase()) {
            case ("firefox") : {
                System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "/src/test/resources/drivers/geckodriver.exe");
                return new FirefoxDriver();
            }
            case ("edge") : {
                System.setProperty("webdriver.edge.driver", System.getProperty("user.dir") + "/src/test/resources/drivers/msedgedriver.exe");
                return new EdgeDriver();
            }
            case ("ie") : case ("internet explorer") : {
                System.setProperty("webdriver.ie.driver", System.getProperty("user.dir") + "/src/test/resources/drivers/IEDriverServer.exe");
                return new InternetExplorerDriver();
            }
            case ("chrome") : default: {
                System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/test/resources/drivers/chromedriver.exe");
                return new ChromeDriver();
            }
        }
    }

    public static EventFiringWebDriver getConfiguredDriver(String browser) {
        EventFiringWebDriver wd = new EventFiringWebDriver(getDriver(browser));
        wd.register(new EventHandler());

        wd.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        wd.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
        wd.manage().window().maximize();

        return wd;
    }
}
