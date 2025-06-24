import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;

public class ExcelReaderTest {

    @Test
    public void readEmployeeExcel() throws IOException {
        Path FILE = Path.of("employees.xlsx");

        try (FileInputStream fis = new FileInputStream(FILE.toFile());
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            int rowCount = sheet.getPhysicalNumberOfRows();

            System.out.println("\n--- Employee Excel Data ---");
            for (int i = 1; i < rowCount; i++) {
                Row row = sheet.getRow(i);
                int empNo = (int) row.getCell(0).getNumericCellValue();
                String empName = row.getCell(1).getStringCellValue();
                String designation = row.getCell(2).getStringCellValue();
                double salary = row.getCell(3).getNumericCellValue();
                String dept = row.getCell(4).getStringCellValue();

                System.out.printf("EmpNo: %d | Name: %s | Designation: %s | Salary: %.2f | Dept: %s%n",
                        empNo, empName, designation, salary, dept);
            }
        }
    }
}
