package keywords;

import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.openqa.selenium.By;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentTest;

public class ApplicationKeywords extends ValidationKeywords {
	
	//constructor -> automatically triggered once object of this class is created
	//initialize different objects (path, softAssert)
	public ApplicationKeywords() {
		System.out.println("====Constructor -> ApplicationKeywords====");
		//init properties file
		//String path = System.getProperty("user.dir")+"//src//test//resources//env.properties";
		String path = System.getProperty("user.dir")+"//src//test//resources//env.properties";
		System.out.println("PATH -> " +path);
		
		prop = new Properties();
		envProp = new Properties();
		try {
			FileInputStream fis = new FileInputStream(path);
			prop.load(fis);
			
			String env = prop.getProperty("env")+".properties";
			path = System.getProperty("user.dir")+"//src//test//resources//"+env;
			fis = new FileInputStream(path);
			envProp.load(fis);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		softAssert = new SoftAssert();
		
		System.out.println("URL -> " +prop.getProperty("url"));
		System.out.println("Platform -> " +prop.getProperty("platform"));
	}
	
	public void login() {
		
	}
	
	public void selectDateFromCalendar(String date) {
		log("Selecting Date "+date);
		
		try {
			Date currentDate = new Date();
			Date dateToSel=new SimpleDateFormat("d-MM-yyyy").parse(date);
			String day=new SimpleDateFormat("d").format(dateToSel);
			String month=new SimpleDateFormat("MMMM").format(dateToSel);
			String year=new SimpleDateFormat("yyyy").format(dateToSel);
			String monthYearToBeSelected=month+" "+year;
			String monthYearDisplayed=getElement("monthyear_css").getText();
			
			while(!monthYearToBeSelected.equals(monthYearDisplayed)) {
				click("datebackButoon_xpath");
				monthYearDisplayed=getElement("monthyear_css").getText();
			}
			driver.findElement(By.xpath("//td[text()='"+day+"']")).click();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void verifyStockAdded() {
		
	}
	
//	public void defaultLogin() {
//		navigate("url");
//		type("txtUsername_css", envProp.getProperty("admin_user_name"));
//		type("txtPassword_xpath", envProp.getProperty("admin_password"));
//		click("btnLoginButton_id");
//		waitForPageToLoad();
//		wait(5);
//	}
	
	public void defaultLogin() {
		navigate("url");
		type("slTxtUsername_xpath", envProp.getProperty("admin_user_name"));
		type("slTxtPassword_xpath", envProp.getProperty("admin_password"));
		click("slBtnLogin_xpath");
		waitForPageToLoad();
		wait(5);
	}
	
	public int findCurrentStockQuantity(String companyName) {
		log("Finding current stock quantity for "+ companyName);
		int row = getRowNumWithCellData("stocktable_css",companyName);
		if(row==-1) {
			log("Current Stock Quantity is 0 as Stock not present in list");
			return 0;
		}
		// table#stock > tbody > tr:nth-child(2) >td:nth-child(4)
		String quantity = driver.findElement(By.cssSelector(prop.getProperty("stocktable_css")+" > tr:nth-child("+row+") >td:nth-child(4)")).getText();
		log("Current stock Quantity "+quantity);
		return Integer.parseInt(quantity);
	}

	public void goToBuySell(String companyName) {
		log("Selecting the company row "+companyName );
		int row = getRowNumWithCellData("stocktable_css", companyName);
		if(row==-1) {
			log("Stock not present in list");
		}
		driver.findElement(By.cssSelector(prop.getProperty("stocktable_css")+" > tr:nth-child("+row+") >td:nth-child(1)")).click();
		driver.findElement(By.cssSelector(prop.getProperty("stocktable_css")+"  tr:nth-child("+row+") input.buySell" )).click();
		
	}

	public void goToTransactionHistory(String companyName) {
	    log("Selecting the company row "+companyName );
		int row = getRowNumWithCellData("stocktable_css", companyName);
		if(row==-1) {
			log("Stock not present in list");
			// report failure
		}
		driver.findElement(By.cssSelector(prop.getProperty("stocktable_css")+" > tr:nth-child("+row+") >td:nth-child(1)")).click();
		driver.findElement(By.cssSelector(prop.getProperty("stocktable_css")+"  tr:nth-child("+row+") input.equityTransaction" )).click();
		
	}

	public void setReports(ExtentTest test) {
		//this means from ApplicationKeywords < ValidationKeywords < GenericKeywords
		this.test = test;
		
	}
}
