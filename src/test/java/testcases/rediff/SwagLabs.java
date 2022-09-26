package testcases.rediff;

import org.testng.ITestContext;

import testbase.BaseTest;

public class SwagLabs extends BaseTest {

	public void testLogin(ITestContext context) {
		app.log("Start >> doLogin");
//		app.openBrowser("Chrome");
		app.navigate("url");
		app.waitForPageToLoad();
		
		app.type("slTxtUsername_xpath", "standard_user");
		app.type("slTxtPassword_xpath", "secret_sauce");
		app.click("slBtnLogin_xpath");
		app.log("End >> doLogin");
	}
	
	
}
