package prac03092023.TestCases;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import javax.security.auth.callback.ConfirmationCallback;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
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

public class ProductSelectionPageTestCases extends BaseClass {

	@Test(dataProvider = "getcorrectData", groups = "DatabaseUsed")
	public void buyproductusingdbFullFlow(String username, String password) throws IOException, InterruptedException {
		LandingPage landingpage = launchApplication();
		ProductSelectionPage productSelectionPage = landingpage.loginApplication(username, password);
		Thread.sleep(2000);
		productSelectionPage.addToCart("ADIDAS ORIGINAL");
		CartPage cartPage = productSelectionPage.clickCartButton();
		Assert.assertTrue(cartPage.checkProduct("ADIDAS ORIGINAL"));
		CheckOutPage checkOutPage = cartPage.clickCheckoutButton();
		checkOutPage.fillFormOnCheckoutPage("123", "Vishal", "ind", "India");
		ConfirmationPage confirmationPage = checkOutPage.clickPlaceOrderButton();
		Assert.assertEquals(confirmationPage.getCenterText(), "THANKYOU FOR THE ORDER.");
	}
	
	@Test(dataProvider = "dataFromJson",groups = "JSONUsed")
	public void buyproductusingJsonFullFlowWithExcelverification(HashMap<String,String> data) throws IOException, InterruptedException {
		LandingPage landingpage = launchApplication();
		ProductSelectionPage productSelectionPage = landingpage.loginApplication(data.get("username"), data.get("password"));
		Thread.sleep(2000);
		productSelectionPage.addToCart(data.get("product"));
		productSelectionPage.addToCart("ZARA COAT 3");
		CartPage cartPage = productSelectionPage.clickCartButton();
		Assert.assertTrue(cartPage.checkProduct(data.get("product")));
		CheckOutPage checkOutPage = cartPage.clickCheckoutButton();
		checkOutPage.fillFormOnCheckoutPage(data.get("cvv"), data.get("nameoncard"), data.get("substring"), data.get("country"));
		ConfirmationPage confirmationPage = checkOutPage.clickPlaceOrderButton();
		Assert.assertEquals(confirmationPage.getCenterText(), "THANKYOU FOR THE ORDER.");
		List<String> orderidfromPage = confirmationPage.getOrderIDs();
		confirmationPage.clickdownloadExcelButton();
		Thread.sleep(3000);
		System.out.println(data.get("username"));
		System.out.println(orderidfromPage);
		Assert.assertTrue(confirmationPage.verifyOrderinExceldownloaded(data.get("username"), orderidfromPage));
		
		
	}
	
	
	
	@Test(dataProvider = "dataFromJson", dependsOnMethods = "buyproductusingJsonFullFlowWithExcelverification", groups = "JSONUsed")
	public void orderHistoryTest(HashMap<String,String> data) throws IOException, InterruptedException {
		LandingPage landingpage = launchApplication();
		ProductSelectionPage productSelectionPage = landingpage.loginApplication(data.get("username"), data.get("password"));
		Thread.sleep(2000);
		OrderHistoryPage orderHistoryPage =  productSelectionPage.clickOrderButton();
		Assert.assertTrue(orderHistoryPage.checkProductPresent(data.get("product")));
		
		
	}

	@DataProvider
	public Object[][] getcorrectData() throws SQLException {
		Object[][] dataToTest = AbstractClass.getDataFromDb("usercredentials", "Yes");
		return dataToTest;
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
