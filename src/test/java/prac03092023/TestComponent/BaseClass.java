package prac03092023.TestComponent;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.support.FindBy;
import org.testng.annotations.AfterMethod;
import org.testng.asserts.SoftAssert;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import freemarker.template.SimpleDate;
import io.github.bonigarcia.wdm.WebDriverManager;
import prac03092023.Abstract.AbstractClass;
import prac03092023.PageObjects.LandingPage;

public class BaseClass {

	public WebDriver driver;

	public WebDriver initializeDriver() throws IOException {
		FileInputStream fis = new FileInputStream(
				System.getProperty("user.dir") + "\\src\\main\\java\\prac03092023\\Resources\\Globalprop.properties");
		Properties prop = new Properties();
		prop.load(fis);
		String browserop = System.getProperty("browser") != null ? System.getProperty("browser")
				: prop.getProperty("browser");
		String headop = System.getProperty("headoption") != null ? System.getProperty("headoption")
				: prop.getProperty("headoption");
		String downloadPath = System.getProperty("user.dir") + "\\Downloads";

		switch (browserop) {

		case "chrome":
			ChromeOptions cop = new ChromeOptions();
			cop.addArguments(headop);
			HashMap<String, Object> prefs = new HashMap<String, Object>();
			prefs.put("download.default_directory", downloadPath);
			cop.setExperimentalOption("prefs", prefs);
			cop.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver(cop);
			break;

		case "firefox":
			FirefoxOptions fop = new FirefoxOptions();
			// 0 means to download to the desktop, 1 means to download to the default
			// "Downloads" directory, 2 means to use the directory

			FirefoxProfile profile = new FirefoxProfile();
			profile.setPreference("browser.download.folderList", 2);
			profile.setPreference("browser.download.dir", downloadPath);
			profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "text/csv,application/zip");
			fop.setProfile(profile);
			fop.addArguments(headop);
			fop.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver(fop);
			break;

		case "edge":
			EdgeOptions eop = new EdgeOptions();
			eop.addArguments(headop);
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver(eop);
			break;
		}

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.manage().window().maximize();

		return driver;

	}

	public LandingPage launchApplication() throws IOException {
		driver = initializeDriver();
		LandingPage landingpage = new LandingPage(driver);
		landingpage.gotoLandPage();
		return landingpage;
	}
	
	public List<HashMap<String, String>> stringOfJson(String pathToJson) throws IOException {
		String stringOfJson = FileUtils.readFileToString(new File(pathToJson), StandardCharsets.UTF_8);
		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String, String>> data = mapper.readValue(stringOfJson,
				new TypeReference<List<HashMap<String, String>>>() {
				});

		return data;
	}
	
	public String getscreenShotPath(WebDriver driver, String testCasename) throws IOException {
		String pathToScreenShot = System.getProperty("user.dir")+"\\ScreenShots\\"+testCasename+"_"+dateString()+".png";
		File src = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(src, new File(pathToScreenShot));
		return pathToScreenShot;
		
	}
	
	@AfterMethod(alwaysRun = true)
	public void afterMethod() {
		driver.close();
	}
	
	public String dateString() {
		return new SimpleDateFormat("YYYYMMdd_HHmmSS").format(Calendar.getInstance().getTime());
	}
	
	@FindBy(xpath = "//tbody//td[2]")
	List<WebElement> producthistoryListEle;
	
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
				as.assertEquals(ele.findElement(By.xpath("following-sibling::td[1]")).getText().split(" ")[1], "31500");
			}
			if (ele.getText().contains("adidas original")) {
				System.out.println(
						ele.getText() + "   " + ele.findElement(By.xpath("following-sibling::td[1]")).getText());
				as.assertEquals(ele.findElement(By.xpath("following-sibling::td[1]")).getText().split(" ")[1], "31500");
			}

		}

		as.assertAll();

	}
	
	

}
