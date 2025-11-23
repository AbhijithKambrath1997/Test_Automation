package dataDriven;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DataDrivenTest {


    @Test
    public void readExcelUsingForLoop() throws IOException {
        /**
         * Reading Excel
         */
        FileInputStream inputStream = new FileInputStream(getFile("readExcel"));
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(inputStream);
        XSSFSheet xssfSheet = xssfWorkbook.getSheet("Sheet1");

        /**
         * Reading Row and Cell
         */
        int numOfRows = xssfSheet.getLastRowNum();
        int numOfColumns = xssfSheet.getRow(1).getLastCellNum();

        for (int row = 0; row <= numOfRows; row++) {
            /**
             * Get Current Row
             */
            XSSFRow xssfRow = xssfSheet.getRow(row);

            for (int column = 0; column < numOfColumns; column++) {
                /**
                 * Get Current Cell
                 */
                XSSFCell xssfCell = xssfRow.getCell(column);

                /**
                 * Fetch the value by CellType and Print
                 */
                Object cellValue = getCellValue(xssfCell);
                System.out.print(cellValue);
                System.out.print("  |  ");
            }
            System.out.println();
        }
    }

    @Test
    public void readExcelUsingIterator() throws IOException {
        /**
         * Reading Excel
         */
        FileInputStream inputStream = new FileInputStream(getFile("readExcel"));
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(inputStream);
        XSSFSheet xssfSheet = xssfWorkbook.getSheet("Sheet1");

        for (Row row : xssfSheet) {
            XSSFRow xssfRow = (XSSFRow) row;

            Iterator<Cell> cellIterator = xssfRow.cellIterator();
            while (cellIterator.hasNext()) {
                XSSFCell xssfCell = (XSSFCell) cellIterator.next();

                Object cellValue = getCellValue(xssfCell);
                System.out.print(cellValue);
                System.out.print("  |  ");
            }
            System.out.println();
        }
    }

    @Test()
    public void writeExcel() throws IOException {

        XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
        XSSFSheet xssfSheet = xssfWorkbook.createSheet("FirstSheet");
        Object[][] totalData = setUpData();

        int numOfRows = totalData.length;
        int numOfColumns = totalData[0].length;

        for (int r = 0; r < numOfRows; r++) {
            XSSFRow xssfRow = xssfSheet.createRow(r);

            for (int c = 0; c < numOfColumns; c++) {
                XSSFCell xssfCell = xssfRow.createCell(c);

                Object value = totalData[r][c];
                setCellValue(xssfCell, value);
            }
        }
        createAndDeleteExcel(xssfWorkbook);
    }

    @Test()
    public void writeExcelWithEnhancedFor() throws IOException {

        XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
        XSSFSheet xssfSheet = xssfWorkbook.createSheet("FirstSheet");

        Object[][] totalData = setUpData();

        int rowCount = 0;
        for (Object[] data : totalData) {
            XSSFRow xssfRow = xssfSheet.createRow(rowCount++);

            int columnCount = 0;
            for (Object value : data) {
                XSSFCell xssfCell = xssfRow.createCell(columnCount++);

                setCellValue(xssfCell, value);
            }
        }

        createAndDeleteExcel(xssfWorkbook);
    }

    @Test
    public void readExcelFormula() throws IOException {
        FileInputStream fileInputStream = new FileInputStream(getFile("readExcel"));
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(fileInputStream);
        XSSFSheet xssfSheet = xssfWorkbook.getSheet("Sheet2");

        for (Row row : xssfSheet) {
            XSSFRow xssfRow = (XSSFRow) row;

            Iterator<Cell> cellIterator = xssfRow.cellIterator();
            while (cellIterator.hasNext()) {
                XSSFCell xssfCell = (XSSFCell) cellIterator.next();

                Object cellValue = getCellValue(xssfCell);
                System.out.print(cellValue);
                System.out.print("  |  ");
                if (xssfCell.getCellType().equals(CellType.FORMULA)) {
                    System.out.println(xssfCell.getNumericCellValue());
                }
            }
            System.out.println();
        }
    }

    @Test
    public void writeExcelFormula() throws IOException {
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
        XSSFSheet xssfSheet = xssfWorkbook.createSheet("Sheet1");
        XSSFRow xssfRow = xssfSheet.createRow(0);

        xssfRow.createCell(0).setCellValue(10);
        xssfRow.createCell(1).setCellValue(20);
        xssfRow.createCell(2).setCellValue(30);

        xssfRow.createCell(3).setCellFormula("A1*B1*C1");
        createAndDeleteExcel(xssfWorkbook);
    }

    @Test
    public void writeExcelFormulaUpdateSheet() throws IOException {
        FileInputStream fileInputStream = new FileInputStream(getFile("readExcel"));
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(fileInputStream);

        XSSFSheet xssfSheet = xssfWorkbook.getSheet("Sheet2");
        xssfSheet.getRow(0).createCell(4).setCellValue("Total Final");

        for (int i = 1; i < 4; i++) {
            int n = i + 1;
            xssfSheet.getRow(i).createCell(4).setCellFormula("SUM(B" + n + ":D" + n + ")");
        }
        fileInputStream.close();

        FileOutputStream fileOutputStream = new FileOutputStream(getFile("readExcel"));
        xssfWorkbook.write(fileOutputStream);
        xssfWorkbook.close();
        fileOutputStream.close();
    }

    @Test
    public void readPassword() throws IOException {
        FileInputStream fileInputStream = new FileInputStream(getFile("passwordExcel"));
        String password = "Abhi";

        XSSFWorkbook xssfWorkbook = (XSSFWorkbook) WorkbookFactory.create(fileInputStream, password);
        XSSFSheet xssfSheet = xssfWorkbook.getSheet("Sheet1");

        for (Row row : xssfSheet) {
            XSSFRow xssfRow = (XSSFRow) row;

            Iterator<Cell> cellIterator = xssfRow.cellIterator();
            while (cellIterator.hasNext()) {
                XSSFCell xssfCell = (XSSFCell) cellIterator.next();

                Object cellValue = getCellValue(xssfCell);
                System.out.print(cellValue);
                System.out.print("  |  ");
            }
            System.out.println();
        }
    }

    @Test
    public void testCellStyling() throws IOException {
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
        XSSFSheet xssfSheet = xssfWorkbook.createSheet("Sheet1");
        XSSFRow xssfRow = xssfSheet.createRow(0);

        //Set Background Color
        CellStyle cellStyle1 = xssfWorkbook.createCellStyle();
        cellStyle1.setFillBackgroundColor(IndexedColors.DARK_BLUE.getIndex());
        cellStyle1.setFillPattern(FillPatternType.BIG_SPOTS);
        cellStyle1.setFillForegroundColor(IndexedColors.WHITE1.getIndex());

        XSSFCell xssfCell1 = xssfRow.createCell(0);
        xssfCell1.setCellValue("Hello");
        xssfCell1.setCellStyle(cellStyle1);

        //Set Foreground Color
        CellStyle cellStyle2 = xssfWorkbook.createCellStyle();
        cellStyle2.setFillForegroundColor(IndexedColors.RED.getIndex());
        cellStyle2.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        XSSFCell xssfCell2 = xssfRow.createCell(1);
        xssfCell2.setCellValue("Hello");
        xssfCell2.setCellStyle(cellStyle2);

        createAndDeleteExcel(xssfWorkbook);
    }

    @Test
    public void testHasMapToCell() throws IOException {
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
        XSSFSheet xssfSheet = xssfWorkbook.createSheet("Sheet1");

        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("1", "ABC");
        hashMap.put("2", "PQR");
        hashMap.put("3", "XYZ");

        int row = 0;
        for (Map.Entry<String, String> entry : hashMap.entrySet()) {
            XSSFRow xssfRow = xssfSheet.createRow(row++);

            xssfRow.createCell(0).setCellValue(String.valueOf(entry.getKey()));
            xssfRow.createCell(1).setCellValue(String.valueOf(entry.getValue()));
        }
        createAndDeleteExcel(xssfWorkbook);
    }

    @Test
    public void testCellToHashMap() throws IOException {
        FileInputStream fileInputStream = new FileInputStream(getFile("readExcel"));
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(fileInputStream);
        XSSFSheet xssfSheet = xssfWorkbook.getSheet("Sheet3");

        int row = xssfSheet.getLastRowNum();
        Map<String, String> hashMap = new HashMap<>();

        for (int i = 0; i <= row; i++) {
            String key = xssfSheet.getRow(i).getCell(0).getStringCellValue();
            String value = xssfSheet.getRow(i).getCell(1).getStringCellValue();

            hashMap.put(key, value);
        }
        System.out.println(hashMap);
    }

    private String getFile(String key) {
        return "src/test/resources/excel/" + key + ".xlsx";
    }

    private void createAndDeleteExcel(XSSFWorkbook xssfWorkbook) throws IOException {
        String filePath = "src/test/resources/excel/writeExcel" + RandomStringUtils.randomNumeric(2) + ".xlsx";
        FileOutputStream fileOutputStream = new FileOutputStream(filePath);
        xssfWorkbook.write(fileOutputStream);
        fileOutputStream.close();
        System.out.println("Success");
        File file = new File(filePath);
        if (file.delete()) {
            System.out.println("Excel file deleted successfully.");
        } else {
            System.out.println("Failed to delete Excel file.");
        }
    }

    public static void setCellValue(XSSFCell cell, Object value) {
        if (cell == null || value == null) return;

        switch (value) {
            case String s -> cell.setCellValue(s);
            case Integer i -> cell.setCellValue(i);
            case Double d -> cell.setCellValue(d);
            case Boolean b -> cell.setCellValue(b);
            case java.util.Date date -> cell.setCellValue(date);
            default -> throw new IllegalArgumentException("Unsupported value type: " + value.getClass());
        }
    }

    private Object getCellValue(XSSFCell xssfCell) {
        if (xssfCell == null) {
            return "";
        }
        return switch (xssfCell.getCellType()) {
            case STRING -> xssfCell.getStringCellValue();
            case NUMERIC -> xssfCell.getNumericCellValue();
            case BOOLEAN -> xssfCell.getBooleanCellValue();
            case FORMULA -> xssfCell.getCellFormula();
            case BLANK, _NONE, ERROR -> "";
        };
    }

    private Object[][] setUpData() {
        return new Object[][]{{"Id", "Name", "IsWorking"},
                {1, "ABC", true},
                {2, "PQR", false},
                {3, "XZ", true}
        };
    }
}

