package dataDriven;

import com.fasterxml.jackson.databind.JsonNode;
import commonUtil.excelUtil.ExcelSource;
import org.testng.annotations.Test;
import utility.ExcelDataProvider;

public class ExcelDrivenTest {

    /**
     * This method returns Excel data in Json format for each row and all column in that row on by one
     *
     * @param jsonNode -> Excel each Row with All column response
     */
    @Test(dataProvider = "excelJson", dataProviderClass = ExcelDataProvider.class)
    @ExcelSource(file = "src/test/resources/excel/excelDrivenData.xlsx", sheet = "Sheet1")
    public void excelTest(JsonNode jsonNode) {
        System.out.println(jsonNode.toPrettyString());
    }

    /**
     * This method returns Excel data in Json format for all rows as a single json
     *
     * @param jsonNode -> Excel all Row with each row with all column response
     */
    @Test(dataProvider = "excelList", dataProviderClass = ExcelDataProvider.class)
    @ExcelSource(file = "src/test/resources/excel/excelDrivenData.xlsx", sheet = "Sheet1")
    public void excelListTest(JsonNode jsonNode) {
        System.out.println(jsonNode.toPrettyString());
    }

    /**
     * This method returns Excel data in Json format for specific rows given in the input
     *
     * @param jsonNode -> Excel specific Row with each row with all column response
     */
    @Test(dataProvider = "filteredExcelJson", dataProviderClass = ExcelDataProvider.class)
    @ExcelSource(file = "src/test/resources/excel/excelDrivenData.xlsx", sheet = "Sheet1", rows = {1,3})
    public void filteredExcelListTest(JsonNode jsonNode) {
        System.out.println(jsonNode.toPrettyString());
    }
}

