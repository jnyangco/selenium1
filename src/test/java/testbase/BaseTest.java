package testbase;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.ITestContext;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import keywords.ApplicationKeywords;
import reports.ExtentManager;
import runner.ReadingXLS;

//Note: ExtentTest should be used by other layer (ApplicationKeywords, Generic, Validation)
//to do that, set it using -> app.setReports(test);

public class BaseTest {
	
	public ApplicationKeywords app;
	public ExtentReports rep;
	public ExtentTest test;
	
	@BeforeTest(alwaysRun = true)
	/**
	public void beforeTest(ITestContext context) {
		System.out.println("\n-------------Before Test-------------");
		//1st -> initialize application keywords
		//init and share it will all tests
		app = new ApplicationKeywords(); //1 app keyword object for entire test (All @Test annotation in a test)
										//In a Test (meaning in XML -> Suite > TEST > classes > class > methods
		context.setAttribute("app", app);
		
		//init the reporting for the test case
		rep = ExtentManager.getReports();
		System.out.println("TEST CASE -> " +context.getCurrentXmlTest().getName());
		test = rep.createTest(context.getCurrentXmlTest().getName());
		test.log(Status.INFO, "Starting Test -> " +context.getCurrentXmlTest().getName());
		
		//init rep + test -> so we can retrieve rep on every method
		app.setReports(test);
		context.setAttribute("report", rep);
		context.setAttribute("test", test);
		*/
	public void beforeTest(ITestContext context) throws NumberFormatException, FileNotFoundException, IOException, ParseException {
		System.out.println("----------Before Test---------");
//		TEMP COMMENT HERE
//		String datafilpath = context.getCurrentXmlTest().getParameter("datafilpath");
//		String dataFlag = context.getCurrentXmlTest().getParameter("dataflag");
//		String iteration = context.getCurrentXmlTest().getParameter("iteration");
//		String sheetName = context.getCurrentXmlTest().getParameter("suitename");
//		// suitename(sheetname)
//		System.out.println("datafilpath -> " +datafilpath);
//		System.out.println("dataFlag -> " +dataFlag);
//		System.out.println("iteration -> " +iteration);
//		System.out.println("sheetName -> " +sheetName);
//		// reading data from JSON
//		//JSONObject data = new DataUtil().getTestData(datafilpath, dataFlag, Integer.parseInt(iteration));
//		JSONObject data = new ReadingXLS().getTestData(sheetName, dataFlag, (Integer.parseInt(iteration)+1), datafilpath);
//		//JSONObject data = from xls
//		context.setAttribute("data", data);
//		String runmode = (String)data.get("runmode");
		
		// what is the path to data json / xls
		// what is the data flag
		// what is the iteration number
		// read the data and keep it in a map
		
		
		// init the reporting for the test
		rep = ExtentManager.getReports();
		test =rep.createTest(context.getCurrentXmlTest().getName());
		test.log(Status.INFO, "Starting Test "+context.getCurrentXmlTest().getName());
//		test.log(Status.INFO, "Data "+data.toString());

		context.setAttribute("report", rep);
		context.setAttribute("test", test);
//        if(!runmode.equals("Y")) {
//        	test.log(Status.SKIP, "Skpping as Data Runmode is N");
//        	throw new SkipException("Skpping as Data Runmode is N");
//		}
		
		
		// init and share it with all tests
		app = new ApplicationKeywords(); // 1 app keyword object for entire test -All @Test
        app.setReports(test);
		
		app.openBrowser("Chrome");
//        app.defaultLogin();
		
		context.setAttribute("app", app);
		
	}
	
	@BeforeMethod(alwaysRun = true)
	public void beforeMethod(ITestContext context) {
		System.out.println("*************Before Method*************");
		
		test = (ExtentTest) context.getAttribute("test");
		
		//Stop execution (other methods) when critical failure
		String criticalFailure = (String)context.getAttribute("criticalFailure");
		if(criticalFailure != null && criticalFailure.equals("Y")) {
			test.log(Status.SKIP, "Critical Failure in Previous Tests");//skip the next method if previous method is failed (critical failure)
			throw new SkipException("Critical Failure in Previous Tests");//this will skip in TestNG
		}
		
		//2nd -> get the value of app
		app = (ApplicationKeywords)context.getAttribute("app");
		
		//this we can keep 1 rep+test on a test case and just keep on logging on the reports
		rep = (ExtentReports) context.getAttribute("report");
		//test = (ExtentTest) context.getAttribute("test"); //moved to top
		
	}
	
	
	@AfterTest(alwaysRun = true)
	public void quit(ITestContext context) {
		app = (ApplicationKeywords)context.getAttribute("app");
		if(app!=null)
			app.quit();
		
		rep = (ExtentReports)context.getAttribute("report");
		
		if(rep != null)
			rep.flush();
	}
	

}
