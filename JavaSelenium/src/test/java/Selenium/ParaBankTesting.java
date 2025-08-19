package Selenium;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.apache.logging.log4j.Logger;
import java.time.Duration;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

public class ParaBankTesting {
	WebDriver driver;
	Logger log = LogManager.getLogger(ParaBankTesting.class);
	
	 // ================== SETUP ==================
	
	@Parameters({"browserName", "url"})
    @BeforeClass(alwaysRun = true)
    public void LaunchBrowser(String browserName, String url) {
        switch (browserName.toLowerCase()) {
            case "chrome":
                driver = new ChromeDriver();
                break;
            case "firefox":
                driver = new FirefoxDriver();
                break;
            case "edge":
                driver = new EdgeDriver();
                break;
            default:
                throw new IllegalArgumentException("Invalid browser: " + browserName);
        }
        driver.manage().window().maximize();
        log.info("(****Loggers*****)Navigating to URL: {}", url);
        driver.get(url);
    }
	
	   // ================== TEST CASES ==================
	
	/*@Test(groups= {"regression"},
			priority = 1,
			dataProvider = "registerData",dataProviderClass = DataProviders.class,
			invocationCount = 3,
	        threadPoolSize = 2)*/
	@Test(groups= {"regression"},
	priority = 1,
	dataProvider = "registerData",dataProviderClass = DataProviders.class
	)
	public void RegisterNewUser(String firstname,String secondname,String street,String city,String state,
			String zip,String number,String ssn,String username,String password,java.lang.reflect.Method method,
            org.testng.ITestContext context) throws InterruptedException {
		//for showing the invoccation-----------------
		/*int currentInvocation = context.getAllTestMethods()[0].getCurrentInvocationCount();
        System.out.println("-> " + method.getName() + " | Invocation #" + currentInvocation +
                           " | Thread ID: " + Thread.currentThread().getId());*/
        //-----------------------------------------
		log.info("(****Loggers*****)Starting registration test for user: {}", username);
		
		driver.get("https://parabank.parasoft.com/");
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='register.htm']"))).click();
		
//		driver.findElement(By.id("FirstName")).sendKeys("Prabal");//approch no.1, it given instance result
		wait.until(ExpectedConditions.elementToBeClickable(By.name("customer.firstName"))).sendKeys(firstname);
		wait.until(ExpectedConditions.elementToBeClickable(By.name("customer.lastName"))).sendKeys(secondname);
		//approch no.2, and it is a better approch bcz it is giving stability//That’s called explicit wait
		wait.until(ExpectedConditions.elementToBeClickable(By.name("customer.address.street"))).sendKeys(street);
		wait.until(ExpectedConditions.elementToBeClickable(By.name("customer.address.city"))).sendKeys(city);
		wait.until(ExpectedConditions.elementToBeClickable(By.name("customer.address.state"))).sendKeys(state);
		wait.until(ExpectedConditions.elementToBeClickable(By.name("customer.address.zipCode"))).sendKeys(zip);
		wait.until(ExpectedConditions.elementToBeClickable(By.name("customer.phoneNumber"))).sendKeys(number);
		wait.until(ExpectedConditions.elementToBeClickable(By.name("customer.ssn"))).sendKeys(ssn);
		wait.until(ExpectedConditions.elementToBeClickable(By.name("customer.username"))).sendKeys(username);
		wait.until(ExpectedConditions.elementToBeClickable(By.name("customer.password"))).sendKeys(password);
		wait.until(ExpectedConditions.elementToBeClickable(By.name("repeatedPassword"))).sendKeys(password);
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@value='Register']"))).click();
		
	    boolean isError = driver.findElements(By.xpath("//*[contains(text(),'This username already exists')]")).size() > 0;

	    if (isError) {
	        System.out.println("Username already exists: " + username + " Skipping logout");
	        Assert.assertTrue(isError, "Registration failed due to existing username");
	    } else {
	        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Log Out']"))).click();
	        Thread.sleep(1000);
	    }
		
	}
	//=========================================LogIn=====================================
	
	/*@Test(groups = {"smoke"}, 
			priority = 2, 
			dataProvider = "logInTestData",dataProviderClass = DataProviders.class,
			invocationCount = 3,
	        threadPoolSize = 2)*/
	@Test(groups = {"smoke"}, 
	priority = 2, 
	dataProvider = "logInTestData",dataProviderClass = DataProviders.class
	)
    public void Login(String username, String password,java.lang.reflect.Method method,
            org.testng.ITestContext context) throws InterruptedException {
		//for showing the invoccation-----------------
		/*int currentInvocation = context.getAllTestMethods()[1].getCurrentInvocationCount();
        System.out.println("-> " + method.getName() + " | Invocation #" + currentInvocation +
                           " | Thread ID: " + Thread.currentThread().getId() +
                           " | Running with user: " + username);*/
        //----------------------------------------
		log.info("(****Loggers*****)Starting login test with user: {}", username);
		
        driver.get("https://parabank.parasoft.com/");
        System.out.println("Performing Login with: " + username + " / " + password);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(By.name("username"))).sendKeys(username);
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys(password);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@value='Log In']"))).click();

        Thread.sleep(2000);
        
     // Assertion: check login success
        boolean isLogoutPresent = driver.findElements(By.xpath("//a[text()='Log Out']")).size() > 0;
        Assert.assertTrue(isLogoutPresent, "Login failed for user: " + username);
        
        if (isLogoutPresent) {
        	log.info("Login successful. Logging out user: {}", username);
            driver.findElement(By.xpath("//a[text()='Log Out']")).click();
            Thread.sleep(1000);
        } else {
			log.error("(****Loggers*****)Login failed for user: {}", username);
		}
    }
	
	@Ignore
	@Test (groups = {"regression"},priority = 3)
	public void CustomerCare() {
		driver.get("https://parabank.parasoft.com/parabank/contact.htm");
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='contact.htm']"))).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.name("name"))).sendKeys("Prabal");
		wait.until(ExpectedConditions.elementToBeClickable(By.name("email"))).sendKeys("raja123gmail.com");
		wait.until(ExpectedConditions.elementToBeClickable(By.name("phone"))).sendKeys("1234567890");
		wait.until(ExpectedConditions.elementToBeClickable(By.name("message"))).sendKeys("You need a good tester for your bank");
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@value='Send to Customer Care']"))).click();
	}
	
	   // ================== CLEANUP ==================
	
	@AfterClass(alwaysRun = true) 
	public void afterClass() throws InterruptedException {
		System.out.println("Closing the browser after done");
		if (driver != null) {
			Thread.sleep(10000); // pause for 2 seconds
			driver.quit();
			log.info("(****Loggers*****)Browser closed.");
		}
	}
	
//	// ================== DATA PROVIDER ==================
//	@DataProvider(name="logInTestData")
//	public Object[][] logInData() throws IOException {
//		return Excelutils.getTestData("src/test/resources/RegisterData.xlsx", "LoginSheet");
//	}
//	@DataProvider(name = "registerData")
//	public Object[][] registerdata() throws IOException {
//	    return Excelutils.getTestData("src/test/resources/RegisterData.xlsx", "RegisterSheet");
//	}

}
	

