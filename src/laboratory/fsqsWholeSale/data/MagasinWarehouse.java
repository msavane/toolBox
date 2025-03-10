package laboratory.fsqsWholeSale.data;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

// CRUD ops for inventory to and from DB (Excel)

public class MagasinWarehouse {
    private final String filePath;

    public MagasinWarehouse(String filePath) {
        this.filePath = filePath;
    }

    // CREATE: Add new row to the Excel file
    public void addRow(List<String> data) throws IOException {
        File file = new File(filePath);
        Workbook workbook;
        Sheet sheet;

        if (file.exists()) {
            try (FileInputStream fis = new FileInputStream(file)) {
                workbook = new XSSFWorkbook(fis);
                sheet = workbook.getSheetAt(0);
            }
        } else {
            workbook = new XSSFWorkbook();
            sheet = workbook.createSheet("Sheet1");
        }

        int rowNum = sheet.getLastRowNum() + 1;
        Row row = sheet.createRow(rowNum);

        for (int i = 0; i < data.size(); i++) {
            row.createCell(i).setCellValue(data.get(i));
        }

        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            workbook.write(fos);
        }
        workbook.close();
    }

    // READ: Retrieve all rows from the Excel file
    public List<List<String>> readAllRows() throws IOException {
        List<List<String>> records = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) return records;

        try (FileInputStream fis = new FileInputStream(file); Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                List<String> rowData = new ArrayList<>();
                for (Cell cell : row) {
                    rowData.add(cell.toString());
                }
                records.add(rowData);
            }
        }
        return records;
    }

    // UPDATE: Modify data in a specific row
    public void updateRow(int rowIndex, List<String> newData) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) return;

        try (FileInputStream fis = new FileInputStream(file); Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            if (rowIndex > sheet.getLastRowNum()) return;

            Row row = sheet.getRow(rowIndex);
            if (row == null) row = sheet.createRow(rowIndex);

            for (int i = 0; i < newData.size(); i++) {
                row.createCell(i).setCellValue(newData.get(i));
            }

            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                workbook.write(fos);
            }
        }
    }

    // DELETE: Remove a specific row
    public void deleteRow(int rowIndex) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) return;

        try (FileInputStream fis = new FileInputStream(file); Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            if (rowIndex > sheet.getLastRowNum()) return;

            int lastRowNum = sheet.getLastRowNum();
            sheet.removeRow(sheet.getRow(rowIndex));

            // Shift rows up to fill the gap
            if (rowIndex < lastRowNum) {
                sheet.shiftRows(rowIndex + 1, lastRowNum, -1);
            }

            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                workbook.write(fos);
            }
        }
    }

    public static void main(String[] args) throws IOException {
      String filePath = "fsqsWholeSaleWharehouse.xlsx";
       MagasinWarehouse excelCRUD = new MagasinWarehouse(filePath);

        // Test: Add new rows
        excelCRUD.addRow(List.of("ProductID", "OrderID", "ProductName", "ProductDescription", "ProductPrice","qty/unit"));
        excelCRUD.addRow(List.of("1", "", "Cotton", "Raw Cotton", "11",""));
        excelCRUD.addRow(List.of("2", "", "Cashew", "Salted Cashew", "10",""));
        excelCRUD.addRow(List.of("3", "", "Jam", "MangoJam", "23",""));
        excelCRUD.addRow(List.of("4", "", "Golden Calabash", "Skin care", "40",""));
/*
        // Test: Read all rows
        System.out.println("Excel Data:");
        for (List<String> row : excelCRUD.readAllRows()) {
            System.out.println(row);
        }

        //Test: Update row
        excelCRUD.updateRow(1, List.of("1", "00A", "Cotton", "Raw Cotton", "11", "5"));

        // Test: Delete row (not needed for now)
        excelCRUD.deleteRow(2);
        excelCRUD.deleteRow(3);
        excelCRUD.deleteRow(4);

        // Display updated data
        System.out.println("Updated Excel Data:");
        for (List<String> row : excelCRUD.readAllRows()) {
            System.out.println(row);


        }
    */}
}
