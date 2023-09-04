package prac03092023.TestComponent;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import prac03092023.Resources.ExtentReportNG;

public class Listeners extends BaseClass implements ITestListener {
	WebDriver driver;
	ExtentReports extent = ExtentReportNG.returnCreatedExtent();
	ExtentTest test;
	ThreadLocal<ExtentTest> safe = new ThreadLocal<ExtentTest>();
	String screeShotPath = null;

	@Override
	public void onTestStart(ITestResult result) {
		test = extent.createTest(result.getMethod().getMethodName());
		safe.set(test);
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		safe.get().log(Status.PASS, result.getMethod().getMethodName());

		try {
			driver = (WebDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			screeShotPath = getscreenShotPath(driver, result.getMethod().getMethodName());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		safe.get().addScreenCaptureFromPath(screeShotPath, result.getMethod().getMethodName());

	}

	@Override
	public void onTestFailure(ITestResult result) {
		safe.get().log(Status.FAIL, result.getMethod().getMethodName());
		safe.get().log(Status.FAIL, result.getThrowable());

		try {
			driver = (WebDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			screeShotPath = getscreenShotPath(driver, result.getMethod().getMethodName());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		safe.get().addScreenCaptureFromPath(screeShotPath, result.getMethod().getMethodName());
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		safe.get().log(Status.SKIP, result.getMethod().getMethodName());
		safe.get().log(Status.SKIP, result.getThrowable());
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// not implemented
	}

	@Override
	public void onTestFailedWithTimeout(ITestResult result) {
		onTestFailure(result);
	}

	@Override
	public void onStart(ITestContext context) {
		// not implemented
	}

	@Override
	public void onFinish(ITestContext context) {
		extent.flush();
	}

}
