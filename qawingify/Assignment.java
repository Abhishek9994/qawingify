package qawingify;

import java.io.ObjectInputFilter.Status;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Assignment {

        WebDriver driver;
        ExtentTest test ;
   	    ExtentReports report;
        
        @BeforeClass
    	public void configreport() {
    		  report = new ExtentReports(System.getProperty("user.dir")+"/Assignment.html");
    	      test = report.startTest("Wingify Assignment");
    	    		
    	}
	
	    @BeforeMethod
	     public void Setup() {
		
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();  
	    test.log(LogStatus.PASS, "Chorme driver has opned successfully");
	   
		driver.get("https://sakshingp.github.io/assignment/login.html#");
		driver.manage().window().maximize();
		test.log(LogStatus.PASS, "Entered URL is vaild");
		
		 SoftAssert sa = new SoftAssert();
		 String actual_url = "https://sakshingp.github.io/assignment/login.html#";
	     String expected_url = driver.getCurrentUrl();
	     sa.assertEquals(actual_url, expected_url);
	     
	     String actual_title = "Login Form";
	     String expected_title = driver.getTitle();
	     sa.assertEquals(actual_title, expected_title);
	     test.log(LogStatus.PASS, "Title Verification Successful");
		
	     
	}
	
	@Test
	public void LoginPage() throws InterruptedException {
		
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));
		
		
		//Enter Username
		driver.findElement(By.cssSelector("input#username")).sendKeys("Abhishek Patil");
		String actualText = driver.findElement(By.cssSelector("input#username")).getAttribute("value");
        String expectedText = "Abhishek Patil";
        Assert.assertEquals(actualText, expectedText, "Username field does not contain expected text");
        test.log(LogStatus.PASS, "Username Entered Successfully");
        
        
		//Enter Password
		 String password = "Abhi@1997";
	     driver.findElement(By.xpath("//input[@placeholder='Enter your password']")).sendKeys(password);
         String actualPassword = driver.findElement(By.xpath("//input[@placeholder='Enter your password']")).getAttribute("value");
	     Assert.assertEquals(actualPassword, password, "Password field does not contain expected text");
	     test.log(LogStatus.PASS, "Password Entered Successfully");
	       
		//Click on Remember Me
		driver.findElement(By.cssSelector("input.form-check-input")).click();
		// Assert that the "Remember Me" checkbox is checked
        boolean isChecked = driver.findElement(By.cssSelector("input.form-check-input")).isSelected();
        Assert.assertTrue(isChecked, "Remember Me checkbox is not checked");
        test.log(LogStatus.PASS, "Successfully Clicked on the Check box");
        
        
		//Click on Log In 
		driver.findElement(By.xpath("//button[text()='Log In']")).click();	
		test.log(LogStatus.PASS, "Successfully Clicked on the Login button");
		
		
		//Scroll Down till Amount Section 
		Thread.sleep(2000);
		 JavascriptExecutor js = (JavascriptExecutor) driver;
         js.executeScript("window.scrollBy(0, 600);");
         test.log(LogStatus.PASS, "Successfully Scroll down till Amount Section");
         
         //Click on the Amount 
         WebElement amountElement = driver.findElement(By.xpath("//th[@id='amount']"));
         assert amountElement != null : "Amount element not found";
         amountElement.click();
         test.log(LogStatus.PASS, "Successfully Clicked on the Amouont");
         
         
      // Get the values from the "AMOUNT" column
         List<Double> amounts = new ArrayList<>();
         List<WebElement> amountElements = driver.findElements(By.xpath("//td[@data-label='Amount']"));
         for (WebElement element : amountElements) {
             String amountText = element.getText().replaceAll("[^0-9.]+", "");
             // Check if amountText is not empty
             assert !amountText.isEmpty() : "Amount value is empty";
             amounts.add(Double.parseDouble(amountText));
         }

         // Verify if the values are sorted in ascending order
         List<Double> sortedAmounts = new ArrayList<>(amounts);
         Collections.sort(sortedAmounts);

         // Assert if amounts and sortedAmounts are equal
         Assert.assertEquals(amounts, sortedAmounts, "Values are not sorted in ascending order");

         // Log the result
         test.log(LogStatus.PASS, "Amounts: " + amounts);
         test.log(LogStatus.PASS, "Sorted Amounts: " + sortedAmounts);
         test.log(LogStatus.PASS, "Values are sorted in ascending order.");
     }
 
	 @AfterMethod
	  public void teardown() {
		  driver.close();
	  }
	  
	  @AfterClass
	  public void last() {
		 report.endTest(test);
		 report.flush();
	  }
         
         
	}
	
			
	

