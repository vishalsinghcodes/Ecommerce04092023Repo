package prac03092023.Abstract;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import prac03092023.PageObjects.CartPage;
import prac03092023.PageObjects.OrderHistoryPage;

import java.sql.ResultSetMetaData;

public class AbstractClass {
	WebDriver driver;

	protected AbstractClass(WebDriver driver) {
		this.driver = driver;
	}
	
	@FindBy(xpath ="//button[contains(@routerlink,'cart')]")
	WebElement cartButtonEle;
	
	@FindBy(xpath="//button[@routerlink='/dashboard/myorders']")
	WebElement orderButtonEle;
	

	public static Object[][] getDataFromDb(String tableName, String correctness) throws SQLException {
		String host = "localhost";
		String port = "3306";
		Connection dbconn = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/ecommerceqadb", "root",
				"Nokia@4230");
		Statement statement = dbconn.createStatement();
		ResultSet rs = statement.executeQuery("Select username, password from "+tableName+" where correctness = "+"'"+correctness+"'");
		int row = getNumberOfRows(tableName, correctness);
		int col = getNumberOfCols(tableName, correctness);

		Object data[][] = new Object[row][col];
		for (int i = 0; i < row; i++) {
			rs.next();
			for (int j = 0; j < col; j++) {
				data[i][j] = rs.getString(j + 1);
			}
		}
		return data;

	}

	public static int getNumberOfRows(String tableName, String Correctness) throws SQLException {
		String host = "localhost";
		String port = "3306";
		Connection dbconn = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/ecommerceqadb", "root",
				"Nokia@4230");
		Statement statement = dbconn.createStatement();
		ResultSet rs = statement.executeQuery("Select count(*) as rowcount from " + tableName +" where correctness = '"+Correctness+"'");
		rs.next();
		int noOfrows = Integer.parseInt(rs.getString("rowcount"));
		return noOfrows;

	}

	public static int getNumberOfCols(String tableName, String Correctness) throws SQLException {
		String host = "localhost";
		String port = "3306";
		Connection dbconn = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/ecommerceqadb", "root",
				"Nokia@4230");
		Statement statement = dbconn.createStatement();
		ResultSet rs = statement.executeQuery("Select username, password from "+tableName+" where correctness = "+"'"+Correctness+"'" );
		ResultSetMetaData metadata = rs.getMetaData();
		return metadata.getColumnCount();
		
	}

	public String getPageURL() {
		return driver.getCurrentUrl();
	}
	
	
	public void visibilityofWebEle(WebElement ele) {
		WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(10));
		w.until(ExpectedConditions.visibilityOf(ele));
	}
	
	public void invisibilityofWebEle(WebElement ele) {
		WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(10));
		w.until(ExpectedConditions.invisibilityOf(ele));
	}
	
	public void visibilityofWebElements(List<WebElement> ele) {
		WebDriverWait w = new WebDriverWait(driver, Duration.ofSeconds(10));
		w.until(ExpectedConditions.visibilityOfAllElements(ele));
	}
	
	public CartPage clickCartButton() {
		cartButtonEle.click();
		CartPage cartPage = new CartPage(driver);
		return cartPage;
	}
	
	public OrderHistoryPage clickOrderButton() {
		orderButtonEle.click();
		OrderHistoryPage orderHistoryPage = new OrderHistoryPage(driver);
		return orderHistoryPage;
	}
	
}
