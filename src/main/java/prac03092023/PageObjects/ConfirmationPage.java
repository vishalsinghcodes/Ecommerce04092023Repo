package prac03092023.PageObjects;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import prac03092023.Abstract.AbstractClass;

public class ConfirmationPage extends AbstractClass {
	WebDriver driver;

	protected ConfirmationPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//h1[@class='hero-primary']")
	WebElement centerTextEle;

	@FindBy(xpath = "//button[text()='Click To Download Order Details in Excel']")
	WebElement downloadExcelButtonEle;

	@FindBy(css = "label.ng-star-inserted")
	List<WebElement> orderListEle;

	public String getCenterText() {
		return centerTextEle.getText();
	}

	public void clickdownloadExcelButton() {
		downloadExcelButtonEle.click();
	}

	public List<String> getOrderIDs() {
		List<String> orderIdList = orderListEle.stream().map(s -> s.getText().split(" ")[1])
				.collect(Collectors.toList());
		return orderIdList;
	}

	public boolean verifyOrderinExceldownloaded(String username, List<String> orderIdFromPage) throws IOException, InterruptedException {
		List<String> orderIdsFromExcel = new ArrayList<String>();
		String pathToExcel = System.getProperty("user.dir") + "\\Downloads\\order-invoice_" + username.split("@")[0]
				+ ".xlsx";
		FileInputStream fis = new FileInputStream(pathToExcel);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet reqSheet = workbook.getSheet("data");
		int col = 0;
		int k = 0;
		Iterator<Row> rowit = reqSheet.rowIterator();
		Row firstRow = rowit.next();
		Iterator<Cell> cit = firstRow.cellIterator();
		while (cit.hasNext()) {
			Cell val = cit.next();
			if (val.getStringCellValue().contains("Invoice Number"))
				col = k;
			k++;
		}
		while (rowit.hasNext()) {
			Row r = rowit.next();
			// Iterator<Cell> citin = r.cellIterator();
			orderIdsFromExcel.add(r.getCell(col).getStringCellValue());
		}
		
		
		workbook.close();
		fis.close();
		
		List<String> sortedListFromExcel = orderIdsFromExcel.stream().sorted().collect(Collectors.toList());
		
		for (String a : orderIdsFromExcel) {
			System.out.println(a);
		}

		for (String a : orderIdFromPage) {
			System.out.println(a);
		}
		
		Thread.sleep(2000);
		
		File fi = new File(pathToExcel);
		if (fi.exists()) {
			System.out.println("file existed");
			fi.delete();
			System.out.println("file deleted");
		}
		if(fi.exists()) {
			System.out.println("file not deleted");
		}
		
		
		return sortedListFromExcel.equals(getOrderIDs());

	}

}
