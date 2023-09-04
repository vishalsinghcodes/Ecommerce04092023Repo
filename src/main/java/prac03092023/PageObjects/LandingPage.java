package prac03092023.PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import prac03092023.Abstract.AbstractClass;


public class LandingPage extends AbstractClass{
	WebDriver driver;

	public LandingPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(id="userEmail")
	WebElement usernameFiledEle;
	
	@FindBy(id ="userPassword")
	WebElement passwordFieldEle;
	
	@FindBy(id="login")
	WebElement loginButtonEle;
	
	@FindBy(xpath="//div[@role='alert']")
	WebElement wrongLoginalertEle;
	
	
	public void gotoLandPage() {
		driver.get("https://www.rahulshettyacademy.com/client");
	}
	
	public ProductSelectionPage loginApplication(String username, String password) {
		usernameFiledEle.sendKeys(username);
		passwordFieldEle.sendKeys(password);
		loginButtonEle.click();	 
		ProductSelectionPage productSelectionPage = new ProductSelectionPage(driver);
		return productSelectionPage;
	}
	
	public String getalertMessage() {
		return wrongLoginalertEle.getText();
	}
	
	
	
	
	

}
