package Selenium;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class DemoApplication {

		WebDriver driver;
		
		@BeforeClass
		public void LunchBrowser() {
			driver = new ChromeDriver();
			driver.manage().window().maximize();
		}
		
		@Test
		public void RegisterNewUser() {
			driver.get("https://demowebshop.tricentis.com/");
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/register']"))).click();
			
			System.out.println(driver.getTitle());
			
			
			driver.findElement(By.id("FirstName")).sendKeys("Prabal");//approch no.1, it given instance result
			wait.until(ExpectedConditions.elementToBeClickable(By.id("LastName"))).sendKeys("Ghosh");
			//approch no.2, and it is a better approch bcz it is giving stability
			wait.until(ExpectedConditions.elementToBeClickable(By.id("Email"))).sendKeys("prabal123@gmail.com");
			wait.until(ExpectedConditions.elementToBeClickable(By.id("Password"))).sendKeys("harry@12");
			wait.until(ExpectedConditions.elementToBeClickable(By.id("ConfirmPassword"))).sendKeys("harry@12");
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='register-button']"))).click();
			
			
			
		}
		@Test
		public void launchGoogle() {
			driver.get("https://www.google.com/");
		}
		
//		@AfterClass
//		public void tearDown() {
//			if(driver != null) {
//				driver.quit();
//			}
//		}

}
