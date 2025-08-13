package Selenium;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ParaBankTesting {
	WebDriver driver;
	@BeforeClass
	public void LunchBrowser() {
		driver = new ChromeDriver();
		driver.manage().window().maximize();
	}
	@Test
	public void RegisterNewUser() {
		driver.get("https://parabank.parasoft.com/");
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='register.htm']"))).click();
		
		System.out.println(driver.getTitle());
		
		
//		driver.findElement(By.id("FirstName")).sendKeys("Prabal");//approch no.1, it given instance result
		wait.until(ExpectedConditions.elementToBeClickable(By.name("customer.firstName"))).sendKeys("Prabal");
		wait.until(ExpectedConditions.elementToBeClickable(By.name("customer.lastName"))).sendKeys("Ghosh");
		//approch no.2, and it is a better approch bcz it is giving stability
		wait.until(ExpectedConditions.elementToBeClickable(By.name("customer.address.street"))).sendKeys("Gurap");
		wait.until(ExpectedConditions.elementToBeClickable(By.name("customer.address.city"))).sendKeys("Hooghly");
		wait.until(ExpectedConditions.elementToBeClickable(By.name("customer.address.state"))).sendKeys("West Bengal");
		wait.until(ExpectedConditions.elementToBeClickable(By.name("customer.address.zipCode"))).sendKeys("712303");
		wait.until(ExpectedConditions.elementToBeClickable(By.name("customer.phoneNumber"))).sendKeys("1234567890");
		wait.until(ExpectedConditions.elementToBeClickable(By.name("customer.ssn"))).sendKeys("123456");
		wait.until(ExpectedConditions.elementToBeClickable(By.name("customer.username"))).sendKeys("Prabal07");
		wait.until(ExpectedConditions.elementToBeClickable(By.name("customer.password"))).sendKeys("Admin123");
		wait.until(ExpectedConditions.elementToBeClickable(By.name("repeatedPassword"))).sendKeys("Admin123");
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@value='Register']"))).click();
		
		
		
	}
	
	@AfterClass
	public void Login(){
	
		driver.get("https://parabank.parasoft.com/");
		System.out.println("performing Login");
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		//driver.findElement(By.xpath("//input[@name='username']")).sendKeys("Admin");
		wait.until(ExpectedConditions.elementToBeClickable(By.name("username"))).sendKeys("Prabal07");
		driver.findElement(By.xpath("//input[@name='password']")).sendKeys("Admin123");
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@value='Log In']"))).click();
	}
		
//	@AfterMethod //Reset to login page
//	public void AfterMethod() {
//		System.out.println("Logout");
//		driver.get("https://parabank.parasoft.com/");
//	}
	
}
	

