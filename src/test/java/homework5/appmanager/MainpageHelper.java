package homework5.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class MainpageHelper extends HelperBase {
    public MainpageHelper(WebDriver wd) {
        super(wd);
    }


    public boolean isBurgerMenuButtonDisplayed() {
        return isElementVisible(By.cssSelector(".mobile #menu-icon"));
    }

    public boolean isBurgerMenuButtonDisplayed(long timeout_millis) {
        return isElementVisible(By.cssSelector(".mobile #menu-icon"), timeout_millis);
    }

    public boolean isMobileHeaderDisplayed() {
        return isElementVisible(By.cssSelector("div[class=\"hidden-md-up text-xs-center mobile\"]"));
    }

    public boolean isMobileHeaderDisplayed(long timeout_millis) {
        return isElementVisible(By.cssSelector("div[class=\"hidden-md-up text-xs-center mobile\"]"), timeout_millis);
    }

    public boolean isMobileUserInfoButtonDisplayed() {
        return isElementVisible(By.cssSelector("div.mobile #_mobile_user_info"));
    }

    public boolean isMobileUserInfoButtonDisplayed(long timeout_millis) {
        return isElementVisible(By.cssSelector("div.mobile #_mobile_user_info"), timeout_millis);
    }

    public boolean isMobileCartDisplayed() {
        return isElementVisible(By.cssSelector("div.mobile #_mobile_cart"));
    }

    public boolean isMobileCartDisplayed(long timeout_millis) {
        return isElementVisible(By.cssSelector("div.mobile #_mobile_cart"), timeout_millis);
    }

    public boolean isDesktopContactLinkDisplayed() {
        return isElementVisible(By.cssSelector("#_desktop_contact_link"));
    }

    public boolean isDesktopContactLinkDisplayed(long timeout) {
        return isElementVisible(By.cssSelector("#_desktop_contact_link"), timeout);
    }

    public boolean isDesktopLanguageSelectorDisplayed() {
        return isElementVisible(By.cssSelector("#_desktop_language_selector"));
    }

    public boolean isDesktopLanguageSelectorDisplayed(long timeout_millis) {
        return isElementVisible(By.cssSelector("#_desktop_language_selector"), timeout_millis);
    }

    public boolean isDesktopCurrencySelectorDisplayed() {
        return isElementVisible(By.cssSelector("#_desktop_currency_selector"));
    }

    public boolean isDesktopCurrencySelectorDisplayed(long timeout_millis) {
        return isElementVisible(By.cssSelector("#_desktop_currency_selector"), timeout_millis);
    }

    public boolean isDesktopUserInfoButtonDisplayed() {
        return isElementVisible(By.cssSelector("#_desktop_user_info"));
    }

    public boolean isDesktopUserInfoButtonDisplayed(long timeout_millis) {
        return isElementVisible(By.cssSelector("#_desktop_user_info"), timeout_millis);
    }

    public boolean isDesktopCartDisplayed() {
        return isElementVisible(By.cssSelector("#_desktop_cart"));
    }

    public boolean isDesktopCartDisplayed(long timeout_millis) {
        return isElementVisible(By.cssSelector("#_desktop_cart"), timeout_millis);
    }

    public int numOfPopularProducts() {
        return wd.findElements(By.cssSelector("a.product-thumbnail")).size();
    }

    public void openProduct(int index) {
        wd.findElement(By.cssSelector("article.product-miniature:nth-child(" + index + ") .product-thumbnail")).click();
        wait.until(presenceOfElementLocated(By.cssSelector(".product-information")));
    }
}
