package appium.utils;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class LocatorUtil {

    AndroidDriver driver;

    public LocatorUtil(AndroidDriver driver){
        this.driver = driver;
    }

    public WebElement getById(String id) {
        return driver.findElement(By.id(id));
    }

    public WebElement getByXpath(String xpath) {
        return driver.findElement(By.xpath(xpath));
    }

    public WebElement getByAccessibility(String id) {
        return driver.findElement(AppiumBy.accessibilityId(id));
    }

    public WebElement getByClassName(String name) {
        return driver.findElement(AppiumBy.className(name));
    }

    public WebElement getByAndroidUiAutomator(String id) {
        return driver.findElement(AppiumBy.androidUIAutomator(id));
    }

    public WebElement getByAndroidUiAutomatorId(String uiSelector) {
        return driver.findElement(AppiumBy.androidUIAutomator("new UiSelector()." + uiSelector));
    }

}
