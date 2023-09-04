package prac03092023.TestComponent;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryClass implements IRetryAnalyzer {

	int tryCount = 0;
	int maxCount = 1;

	@Override
	public boolean retry(ITestResult result) {
		
		if (tryCount < maxCount) {
			tryCount++;
			return true;
		}
		return false;
	}

}
