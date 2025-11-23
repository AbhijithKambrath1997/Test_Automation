package utility;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import commonUtil.excelUtil.ExcelSource;
import commonUtil.excelUtil.ExcelUtil;
import org.testng.annotations.DataProvider;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ExcelDataProvider {


    @DataProvider(name = "excelJson")
    public static Object[][] provideExcelData(Method method) {

        ExcelSource excelSource = method.getAnnotation(ExcelSource.class);

        if (excelSource == null)
            throw new RuntimeException("@ExcelSource annotation missing on: " + method.getName());

        String file = excelSource.file();
        String sheet = excelSource.sheet();

        List<JsonNode> rows = ExcelUtil.getExcelRowsAsJsonNode(file, sheet);

        Object[][] data = new Object[rows.size()][1];

        for (int i = 0; i < rows.size(); i++) {
            data[i][0] = rows.get(i);
        }

        return data;
    }

    @DataProvider(name = "excelList")
    public static Object[][] provideListExcelData(Method method) {

        ExcelSource excelSource = method.getAnnotation(ExcelSource.class);

        if (excelSource == null)
            throw new RuntimeException("@ExcelSource annotation missing on: " + method.getName());

        String file = excelSource.file();
        String sheet = excelSource.sheet();

        List<JsonNode> rows = ExcelUtil.getExcelRowsAsJsonNode(file, sheet);

        // Combine all rows into a single ArrayNode
        ObjectMapper mapper = new ObjectMapper();
        ArrayNode allRows = mapper.createArrayNode();
        rows.forEach(allRows::add);

        // Return a single test invocation with the combined JSON
        return new Object[][]{{allRows}};
    }

    @DataProvider(name = "filteredExcelJson")
    public static Object[][] provideFilteredExcelDate(Method method) {
        ExcelSource excelSource = method.getAnnotation(ExcelSource.class);

        if (excelSource == null) {
            throw new RuntimeException("@ExcelSource annotation missing on: " + method.getName());
        }

        String file = excelSource.file();
        String sheet = excelSource.sheet();
        int[] selectRowCount = excelSource.rows();

        List<JsonNode> allRows = ExcelUtil.getExcelRowsAsJsonNode(file, sheet);
        List<JsonNode> filteredRows = new ArrayList<>();

        if (selectRowCount.length != allRows.size()) {
            for (int rowIndex : selectRowCount) {
                if (rowIndex >= 1 && rowIndex <= allRows.size()) {
                    filteredRows.add(allRows.get(rowIndex - 1)); // Excel first row = header
                }
            }
        }

        Object[][] data = new Object[filteredRows.size()][1];
        for (int i = 0; i < filteredRows.size(); i++) {
            data[i][0] = filteredRows.get(i);
        }

        return data;
    }
}

