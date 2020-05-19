package homework5.tests;

import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.EyesRunner;
import com.applitools.eyes.TestResultsSummary;
import com.applitools.eyes.selenium.ClassicRunner;
import com.applitools.eyes.selenium.Configuration;
import com.applitools.eyes.selenium.Eyes;
import homework5.appmanager.ApplicationManager;
import org.openqa.selenium.remote.BrowserType;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class TestBase {

    EyesRunner runner;
    Eyes eyes;
    protected static final ApplicationManager app
            = new ApplicationManager(System.getProperty("browser", BrowserType.CHROME));

    @BeforeSuite
    public void setUp() throws Exception {
        app.init();
        runner = new ClassicRunner();
        eyes = new Eyes(runner);
        Configuration config = new Configuration();
        config.setApiKey("QZ6ht9JOYasOKa1Muos101AxHRcUo109vyBsiqAowzfYiTI110");
        config.setBatch(new BatchInfo("Inner course - homework 5"));
        eyes.setConfiguration(config);
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() {
        app.stop();
        TestResultsSummary allTestResults = runner.getAllTestResults(false);
        System.out.println(allTestResults);
    }
}
