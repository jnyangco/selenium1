package listener;

import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

//To use it - register it on testng.xml suite file  (top)
//Note: Listener will only be triggered when you run the xml test suite
public class MyTestNGListener implements ITestListener{
	
	//Note: this function will be called (1-you catch the error/2-unknow error like 404, browser hangs, memory issue)
	public void onTestFailure(ITestResult result) {
		System.out.println("***************************Test Failed"+result.getName());
		System.out.println(result.getThrowable().getMessage());
		
		//use "test" object to add the error to the reports
		ExtentTest test = (ExtentTest)result.getTestContext().getAttribute("test");
		//Reporter.getCurrentTestResult().getTestContext().setAttribute("criticalFailure", "Y");
		test.log(Status.FAIL, result.getThrowable().getMessage());
	}
	
	public void onTestSuccess(ITestResult result) {
		System.out.println("***************************Test Passed "+result.getName());
		ExtentTest test = (ExtentTest)result.getTestContext().getAttribute("test");
		test.log(Status.PASS, result.getName()+" -  Test Passed");
	
	}
	
	public void onTestSkipped(ITestResult result) {
		System.out.println("***************************Test Skipped "+result.getName());
		ExtentTest test = (ExtentTest)result.getTestContext().getAttribute("test");
		test.log(Status.SKIP, result.getName()+" -  Test Skipped");
	}

}
