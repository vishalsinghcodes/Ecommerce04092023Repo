package prac03092023.PageObjects;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


import prac03092023.Abstract.AbstractClass;

public class ProductSelectionPage extends AbstractClass {
	WebDriver driver;

	protected ProductSelectionPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//div[@class='card-body']")
	List<WebElement> cardsEle;
	
	@FindBy(css=".ng-tns-c31-0.ng-star-inserted")
	WebElement spinnerEle;
	
	@FindBy(xpath="//div[@aria-label='Product Added To Cart']")
	WebElement productAddedToast;

	public void addToCart(final String productName) {
		cardsEle.stream().filter(s -> {
			return s.findElement(By.tagName("b")).getText().equalsIgnoreCase(productName);
		}).map(s -> s.findElement(By.xpath("button[@class='btn w-10 rounded']"))).collect(Collectors.toList())
				.get(0).click();
		
		//button//i[@class='fa fa-shopping-cart']
		visibilityofWebEle(productAddedToast);
		invisibilityofWebEle(spinnerEle);
		visibilityofWebElements(cardsEle);
	}
	
	

}
