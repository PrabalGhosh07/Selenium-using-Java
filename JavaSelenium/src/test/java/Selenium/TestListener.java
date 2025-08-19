package Selenium;

import java.io.File;
import java.io.IOException;
import java.nio.file.StandardCopyOption;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.nio.file.Files;

//This class implements ITestListener interface 
//which allows us to "listen" to test events during execution
public class TestListener implements ITestListener{
	
	private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
	private WebDriver driver;
	
	//onStart(ITestContext context) runs before any test methods in the suite start.
	@Override
	public void onStart(ITestContext context) {
		ExtentSparkReporter spark = new ExtentSparkReporter("reports/ExtentReport.html");
		extent=new ExtentReports();
		extent.attachReporter(spark);
		System.out.println("===== Test Suite Started =====");//gets the name of the test method
		/*ExtentSparkReporter → a reporter class from ExtentReports that creates HTML reports.
		
		"reports/ExtentReport.html" → the file where the report will be generated.
		
		A folder called reports/ will be created automatically.
		
		Inside it, you’ll get ExtentReport.html.*/
	}
	@Override
	public void onTestStart(ITestResult result) {
        // Create a new entry in Extent Report for each test
        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName());
        test.set(extentTest);
        System.out.println(">> Test Started: " + result.getMethod().getMethodName());
	}
	
	 // Called when a test method has successfully finished
	@Override
	public void onTestSuccess(ITestResult result) {
		test.get().pass("Test Passed");
        System.out.println(" Test Passed: " + result.getMethod().getMethodName());
    }
	
	//********Screenshot is perform whenever the test fails***********
	@Override
	public void onTestFailure(ITestResult result) {
        // Called when a test method fails
        System.out.println(" Test Failed: " + result.getMethod().getMethodName());
        
        Object testClass=result.getInstance();
        try {
			driver = (WebDriver) testClass.getClass().getDeclaredField("driver").get(testClass);
			/*Go into the test class object, find the field called driver, 
			and give me its current value (the WebDriver instance).
			That way, your listener can reuse the same driver to take a screenshot 
			when a test fails.*/
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        if(driver !=null) {
        	File src=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        	/*TakesScreenshot → Selenium interface that lets WebDriver capture screenshots.

			(TakesScreenshot) driver → We “cast” the WebDriver into TakesScreenshot because not all
			drivers implement it directly, but ChromeDriver, FirefoxDriver, etc. do.

			getScreenshotAs(OutputType.FILE) → Captures the current browser screen and returns it as a 
			temporary file (File).*/
        	String timestamp = String.valueOf(System.currentTimeMillis());
        	File dest= new File("screenshot/"+result.getMethod().getMethodName()+"_"+timestamp+".png");
        	/*Creates a destination 
        	path where the screenshot will be saved permanently.*/
        	/*"screenshots/" → means store inside a folder named screenshots.

			result.getMethod().getMethodName() → gets the test method name that failed, e.g.,
			Login, RegisterNewUser.

			".png" → adds .png extension for the screenshot.*/
        	
        	dest.getParentFile().mkdir();//creat folder if not exist
        	try {
        		Files.copy(src.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
        		// Attach screenshot to Extent Report
        		 test.get().fail("Test Failed: " + result.getMethod().getMethodName());
                 test.get().fail(result.getThrowable()); // log exception details
                 test.get().addScreenCaptureFromPath(dest.getAbsolutePath());

                 System.out.println("Screenshot saved: " + dest.getAbsolutePath());
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
    }
	
	@Override
	public void onTestSkipped(ITestResult result) {
        // Called when a test method is skipped (e.g., due to dependency failure)
		
		test.get().skip("Test Skipped: " + result.getThrowable());
        System.out.println(" Test Skipped: " + result.getMethod().getMethodName());
    }
	
	 
	 @Override
	 public void onFinish(ITestContext context) {
        // Called after all test methods in the suite/class have finished executing
		extent.flush();
        System.out.println("===== Test Suite Finished =====");
	 }
	

}
