package appium.tests;

import appium.utils.ActionUtil;
import appium.base.AppiumSetUp;
import appium.utils.LocatorUtil;
import io.appium.java_client.android.AndroidDriver;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class GeneralStoreTest {

    private static final String appActivity = "com.androidsample.generalstore.MainActivity";
    private static final String appPackage = "com.androidsample.generalstore";

    private final AppiumSetUp appiumSetUp = new AppiumSetUp();
    private LocatorUtil locatorUtil;
    private ActionUtil actionUtil;
    private AndroidDriver driver;

    @BeforeMethod
    public void setUp() {
        driver = appiumSetUp.runWithPackage(appPackage, appActivity);
        actionUtil = new ActionUtil(driver);
        locatorUtil = new LocatorUtil(driver);
    }

    @Test(enabled = false, description = "Only to Install app")
    public void installAppTest() {
        if (driver.isAppInstalled(appPackage)) {
            driver.removeApp(appPackage);
        }

        String path = System.getProperty("user.dir") + "/src/test/resources/General-Store.apk";
        driver.installApp(path);
        driver.activateApp(appPackage);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.quit();
    }

    @Test
    public void loginTest() {

        //Verify Title
        WebElement webElement = locatorUtil.getById("com.androidsample.generalstore:id/toolbar_title");
        Assert.assertEquals(webElement.getText(), "General Store");

        //Click Drop Down and select Country
        locatorUtil.getByAndroidUiAutomatorId("resourceId(\"android:id/text1\")").click();
        actionUtil.scrollDownToText("India");
        locatorUtil.getByAndroidUiAutomatorId("text(\"India\")").click();

        /*locatorUtil.getByAndroidUiAutomatorId("resourceId(\"android:id/text1\")").click();
        actionUtil.scrollUpToText("China");
        locatorUtil.getByAndroidUiAutomatorId("text(\"China\")").click();

        locatorUtil.getByAndroidUiAutomatorId("resourceId(\"android:id/text1\")").click();
        actionUtil.scrollToText("Zimbabwe", false);
        locatorUtil.getByAndroidUiAutomatorId("text(\"Zimbabwe\")").click();*/

        //Enter text
        String userName = RandomStringUtils.randomAlphabetic(5);
        locatorUtil.getByClassName("android.widget.EditText").sendKeys(userName);

        //Verify Radio button is visible
        WebElement element1 = locatorUtil.getById("com.androidsample.generalstore:id/radioMale");
        WebElement element2 = locatorUtil.getById("com.androidsample.generalstore:id/radioFemale");
        Assert.assertTrue(element1.isDisplayed() && element2.isDisplayed());
        Assert.assertTrue(element1.isEnabled() && element2.isEnabled());
        Assert.assertTrue(Boolean.parseBoolean(element1.getAttribute("clickable")));
        Assert.assertTrue(Boolean.parseBoolean(element2.getAttribute("clickable")));

        //Verify Radio button click Action
        element1.click();
        Assert.assertTrue(Boolean.parseBoolean(element1.getAttribute("checked")));
        Assert.assertFalse(Boolean.parseBoolean(element2.getAttribute("checked")));
        Assert.assertFalse(element2.isSelected());

        element2.click();
        Assert.assertFalse(Boolean.parseBoolean(element1.getAttribute("checked")));
        Assert.assertTrue(Boolean.parseBoolean(element2.getAttribute("checked")));

        element1.click();
        Assert.assertTrue(Boolean.parseBoolean(element1.getAttribute("checked")));
        Assert.assertFalse(Boolean.parseBoolean(element2.getAttribute("checked")));

        WebElement element = locatorUtil.getById("com.androidsample.generalstore:id/btnLetsShop");
        Assert.assertEquals(element.getText(), "Let's  Shop");
        element.click();
    }

    @Test
    public void homepageTest() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        String userName = RandomStringUtils.randomAlphabetic(5);
        locatorUtil.getByClassName("android.widget.EditText").sendKeys(userName);
        locatorUtil.getById("com.androidsample.generalstore:id/btnLetsShop").click();

        WebElement element = locatorUtil.getByAndroidUiAutomatorId(
                "resourceId(\"com.androidsample.generalstore:id/toolbar_title\")");
        Assert.assertEquals(element.getText(), "Products");

        WebElement cartElement = locatorUtil.getById("com.androidsample.generalstore:id/appbar_btn_cart");
        Assert.assertTrue(cartElement.isEnabled());
        Assert.assertTrue(Boolean.parseBoolean(cartElement.getAttribute("clickable")));


    }
}
