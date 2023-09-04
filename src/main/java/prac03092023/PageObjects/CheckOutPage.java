package prac03092023.PageObjects;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.locators.RelativeLocator;

import prac03092023.Abstract.AbstractClass;

public class CheckOutPage extends AbstractClass {
	WebDriver driver;

	public CheckOutPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath="//div[contains(text(),'CVV Code')]/following-sibling::input")
	WebElement cvvTextEle;
	
	@FindBy(xpath="//div[contains(text(),'Name on Card')]/following-sibling::input")
	WebElement nameOnCardEle;
	
	@FindBy(xpath="//*[@placeholder='Select Country']")
	WebElement countryAutoEle;
	
	@FindBy(css="button.ng-star-inserted span")
	List<WebElement> suggestionListEles;
	
	@FindBy(css=".btnn.action__submit.ng-star-inserted")
	WebElement placeOrderButtonEle;
	
	public void fillFormOnCheckoutPage(String cvv, String nameOnCard, String countrysub , String country) {
		cvvTextEle.sendKeys(cvv);
		nameOnCardEle.sendKeys(nameOnCard);
		countryAutoEle.sendKeys(countrysub);
		visibilityofWebElements(suggestionListEles);
		suggestionListEles.stream().filter(s->s.getText().equalsIgnoreCase(country)).collect(Collectors.toList()).get(0).click();	
	}
	
	public ConfirmationPage clickPlaceOrderButton() {
		placeOrderButtonEle.click();
		ConfirmationPage confirmationpage = new ConfirmationPage(driver);
		return confirmationpage;
	}

	
	
	

}
