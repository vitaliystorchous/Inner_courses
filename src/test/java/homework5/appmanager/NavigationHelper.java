package homework5.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;

public class NavigationHelper extends HelperBase {
    public NavigationHelper(WebDriver wd) {
        super(wd);
    }


    public void mainpage() {
        if (getViewportSize().getWidth() >= 768) {
            wd.findElement(By.cssSelector("#_desktop_logo")).click();
        } else {
            wd.findElement(By.cssSelector("#_mobile_logo")).click();
        }
        wait.until(elementToBeClickable(By.cssSelector("a.product-thumbnail")));
    }
}
