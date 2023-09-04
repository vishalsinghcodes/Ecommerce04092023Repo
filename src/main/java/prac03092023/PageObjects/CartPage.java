package prac03092023.PageObjects;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import prac03092023.Abstract.AbstractClass;

public class CartPage extends AbstractClass {
	WebDriver driver;

	public CartPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(css = ".cartSection h3")
	List<WebElement> prolistEle;

	@FindBy(xpath = "//button[contains(text(),'Checkout')]")
	WebElement checkOutButtonEle;

	public boolean checkProduct(String product) {
		return prolistEle.stream().anyMatch(s -> s.getText().equalsIgnoreCase(product));

	}

	public CheckOutPage clickCheckoutButton() {
		checkOutButtonEle.click();
		CheckOutPage checkOutPage = new CheckOutPage(driver);
		return checkOutPage;
	}

}
