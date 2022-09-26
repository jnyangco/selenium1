package testcases.rediff;

import org.json.simple.JSONObject;
import org.testng.ITestContext;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import keywords.ApplicationKeywords;
import testbase.BaseTest;

public class PortfolioManagement extends BaseTest {
	
	@Test
	public void createPortfolio(ITestContext context) {
		app.log("Start >> createPortfolio");
//		System.out.println("Creating Portfolio");
//		test.log(Status.INFO, "Creating Portfolio");
//		app.log("Creating Portfolio");
		//failure
		//app.reportFailure("Reporting some non-critical failure", false);
		
//		app.assertAll();
		
		//System.out.println(context.getAttribute("i_val"));
		
		//put it on @BeforeMethod
		//ApplicationKeywords app = (ApplicationKeywords)context.getAttribute("app");
		//app.navigate("url");
		
		JSONObject data = (JSONObject)context.getAttribute("data");
		String portfolioName=(String)data.get("portfolioname");
		
		app.log("Creating Profolio");
		app.click("createPortfolio_id");
		app.clear("porfolioname_id");
		app.type("porfolioname_id", portfolioName);
		app.click("createPortfolioButton_css");
		app.waitForPageToLoad();
		app.validateSelectedValueInDropDown("portfolioid_dropdown_id",portfolioName);
		app.log("End >> createPortfolio");
	}
	

	@Test
	public void deletePortfolio(ITestContext context) {
		JSONObject data = (JSONObject)context.getAttribute("data");
		String portfolioName=(String)data.get("portfolioname");		
		app.log("Deleting Profolio");
        app.selectByVisibleText("portfolioid_dropdown_id", portfolioName);
        app.waitForPageToLoad();
        app.click("deletePortfolio_id");
        app.acceptAlert();
        app.validateSelectedValueNotInDropDown("portfolioid_dropdown_id",portfolioName);
	}
	
	@Test
	public void selectPortFolio(ITestContext context) {
		
		JSONObject data = (JSONObject)context.getAttribute("data");
		String portfolioName=(String)data.get("portfolioname");
		app.log("Selecting Profolio");
		app.selectByVisibleText("portfolioid_dropdown_id", portfolioName);
        app.waitForPageToLoad();
	}
	
	@Test
	public void test() {
		
	}

}
