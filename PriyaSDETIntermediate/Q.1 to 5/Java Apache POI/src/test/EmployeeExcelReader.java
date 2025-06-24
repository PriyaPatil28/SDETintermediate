// EmployeeExcelReader.java
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Iterator;

public class EmployeeExcelReader {

    private static final Path FILE = Path.of("employees.xlsx");   // path to your sheet

    public static void main(String[] args) {
        try (FileInputStream fis = new FileInputStream(FILE.toFile());
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);          // first sheet
            Iterator<Row> rowIt = sheet.iterator();

            // Skip header row (remove this block if you want to print headers too)
            if (rowIt.hasNext()) rowIt.next();

            while (rowIt.hasNext()) {
                Row row = rowIt.next();

                int empNo          = (int)  getNumericCell(row, 0);
                String empName     =        getStringCell (row, 1);
                String designation =        getStringCell (row, 2);
                double salary      =         getNumericCell(row, 3);
                String department  =        getStringCell (row, 4);

                System.out.printf(
                    "EmpNo: %-6d  Name: %-15s  Designation: %-18s  Salary: %-10.2f  Dept: %-10s%n",
                    empNo, empName, designation, salary, department
                );
            }

        } catch (IOException e) {
            System.err.println("Unable to read the Excel file → " + e.getMessage());
        }
    }

    /* ---------- tiny helper methods ---------- */

    private static double getNumericCell(Row row, int idx) {
        Cell c = row.getCell(idx, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
        return c == null ? 0 : c.getNumericCellValue();
    }
    private static String getStringCell(Row row, int idx) {
        Cell c = row.getCell(idx, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
        return c == null ? "" : c.getStringCellValue();
    }
}
