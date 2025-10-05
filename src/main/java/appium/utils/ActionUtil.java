package appium.utils;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;

import org.openqa.selenium.WebElement;

public class ActionUtil {

    private final AndroidDriver driver;

    public ActionUtil(AndroidDriver driver) {
        this.driver = driver;
    }

    // ---------- SCROLL ----------

    public WebElement scrollDownToText(String text) {
        String expr = String.format(
                "new UiScrollable(new UiSelector()).scrollIntoView(text(\"%s\"))", text
        );
        return driver.findElement(AppiumBy.androidUIAutomator(expr));
    }

    public WebElement scrollUpToText(String text) {
        String expr = String.format("new UiScrollable(new UiSelector())" +
                ".setAsVerticalList().scrollBackward().scrollIntoView(text(\"%s\"))", text);
        return driver.findElement(AppiumBy.androidUIAutomator(expr));
    }

    public WebElement scrollToText(String text, boolean scrollUp) {
        String expr = scrollUp
                ? String.format("new UiScrollable(new UiSelector())" +
                ".setAsVerticalList().scrollBackward().scrollIntoView(text(\"%s\"))", text)
                : String.format("new UiScrollable(new UiSelector())" +
                ".scrollIntoView(text(\"%s\"))", text);
        return driver.findElement(AppiumBy.androidUIAutomator(expr));
    }


    // ---------- DEVICE KEYS ----------
    public void pressBack() {
        driver.pressKey(new KeyEvent(AndroidKey.BACK));
    }

    public void pressHome() {
        driver.pressKey(new KeyEvent(AndroidKey.HOME));
    }

    public void pressEnter() {
        driver.pressKey(new KeyEvent(AndroidKey.ENTER));
    }
}
