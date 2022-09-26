package reports;

import java.io.File;
import java.util.Date;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {

	static ExtentReports reports; //static means - to make it single instance/object for all the test cases
	public static String screenshotFolderPath;
	
	public static ExtentReports getReports() {//initialize and generate reports
		if(reports == null) {
			reports = new ExtentReports();
			
			//initialize reports folder
			Date d = new Date();
			System.out.println(d.toString().replaceAll(":", "-"));
			String reportsFolder = d.toString().replaceAll(":", "-") +"\\screenshots";
			
			screenshotFolderPath = System.getProperty("user.dir") +"\\reports\\" +reportsFolder;
			String reportFolderPath = System.getProperty("user.dir") +"\\reports\\" +d.toString().replaceAll(":", "-");
			
			System.out.println(screenshotFolderPath);
			File f = new File(screenshotFolderPath);
			f.mkdirs();//will create dynamic report folder name + screenshot folder
			
			//**************************
			
			//ExtentSparkReporter sparkReporter = new ExtentSparkReporter(System.getProperty("user.dir")+"\\reports");
			ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportFolderPath);
			
			sparkReporter.config().setReportName("Production Regression Testing");
			sparkReporter.config().setDocumentTitle("Automation Reports");
			sparkReporter.config().setTheme(Theme.STANDARD);
			sparkReporter.config().setEncoding("utf-8");
			
			reports.attachReporter(sparkReporter);
			
		}
		
		return reports;//keep adding to reports
	}
}
