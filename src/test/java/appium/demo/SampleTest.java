package appium.demo;

import appium.base.AppiumSetUp;
import appium.utils.LocatorUtil;
import com.google.common.collect.ImmutableMap;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class SampleTest {

    private final AppiumSetUp appiumSetUp = new AppiumSetUp();
    private LocatorUtil locatorUtil;

    private final String appActivity = ".ApiDemos";
    private final String appPackage = "io.appium.android.apis";

    @Test
    public void demoTest() {
        AndroidDriver driver = appiumSetUp.runWithPackage(appPackage, appActivity);
        locatorUtil = new LocatorUtil(driver);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        locatorUtil.getByXpath("//android.widget.TextView[@text='Accessibility']").click();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.quit();
    }

    @Test
    public void installAppTest() {
        String path = System.getProperty("user.dir") + "/src/test/resources/ApkInfo.apk";
        AndroidDriver driver = appiumSetUp.runDefault();
        String appPackage = "com.wt.apkinfo";
        if (driver.isAppInstalled(appPackage)) {
            driver.removeApp(appPackage);
        }

        driver.installApp(path);
        driver.activateApp(appPackage);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.quit();
    }

    @Test
    public void clockTest() {
        AndroidDriver driver = appiumSetUp.runDefault();
        locatorUtil = new LocatorUtil(driver);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        driver.pressKey(new KeyEvent(AndroidKey.HOME));

        //click Clock
        locatorUtil.getByAccessibility("Predicted app: Clock").click();

        //Add new city
        locatorUtil.getByAccessibility("Add city").click();

        //Get search bar text
        WebElement webElement = driver.findElement(AppiumBy.androidUIAutomator("new UiSelector()" +
                ".resourceId(\"com.google.android.deskclock:id/open_search_view_edit_text\")"));
        String text = webElement.getText();

        //Send city and pass value
        webElement.sendKeys("Chicago");

        //select city
        locatorUtil.getByAndroidUiAutomator("new UiSelector().text(\"Chicago, IL, USA\")").click();

        //verify city is selected
        WebElement displayElement = locatorUtil.getByAndroidUiAutomator("new UiSelector().text(\"Chicago\")");
        Assert.assertTrue(displayElement.isDisplayed());

        //Again send add city and provide same value
        locatorUtil.getByAccessibility("Add city").click();
        webElement.sendKeys("Chicago");

        //verify city already selected
        locatorUtil.getByAndroidUiAutomator("new UiSelector().resourceId" +
                "(\"com.google.android.deskclock:id/city_selected\")").isDisplayed();

        //Again select city
        locatorUtil.getByAndroidUiAutomator("new UiSelector().text(\"Chicago, IL, USA\")").click();

        //verify city is removed
        List<WebElement> displayElements = driver.findElements(AppiumBy.androidUIAutomator(
                "new UiSelector().text(\"Chicago\")"));
        Assert.assertTrue(displayElements.isEmpty());

        System.out.println(text);
        driver.quit();
    }

    @Test
    public void getTextTest() {
        AndroidDriver driver = appiumSetUp.runWithPackage(appPackage, appActivity);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        locatorUtil.getByAccessibility("Views").click();
        locatorUtil.getByAndroidUiAutomator("new UiSelector().text(\"Buttons\")").click();

        String text = locatorUtil.getByAccessibility("Normal").getText();
        Assert.assertEquals(text, "Normal");
        driver.quit();
    }

    @Test
    public void longPressTest() {
        AndroidDriver driver = appiumSetUp.runWithPackage(appPackage, appActivity);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        locatorUtil.getByAccessibility("Views").click();
        locatorUtil.getByAccessibility("Expandable Lists").click();
        locatorUtil.getByAccessibility("1. Custom Adapter").click();
        WebElement webElement = locatorUtil.getByXpath("//android.widget.TextView[@text=\"People Names\"]");

        /*
         * Use x, y key in Map to long press on empty screen
         * Use duration in Map to set time for clicking
         */
        driver.executeScript("mobile: longClickGesture", ImmutableMap.of("elementId",
                ((RemoteWebElement)webElement).getId()));

        WebElement element1 = locatorUtil.getByAndroidUiAutomator("new UiSelector().text(\"Sample menu\")");
        Assert.assertTrue(element1.isDisplayed());
        Assert.assertEquals(element1.getText(), "Sample menu");
        WebElement element2 = locatorUtil.getByAndroidUiAutomator("new UiSelector().text(\"Sample action\")");
        Assert.assertTrue(element2.isDisplayed());
        Assert.assertTrue(element2.isEnabled());
        driver.quit();
    }

    @Test
    public void scrollTest() {
        AndroidDriver driver = appiumSetUp.runWithPackage(appPackage, appActivity);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        //Using androidUiAutomator
        locatorUtil.getByAccessibility("Views").click();
        locatorUtil.getByAndroidUiAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"WebView\"))");

        //Using JSE
        WebElement container = locatorUtil.getById("android:id/content");
        while ((driver.findElements(AppiumBy.accessibilityId("Chronometer"))).isEmpty()) {
            driver.executeScript("mobile: scrollGesture", ImmutableMap.of(
                    "elementId", ((RemoteWebElement) container).getId(),
                    "direction", "up",
                    "percent", 0.8));
        }
       locatorUtil.getByAccessibility("Chronometer").click();
    }

    @Test
    public void swipeTest() {
        AndroidDriver driver = appiumSetUp.runWithPackage(appPackage, appActivity);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        locatorUtil.getByAccessibility("Views").click();
        locatorUtil.getByAccessibility("Gallery").click();
        locatorUtil.getByAndroidUiAutomator("new UiSelector().textContains(\"Photos\")").click();

        WebElement webElement1 = locatorUtil.getByXpath("(//android.widget.ImageView)[1]");
        WebElement webElement2 = locatorUtil.getByXpath("(//android.widget.ImageView)[2]");

        Assert.assertTrue(Boolean.parseBoolean(webElement1.getAttribute("focusable")));
        Assert.assertFalse(Boolean.parseBoolean(webElement2.getAttribute("focusable")));

        driver.executeScript("mobile: swipeGesture", ImmutableMap.of("elementId",
                ((RemoteWebElement)webElement1).getId(), "direction", "left", "percent", "0.25"));
        Assert.assertFalse(Boolean.parseBoolean(webElement1.getAttribute("focusable")));
        Assert.assertTrue(Boolean.parseBoolean(webElement2.getAttribute("focusable")));

        driver.executeScript("mobile: swipeGesture", ImmutableMap.of("elementId",
                ((RemoteWebElement)webElement2).getId(), "direction", "right", "percent", "0.75"));
        Assert.assertTrue(Boolean.parseBoolean(webElement1.getAttribute("focusable")));
        Assert.assertFalse(Boolean.parseBoolean(webElement2.getAttribute("focusable")));
    }
}
