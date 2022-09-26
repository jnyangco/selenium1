package keywords;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import reports.ExtentManager;

public class GenericKeywords {
	
	public WebDriver driver;
	public Properties prop;
	public Properties envProp;
	public ExtentTest test;
	public SoftAssert softAssert;
	
	public void openBrowser(String browser) {
		//System.out.println("Open the browser - " +browser);
		//test.log(Status.INFO, "Open the browser - " +browser);
		log("Open the browser - " +browser);
		
		if(browser.equals("Chrome")) {
			System.setProperty("webdriver.chrome.driver", "C:\\Selenium Configuration\\browser_jars\\chromedriver.exe");
			driver = new ChromeDriver();
		} else if(browser.equals("Mozilla")) {
			System.setProperty("webdriver.gecko.driver", "C:\\Selenium Configuration\\browser_jars\\geckodriver.exe");
			driver = new FirefoxDriver();
		} else if(browser.equals("Edge")) {
			System.setProperty("webdriver.edge.driver", "C:\\Selenium Configuration\\browser_jarsmsedgedriver.exe");
			driver = new EdgeDriver();
		}
		//implicit wait
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
	}
	
	public void navigate(String urlKey) {
		//System.out.println("Navigating to - "+ urlKey);
		//test.log(Status.INFO, "Navigating to - "+ urlKey);
		log("Navigating to - "+ urlKey);
		
		driver.get(envProp.getProperty(urlKey));
		
	}
	
	public void click(String locatorKey) {
		//System.out.println("Clicking on - "+locatorKey);
		//test.log(Status.INFO, "Clicking on - "+locatorKey);
		log("Clicking on - "+locatorKey);
		
		getElement(locatorKey).click();
	}
	
	public void type(String locatorKey, String data) {
		//System.out.println("Typing on - "+locatorKey+". Data - "+data);
		//test.log(Status.INFO, "Typing on - "+locatorKey+". Data - "+data);
		log("Typing on - "+locatorKey+". Data - "+data);
		
		getElement(locatorKey).sendKeys(data);;
	}
	
	public void clear(String locatorKey) {
		log("Clearing text field "+ locatorKey);
		getElement(locatorKey).clear();
	}
	
	public void clickEnterButton(String locatorKey) {
		log("Clinking enter button");
		getElement(locatorKey).sendKeys(Keys.ENTER);
	}
	
	public void selectByVisibleText(String locatorKey, String data) {
		Select s = new Select(getElement(locatorKey));
		s.selectByVisibleText(data);
	}
	
	public String getText(String locatorKey) {
		return getElement(locatorKey).getText();
	}
	
	// central functions to extract elements
	public WebElement getElement(String locatorKey) {
		//  check the presence
		if(!isElementPresent(locatorKey)) {
			// report failure
			log("Element not present - "+locatorKey);
		}
		//  check the visibility
		if(!isElementVisible(locatorKey)) {
			// report failure
			log("Element not visible - "+locatorKey);
		}
			
		WebElement e = driver.findElement(getLocator(locatorKey));
		
		/* this function is replaced by -> public By getLocator(String locatorKey)
		if(locatorKey.endsWith("_id")) {
			e = driver.findElement(By.id(prop.getProperty(locatorKey)));
		} else if(locatorKey.endsWith("_xpath")) {
			e = driver.findElement(By.xpath(prop.getProperty(locatorKey)));
		} else if(locatorKey.endsWith("_css")) {
			e = driver.findElement(By.cssSelector(prop.getProperty(locatorKey)));
		} else if(locatorKey.endsWith("_name")) {
			e = driver.findElement(By.name(prop.getProperty(locatorKey)));
		}
		*/
		return e;
	}
	
	// true - present
	// false - not present
	public boolean isElementPresent(String locatorKey) {
		log("Checking presence of - " +locatorKey);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		try {
			
			wait.until(ExpectedConditions.presenceOfElementLocated(getLocator(locatorKey)));
			
			/* this function is replaced by -> public By getLocator(String locatorKey)
			if(locatorKey.endsWith("_id")) {
				wait.until(ExpectedConditions.presenceOfElementLocated(By.id(prop.getProperty(locatorKey))));
			} else if(locatorKey.endsWith("_xpath")) {
				wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(prop.getProperty(locatorKey))));
			} else if(locatorKey.endsWith("_css")) {
				wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(prop.getProperty(locatorKey))));
			} else if(locatorKey.endsWith("_name")) {
				wait.until(ExpectedConditions.presenceOfElementLocated(By.name(prop.getProperty(locatorKey))));
			}
			*/
			
		} catch (Exception e) {
			return false;
		}
		
		return true;
	}
	
	// true - visible
	// false - not visible
	public boolean isElementVisible(String locatorKey) {
		log("Checking visibility of - " +locatorKey);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		try {
			
			wait.until(ExpectedConditions.visibilityOfElementLocated(getLocator(locatorKey)));
			
			/* this function is replaced by -> public By getLocator(String locatorKey)
			if(locatorKey.endsWith("_id")) {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(prop.getProperty(locatorKey))));
			} else if(locatorKey.endsWith("_xpath")) {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(prop.getProperty(locatorKey))));
			} else if(locatorKey.endsWith("_css")) {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(prop.getProperty(locatorKey))));
			} else if(locatorKey.endsWith("_name")) {
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(prop.getProperty(locatorKey))));
			}
			*/
		} catch (Exception e) {
			return false;
		}
		
		return true;
	}
	
	public By getLocator(String locatorKey) {
		By by = null;
		
		if(locatorKey.endsWith("_id")) {
			by = By.id(prop.getProperty(locatorKey));
		} else if(locatorKey.endsWith("_xpath")) {
			by = By.xpath(prop.getProperty(locatorKey));
		} else if(locatorKey.endsWith("_css")) {
			by = By.cssSelector(prop.getProperty(locatorKey));
		} else if(locatorKey.endsWith("_name")) {
			by = By.name(prop.getProperty(locatorKey));
		}
		
		return by;
	}
	
	
	//reporting function
	public void log(String msg) {
		System.out.println(msg);
		test.log(Status.INFO, msg);
	}
	
	public void reportFailure(String failureMsg, boolean stopOnFailure) {
		System.out.println("FAIL -> " +failureMsg);
		test.log(Status.FAIL, failureMsg); //failure in Extent reports
		takeScreenShot(); //take screenshot everytime encountered a failure
		softAssert.fail("FAIL -> " +failureMsg); //write failure in TestNG reports
		
		if(stopOnFailure) {
			Reporter.getCurrentTestResult().getTestContext().setAttribute("criticalFailure", "Y");
			assertAll();//report all the failures - skip all next methods
		}
		
	}
	
	public void assertAll() {
		softAssert.assertAll();
	}
	
	public void takeScreenShot(){
		//1 - fileName of the screenshot - base on time stamp
		Date d=new Date();
		String screenshotFile=d.toString().replace(":", "_").replace(" ", "_")+".png";
		
		//2 - take screenshot -> put it on file object (default)
		File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		
		try {
			//3 - copy file (from object) - get the dynamic folder name
			FileUtils.copyFile(srcFile, new File(ExtentManager.screenshotFolderPath+"//"+screenshotFile));
			//4 - put/embed screenshot file in reports
			test.log(Status.INFO,"Screenshot-> "+ test.addScreenCaptureFromPath(ExtentManager.screenshotFolderPath+"//"+screenshotFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	public void waitForPageToLoad(){
		JavascriptExecutor js = (JavascriptExecutor)driver;
		int i=0;
		// ajax status
		while(i!=10){
		String state = (String)js.executeScript("return document.readyState;");
		System.out.println(state);

		if(state.equals("complete"))
			break;
		else
			wait(2);

		i++;
		}
		// check for jquery status
		i=0;
		while(i!=10){
	
			Long d= (Long) js.executeScript("return jQuery.active;");
			System.out.println(d);
			if(d.longValue() == 0 )
			 	break;
			else
				 wait(2);
			 i++;
				
			}
		
		}
	
	public void wait(int time) {
		try {
			Thread.sleep(time*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void acceptAlert(){
		test.log(Status.INFO, "Switching to alert");
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.alertIsPresent());
		try{
			driver.switchTo().alert().accept();
			driver.switchTo().defaultContent();
			test.log(Status.INFO, "Alert accepted successfully");
		}catch(Exception e){
				reportFailure("Alert not found when mandatory",true);
		}
		
	}

	// finds the row number of the data
	public int getRowNumWithCellData(String tableLocator, String data) {
		
		WebElement table = getElement(tableLocator);
		List<WebElement> rows = table.findElements(By.tagName("tr"));
		for(int rNum=0;rNum<rows.size();rNum++) {
			WebElement row = rows.get(rNum);
			List<WebElement> cells = row.findElements(By.tagName("td"));
			for(int cNum=0;cNum<cells.size();cNum++) {
				WebElement cell = cells.get(cNum);
				System.out.println("Text "+ cell.getText());
				if(!cell.getText().trim().equals(""))
					if(data.startsWith(cell.getText()))
						return(rNum+1);
			}
		}
		
		return -1; // data is not found
	}
	

	public void quit() {
		driver.quit();
		
	}
	
	
	

}
