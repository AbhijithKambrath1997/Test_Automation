package dataDriven;

import commonUtil.excelUtil.XLUtility;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class ExcelDrivenWriteTest {


    @Test
    public void writeExcelData() throws IOException {

        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get("https://en.wikipedia.org/wiki/List_of_states_and_union_territories_of_India_by_area");

        WebElement webElement = driver.findElement(By.xpath("//table[@class = \"wikitable sortable " +
                "sticky-header col2left col4left jquery-tablesorter\"]/child::tbody"));

        String filePath = "src/test/resources/excel/writeExcel" + RandomStringUtils.randomNumeric(2) + ".xlsx";
        XLUtility xlUtility = new XLUtility(filePath);

        List<WebElement> headers = webElement.findElements(By.xpath("tr[1]/th"));
        for (int j = 0; j < headers.size(); j++) {
            xlUtility.setCellData("Sheet1", 0, j, headers.get(j).getText());
        }

        List<WebElement> rows = webElement.findElements(By.xpath("tr"));
        for (int i = 1; i < rows.size(); i++) { // skip header
            List<WebElement> cells = rows.get(i).findElements(By.tagName("td")); // count <td> dynamically
            for (int j = 0; j < cells.size(); j++) {
                xlUtility.setCellData("Sheet1", i, j, cells.get(j).getText());
            }
        }

        File file = new File(filePath);
        if (file.delete()) {
            System.out.println("Excel file deleted successfully.");
        } else {
            System.out.println("Failed to delete Excel file.");
        }

    }
}

