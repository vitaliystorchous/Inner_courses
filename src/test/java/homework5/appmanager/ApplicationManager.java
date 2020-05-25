package homework5.appmanager;

import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.EyesRunner;
import com.applitools.eyes.selenium.ClassicRunner;
import com.applitools.eyes.selenium.Configuration;
import com.applitools.eyes.selenium.Eyes;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.SECONDS;

public class ApplicationManager {

    private final Properties properties;
    private WebDriver wd;
    private NavigationHelper navigationHelper;
    private MainpageHelper mainpageHelper;
    private String browser;
    private ProductHelper productHelper;
    private CartHelper cartHelper;

    public ApplicationManager(String browser) {
        this.browser = browser;
        properties = new Properties();
    }

    public void init() throws IOException {
        String target = System.getProperty("target", "local");
        properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));

        if ("".equals(properties.getProperty("selenium.server"))) {
            if (browser.equals(BrowserType.CHROME)) {
                wd = new ChromeDriver();
            } else if (browser.equals(BrowserType.FIREFOX)) {
                wd = new FirefoxDriver();
            } else if (browser.equals(BrowserType.IE)) {
                wd = new InternetExplorerDriver();
            } else if (browser.equals(BrowserType.EDGE)) {
                File file = new File("src/test/resources/drivers/msedgedriver.exe");
                System.setProperty("webdriver.edge.driver", file.getAbsolutePath());
                wd = new EdgeDriver();
            } else if (browser.equals(BrowserType.OPERA_BLINK)) {
                wd = new OperaDriver();
            } else if (browser.equals("mobile")) {
                Map<String, String> mobileEmulation = new HashMap<>();
                mobileEmulation.put("deviceName", System.getProperty("deviceName", "iPhone 6"));

                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
                wd = new ChromeDriver(chromeOptions);
            }
        } else {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setBrowserName(browser);
            capabilities.setPlatform(Platform.fromString(System.getProperty("platform", "all")));
            wd = new RemoteWebDriver(new URL(properties.getProperty("selenium.server")), capabilities);

        }

        wd.manage().window().maximize();
        wd.manage().timeouts().implicitlyWait(10, SECONDS);
        wd.get(properties.getProperty("web.baseUrl"));
        navigationHelper = new NavigationHelper(wd);
        mainpageHelper = new MainpageHelper(wd);
        productHelper = new ProductHelper(wd);
        cartHelper = new CartHelper(wd);
    }

    public WebDriver getDriver() { return wd;}

    public void stop() {
        wd.quit();
    }

    public NavigationHelper goTo() {
        return navigationHelper;
    }

    public MainpageHelper mainpage() {
        return mainpageHelper;
    }

    public byte[] takeScreenshot() {
        return ((TakesScreenshot) wd).getScreenshotAs(OutputType.BYTES);
    }

    public Dimension getWindowSize() {
        return wd.manage().window().getSize();
    }

    public Dimension getViewportSize() {
        Long width = (Long) ((JavascriptExecutor) wd).executeScript(" return document.documentElement.clientWidth;");
        Long height = (Long) ((JavascriptExecutor) wd).executeScript(" return document.documentElement.clientHeight;");
        return new Dimension(width.intValue(), height.intValue());
    }

    public ProductHelper product() {
        return productHelper;
    }

    public CartHelper cart() {
        return cartHelper;
    }
}
