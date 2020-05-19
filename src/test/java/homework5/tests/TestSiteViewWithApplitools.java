package homework5.tests;

import com.applitools.eyes.selenium.fluent.Target;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class TestSiteViewWithApplitools extends TestBase {

    @Test
    public void testOne() {

        if (app.mainpage().getViewportSize().getWidth() <= 767) {
            assertTrue(app.mainpage().isMobileHeaderDisplayed());
            assertTrue(app.mainpage().isBurgerMenuButtonDisplayed());
            assertTrue(app.mainpage().isMobileUserInfoButtonDisplayed());
            assertTrue(app.mainpage().isMobileCartDisplayed());

            assertFalse(app.mainpage().isDesktopContactLinkDisplayed(500));
            assertFalse(app.mainpage().isDesktopLanguageSelectorDisplayed(500));
            assertFalse(app.mainpage().isDesktopCurrencySelectorDisplayed(500));
            assertFalse(app.mainpage().isDesktopUserInfoButtonDisplayed(500));
            assertFalse(app.mainpage().isDesktopCartDisplayed(500));
        } else if (app.mainpage().getViewportSize().getWidth() >= 768) {
            assertTrue(app.mainpage().isDesktopContactLinkDisplayed());
            assertTrue(app.mainpage().isDesktopLanguageSelectorDisplayed());
            assertTrue(app.mainpage().isDesktopCurrencySelectorDisplayed());
            assertTrue(app.mainpage().isDesktopUserInfoButtonDisplayed());
            assertTrue(app.mainpage().isDesktopCartDisplayed());

            assertFalse(app.mainpage().isMobileHeaderDisplayed(500));
            assertFalse(app.mainpage().isBurgerMenuButtonDisplayed(500));
            assertFalse(app.mainpage().isMobileUserInfoButtonDisplayed(500));
            assertFalse(app.mainpage().isMobileCartDisplayed(500));
        }

        // здесь идет проверка верстки с помощью сервиса Applitools.
        // код специально закоментирован что бы не использовать все доступные проверки в триальной версии (всего их 100 на месяц)
        /*eyes.open(app.getDriver(), "Prestashop automation", "Learning test for automation of GUI testing");
        eyes.check(Target.window().fully().withName("My test"));
        eyes.closeAsync();*/
    }
}
