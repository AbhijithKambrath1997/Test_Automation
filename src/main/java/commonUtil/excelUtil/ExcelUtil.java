package commonUtil.excelUtil;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelUtil {

    public static List<JsonNode> getExcelRowsAsJsonNode(String filePath, String sheetName) {

        List<JsonNode> rows = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = WorkbookFactory.create(fis)) {

            Sheet sheet = workbook.getSheet(sheetName);
            if (sheet == null)
                throw new RuntimeException("Sheet not found: " + sheetName);

            Row headerRow = sheet.getRow(0);
            Iterator<Cell> headerIterator = headerRow.cellIterator();

            List<String> headers = new ArrayList<>();
            while (headerIterator.hasNext()) {
                headers.add(headerIterator.next().getStringCellValue());
            }

            int lastRow = sheet.getLastRowNum();

            for (int i = 1; i <= lastRow; i++) {

                Row row = sheet.getRow(i);
                if (row == null) continue;

                ObjectNode jsonRow = mapper.createObjectNode();

                for (int j = 0; j < headers.size(); j++) {

                    Cell cell = row.getCell(j);
                    String key = headers.get(j);

                    if (cell == null) {
                        jsonRow.put(key, "");
                        continue;
                    }

                    switch (cell.getCellType()) {
                        case STRING -> jsonRow.put(key, cell.getStringCellValue());
                        case NUMERIC -> jsonRow.put(key, cell.getNumericCellValue());
                        case BOOLEAN -> jsonRow.put(key, cell.getBooleanCellValue());
                        case FORMULA -> jsonRow.put(key, cell.getCellFormula());
                        case BLANK, _NONE, ERROR -> jsonRow.put(key, "");
                        default -> jsonRow.put(key, cell.toString());
                    }
                }

                rows.add(jsonRow);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return rows;
    }
}


