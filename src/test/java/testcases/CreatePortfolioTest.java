package testcases;

import org.testng.annotations.Test;

import keywords.ApplicationKeywords;
import keywords.GenericKeywords;
import keywords.ValidationKeywords;

public class CreatePortfolioTest {
	//no webdriver code
	//login, create, verify
	
	@Test
	public void createPortfolioTest() {
		//GenericKeywords keywordsGeneric = new GenericKeywords();
		//ValidationKeywords keywordsValidation = new ValidationKeywords();
		ApplicationKeywords app = new ApplicationKeywords(); //constructor -> automatillally run (init properties)
		
		app.openBrowser("Chrome");
		app.navigate("url");
		app.type("txtUsername_css", "markchoi1234@yopmail.com");
		app.type("txtPassword_xpath", "Password@1234");
		app.validateElementPresent("btnLoginButton_id");
		app.click("btnLoginButton_id");
		
		app.validateLogin();
		//app.selectDateFromCalendar();
		
	}
	
	@Test
	public void createReservation() {
		
	}
	
	
}
