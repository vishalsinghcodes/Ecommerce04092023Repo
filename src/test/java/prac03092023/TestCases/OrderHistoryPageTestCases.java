package prac03092023.TestCases;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import prac03092023.Abstract.AbstractClass;
import prac03092023.PageObjects.CartPage;
import prac03092023.PageObjects.CheckOutPage;
import prac03092023.PageObjects.ConfirmationPage;
import prac03092023.PageObjects.LandingPage;
import prac03092023.PageObjects.OrderHistoryPage;
import prac03092023.PageObjects.ProductSelectionPage;
import prac03092023.TestComponent.BaseClass;

public class OrderHistoryPageTestCases extends BaseClass {
	
	@Test(dataProvider = "dataFromJson", groups = "JSONUsed" , enabled = false)
	public void OrderHistoryPriceVerificationAllproducts(HashMap<String,String> data) throws IOException, InterruptedException {
		LandingPage landingpage = launchApplication();
		ProductSelectionPage productSelectionPage = landingpage.loginApplication(data.get("username"), data.get("password"));
		Thread.sleep(2000);
		OrderHistoryPage orderHistoryPage =  productSelectionPage.clickOrderButton();
		validatePriceOfAllOrders();
		System.out.println("$$$$$$$$$$$$$$$");
		
		
	}
	
	
	@DataProvider
	public Object[] dataFromJson() throws IOException {
		String pathToJson = System.getProperty("user.dir") + "\\src\\test\\java\\prac03092023\\TestData\\data.json";
		List<HashMap<String, String>> data = stringOfJson(pathToJson);
		int sizeOfList = data.size();
		Object[] datatotest = new Object[sizeOfList];
		for (int i = 0; i < sizeOfList; i++) {
			datatotest[i] = data.get(i);
		}

		return datatotest; 

	}
	
	

}
