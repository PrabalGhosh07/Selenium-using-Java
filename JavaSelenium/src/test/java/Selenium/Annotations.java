package Selenium;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.AssertJUnit;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.*;
import org.testng.annotations.BeforeMethod;

public class Annotations {
	WebDriver driver;
	
	@BeforeSuite //Start of the suite
	public void BeforeSuit() {
		System.out.println("Start the Suit");
	}
	/* test suite-> is a collection of related test cases designed to verify
	the functionality and performance of a software application or system. It 
	acts as a container for these test cases, providing a structured way to manage,
	execute, and report on the testing process. */
	
	@BeforeTest //Preparing test environment
	public void BeforTest() {
		System.out.println("Prepairing the test environment");
	}
	
	@BeforeClass// Launch Chrome, open OrangeHRM
	public void BeforeClass() {
		System.out.println("Lunching the browser");
		
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://opensource-demo.orangehrmlive.com/");
	}
	
	@BeforeMethod //Go to login page
	public void BeforeMethod() {
		System.out.println("Navigating to orangHRM login page ");
		driver.get("https://opensource-demo.orangehrmlive.com/");
	}
	
	@Test(priority = 1)//Verify page
	public void VerifyPage() {
		System.out.println("Page is Dispalyed");
	}
	
	@Test(priority = 2) //Perform login
	public void LoginTest() {
		System.out.println("performing Login");
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		//driver.findElement(By.xpath("//input[@name='username']")).sendKeys("Admin");
		wait.until(ExpectedConditions.elementToBeClickable(By.name("username"))).sendKeys("Admin");
		driver.findElement(By.xpath("//input[@name='password']")).sendKeys("admin123");
		//driver.findElement(By.xpath("//button[text()=' Login ']"));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()=' Login ']"))).click();
		String ExpectedTitle = "OrangeHRM";
		String Actualtitle=driver.getTitle();
		AssertJUnit.assertEquals(Actualtitle,ExpectedTitle,"Titale is mismatched");
		AssertJUnit.assertTrue(true);
		System.out.println(driver.getTitle());
	}
	
	@AfterMethod //Reset to login page
	public void AfterMethod() {
		System.out.println("Logout");
		driver.get("https://opensource-demo.orangehrmlive.com/");
	}
	
//	@AfterClass // Close browser
//	public void AfterClass() {
//		System.out.println("Closing the browser");
//		if(driver != null) {
//			driver.quit();
//		}
//	}
	
	@AfterTest //After test method
	public void AfterTest() {
		System.out.println("After test method should work");
	}
	
	@AfterSuite //After suite
	public void AfterSuit() {
		 System.out.println("After suite method should work");
	}
}
