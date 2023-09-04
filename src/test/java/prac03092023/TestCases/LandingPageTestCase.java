package prac03092023.TestCases;

import java.io.IOException;
import java.sql.SQLException;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import prac03092023.Abstract.AbstractClass;
import prac03092023.PageObjects.LandingPage;
import prac03092023.TestComponent.BaseClass;
import prac03092023.TestComponent.RetryClass;

public class LandingPageTestCase extends BaseClass {
	

	@Test(dataProvider = "getcorrectData", groups = "DatabaseUsed")
	public void Correctloginvalidation(String username, String password) throws IOException, InterruptedException {
		LandingPage landingpage = launchApplication();
		landingpage.loginApplication(username, password);
		Thread.sleep(2000);
		Assert.assertTrue(landingpage.getPageURL().contains("https://www.rahulshettyacademy.com/client/dashboard/dash"));
	}
	
	@Test(dataProvider = "getWrongData", groups = "DatabaseUsed", retryAnalyzer = RetryClass.class)
	public void inCorrectloginvalidation(String username, String password) throws IOException, InterruptedException {
		LandingPage landingpage = launchApplication();
		landingpage.loginApplication(username, password);
		Thread.sleep(1000);
		String errror_message =  landingpage.getalertMessage();
		Assert.assertEquals(errror_message, "Incorrect email or password.");
	}
	
	@DataProvider
	public Object[][] getcorrectData() throws SQLException {
		Object[][] dataToTest = AbstractClass.getDataFromDb("usercredentials", "Yes");
		return dataToTest;
	}
	
	@DataProvider
	public Object[][] getWrongData() throws SQLException {
		Object[][] dataToTest = AbstractClass.getDataFromDb("usercredentials", "No");
		return dataToTest;
	}
	
	

}
