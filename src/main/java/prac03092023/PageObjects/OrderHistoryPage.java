package prac03092023.PageObjects;

import java.util.List;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import org.testng.asserts.Assertion;
import org.testng.asserts.SoftAssert;

import prac03092023.Abstract.AbstractClass;

public class OrderHistoryPage extends AbstractClass {
	WebDriver driver;

	public OrderHistoryPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//h1[@class='hero-primary']")
	WebElement centerTextEle;

	@FindBy(xpath = "//tbody//td[2]")
	List<WebElement> producthistoryListEle;

	public String getCenterText() {
		return centerTextEle.getText();
	}

	public boolean checkProductPresent(String product) {
		return producthistoryListEle.stream().anyMatch(s -> s.getText().equalsIgnoreCase(product));
	}

	public void validatePriceOfAllOrders() {
		SoftAssert as = new SoftAssert();
		for (WebElement ele : producthistoryListEle) {
			if (ele.getText().contains("iphone 13 pro")) {
				ele.getText();
				System.out.println(
						ele.getText() + "   " + ele.findElement(By.xpath("following-sibling::td[1]")).getText());
				as.assertEquals(ele.findElement(By.xpath("following-sibling::td[1]")).getText().split(" ")[1],
						"231500");
			}
			if (ele.getText().contains("zara coat 3")) {
				System.out.println(
						ele.getText() + "   " + ele.findElement(By.xpath("following-sibling::td[1]")).getText());
				as.assertEquals(ele.findElement(By.xpath("following-sibling::td[1]")).getText().split(" ")[1],
						"31500");
			}
			if (ele.getText().contains("adidas original")) {
				System.out.println(
						ele.getText() + "   " + ele.findElement(By.xpath("following-sibling::td[1]")).getText());
				as.assertEquals(ele.findElement(By.xpath("following-sibling::td[1]")).getText().split(" ")[1],
						"31500");
			}

		}

		as.assertAll();

	}
}
