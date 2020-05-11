package homework3;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

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
}
