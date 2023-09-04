package prac03092023.Resources;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportNG {

	public static ExtentReports returnCreatedExtent() {
		String reportPath = System.getProperty("user.dir") + "\\ExtentReports\\Report_" + returndateString() + ".html";
		File fis = new File(reportPath);
		ExtentSparkReporter reporter = new ExtentSparkReporter(fis);
		reporter.config().setDocumentTitle("Automation Test");
		reporter.config().setReportName("Test Results");

		String Tester = System.getProperty("Tester") != null ? System.getProperty("Tester") : "VishalSinghLocalPC";

		ExtentReports extent = new ExtentReports();
		extent.setSystemInfo("Tester", Tester);
		extent.attachReporter(reporter);

		return extent;

	}

	public static String returndateString() {
		return new SimpleDateFormat("YYYYMMdd_HHmmSS").format(Calendar.getInstance().getTime());
	}

}
