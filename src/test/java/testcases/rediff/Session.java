package testcases.rediff;

import org.testng.ITestContext;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import keywords.ApplicationKeywords;
import testbase.BaseTest;

public class Session extends BaseTest {
	
	@Test
	public void doLogin(ITestContext context) {
//		System.out.println("Logging In");
//		test.log(Status.INFO, "Logging In");
		app.log("Start >> doLogin");
		app.log("Logging In");
		
		//i=100;
		//context.setAttribute("i_val", i);
		
		//ApplicationKeywords app = (ApplicationKeywords)context.getAttribute("app");
		//app.openBrowser("Chrome");
		//app.navigate("url"); //try to call navigate in other test function
		
		app.openBrowser("Chrome");
		app.navigate("url");
//		int i = 100/0; //will throw an exception
		app.type("txtUsername_css", "markchoi1234@yopmail.com");
		
		//failure
		//app.reportFailure("First failure - Non Critical (Text is incorrect)", false);
		
		app.type("txtPassword_xpath", "Password@1234");
		app.validateElementPresent("btnLoginButton_id");
		app.click("btnLoginButton_id");
		app.log("End >> doLogin");
		
	}
	
	@Test
	public void doLogout() {
		app.log("Start >> doLogout");
		System.out.println("Logging Out");
		app.log("End >> doLogout");
		
	}

}
