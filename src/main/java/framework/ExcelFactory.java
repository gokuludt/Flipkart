package framework;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Reporter;

/**
 * @author Gokul
 * This class is used as Excel Factory
 *
 */
public class ExcelFactory {

	public String dataFilePath = System.getProperty("user.dir") + "/src/main/resources/datatables/inputdata.xlsx";
	FileInputStream fip;
	XSSFWorkbook workbook;
	XSSFSheet worksheet;
	public String tcId;

	public void setTestCaseId(String tcId) {
		this.tcId = tcId;
	}

	public String getTestCaseId() {
		return tcId;
	}

	public void loadExcelsheet(String sheetName) {
		try {
			fip = new FileInputStream(dataFilePath);
			workbook = new XSSFWorkbook(fip);
			worksheet = workbook.getSheet(sheetName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public String getData(String TestID, String Sheet, String field) {
		String value = "";
		XSSFRow row;
		int rowNo = 0;
		int colNo = 0;

		int totalRows = worksheet.getPhysicalNumberOfRows();

		for (int i = 1; i < totalRows; i++) {
			row = worksheet.getRow(i);
			String actTestID = row.getCell(0).getStringCellValue();
			if (actTestID.equalsIgnoreCase(TestID)||actTestID.contains(TestID)) {
				rowNo = i;
				break;
			}
		}

		int totalCols = worksheet.getRow(rowNo).getPhysicalNumberOfCells();
		row = worksheet.getRow(0);

		for (int j = 0; j < totalCols; j++) {

			String actField = row.getCell(j).getStringCellValue();
			if (actField.equalsIgnoreCase(field)) {
				colNo = j;
				break;
			}
		}

		value = worksheet.getRow(rowNo).getCell(colNo).getStringCellValue();
		Reporter.log(value + " is populated in " + field);
		return value;

	}

}