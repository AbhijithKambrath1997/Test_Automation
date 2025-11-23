package dataDriven;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.Duration;

public class ArrayDrivenTest {

    @Test(dataProvider = "LoginData")
    public void loginTest(String name, String password, String value) {
        System.out.println(name);
        System.out.println(password);
        System.out.println(value);

        WebDriver webDriver = new ChromeDriver();
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(7));
        webDriver.get("https://admin-demo.nopcommerce.com/login?ReturnUrl=%2Fadmin%2F");

        WebElement element1 = webDriver.findElement(By.cssSelector("input.email"));
        element1.clear();
        element1.sendKeys(name);

        WebElement element2 = webDriver.findElement(By.cssSelector("input.password"));
        element2.clear();
        element2.sendKeys(password);

        webDriver.findElement(By.cssSelector("button.button-1")).click();

        String expectedTitle = "DashBoard / nopCommerce administration";
        String actualTitle = webDriver.getTitle();

        if (value.equals("TRUE")) {
            Assert.assertEquals(actualTitle, expectedTitle);
            webDriver.findElement(By.xpath("//a[text()= 'Logout']")).click();
        } else {
            Assert.assertNotEquals(actualTitle, expectedTitle);
        }
        webDriver.close();
    }

    @DataProvider(name = "LoginData")
    public String[][] getData() {
        return new String[][]{
                {"admin@yourstore.com", "admin", "TRUE"},
                {"admin@.com", "admin", "FALSE"},
                {"admin@you.com", "adm", "FALSE"},
                {"admin@yourstore.com", "adm", "FALSE"},
        };
    }
}

