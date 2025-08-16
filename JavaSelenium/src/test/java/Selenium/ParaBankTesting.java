package Selenium;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

public class ParaBankTesting {
	WebDriver driver;
	
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
        driver.get(url);
    }
	
	   // ================== TEST CASES ==================
	
	@Test(groups= {"regression"},priority = 1)
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
	
	@Test(groups = {"smoke"}, priority = 2, dataProvider = "logInTestData")
    public void Login(String username, String password) throws InterruptedException {
        driver.get("https://parabank.parasoft.com/");
        System.out.println("Performing Login with: " + username + " / " + password);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(By.name("username"))).sendKeys(username);
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys(password);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@value='Log In']"))).click();

        Thread.sleep(2000);
        
        try {
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Log Out']"))).click();
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println("Logout link not found, maybe login failed.");
        }
    }
	
	@Ignore 
	@Test (groups = {"regression"})
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
		}
	}
	
	// ================== DATA PROVIDER ==================
	@DataProvider(name="logInTestData")
	public Object[][] logInData() {
		Object[][] data = new Object[2][2];
		data[0][0] = "Prabal07";
		data[0][1] = "Admin123";
		data[1][0] = "Abhas1";
		data[1][1] = "Admin123";
		return data;
	}
	
	
}
	

