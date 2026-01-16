package com.comcast.crm.contacttest_using_genericutility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.comcast.crm.generic.fileutility.ExcelUtility;
import com.comcast.crm.generic.fileutility.FileUtility;
import com.comcast.crm.generic.webdriverutility.JavaUtility;
import com.comcast.crm.generic.webdriverutility.WebDriverUtility;

public class CreateContactWithSupportDate {

	public static void main(String[] args) throws Exception {
		/*create Object*/
		FileUtility fil=new FileUtility();
		ExcelUtility eu=new ExcelUtility();
		JavaUtility jLib=new JavaUtility();
		WebDriverUtility wLib=new WebDriverUtility();
		
		//read common data from property file
		String BROWSER=fil.getDataFromPropertiesFile("browser");
		String URL=fil.getDataFromPropertiesFile("url");
		String UN=fil.getDataFromPropertiesFile("username");
		String PW=fil.getDataFromPropertiesFile("password");
				
		//read testscript data drom excel;
		String lastname =  eu.getDataFromExcel("contact",4, 2)+jLib.getRandomNumber();
				
		//to achieve run time polymorphism (cross browser testing)
		WebDriver driver=null;
		if(BROWSER.equals("chrome")) {	
			driver=new ChromeDriver();
		}
		else if(BROWSER.equals("firefox")) {
			driver=new FirefoxDriver();
		}
		else if(BROWSER.equals("edge")) {
			driver=new EdgeDriver();
		}
		else {
			driver=new ChromeDriver();
		}
				
		wLib.maximizePage(driver);
	    wLib.waitForPageToLoad(driver);	
				
		//TC 1 Smoke test Case Test Basic or critical feature
		//1) Navigate to application
		driver.get(URL);
				
		//2)Login to app
		driver.findElement(By.name("user_name")).sendKeys(UN);
		driver.findElement(By.name("user_password")).sendKeys(PW);
		driver.findElement(By.id("submitButton")).click();
				
		//3)place mouse cursor and click contact link
		driver.findElement(By.linkText("Contacts")).click();
				
		//4)Navigate to create new contact page by click on + image
		driver.findElement(By.xpath("//img[@alt='Create Contact...']")).click();
				
		//5)create a contact with mandatory name field and save
		driver.findElement(By.name("lastname")).sendKeys(lastname);
		
		//capture current date
		String startdate=jLib.getSystemDateYYYYMMDD();
		String enddate = jLib.getRequiredDateYYYYMMDD(30);
		//System.out.println(dateRequires);
		driver.findElement(By.name("support_start_date")).clear();
		driver.findElement(By.name("support_start_date")).sendKeys(startdate);
		driver.findElement(By.name("support_end_date")).clear();
		driver.findElement(By.name("support_end_date")).sendKeys(enddate);
		
		
		driver.findElement(By.xpath("//input[@title='Save [Alt+S]']")).click();
				
		//verification
		String actStartDate = driver.findElement(By.id("dtlview_Support Start Date")).getText();
		if(actStartDate.equals(startdate)) {
			System.out.println(startdate+" information is created == PASS");
		}
		else {
			System.out.println(startdate+" information is not created == FAIL");
		}
		String actEndDate = driver.findElement(By.id("dtlview_Support End Date")).getText();
		if(actEndDate.equals(enddate)){
			System.out.println(enddate+" information is created == PASS");
		}
		else {
			System.out.println(enddate+" information is not created == FAIL");
		}
		
		

				
				
				
		//6)logout
		wLib.mouseMoveOnElement(driver,driver.findElement(By.xpath("//img[@src='themes/softed/images/user.PNG']")));
		driver.findElement(By.xpath("//a[text()='Sign Out']")).click();
		driver.quit();
					

	}

}
