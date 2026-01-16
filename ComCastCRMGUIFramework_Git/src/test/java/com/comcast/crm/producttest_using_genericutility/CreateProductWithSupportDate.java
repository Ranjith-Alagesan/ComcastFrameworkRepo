package com.comcast.crm.producttest_using_genericutility;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.invoke.MethodHandles.Lookup.ClassOption;
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
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import com.comcast.crm.generic.fileutility.ExcelUtility;
import com.comcast.crm.generic.fileutility.FileUtility;
import com.comcast.crm.generic.webdriverutility.JavaUtility;
import com.comcast.crm.generic.webdriverutility.WebDriverUtility;

public class CreateProductWithSupportDate {

	public static void main(String[] args) throws IOException {
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

		// read testscript data drom excel
		String productname= eu.getDataFromExcel("product", 1, 2)+jLib.getRandomNumber();
		WebDriver driver = null;
		if (BROWSER.equals("chrome")) {
			driver = new ChromeDriver();
		} else if (BROWSER.equals("firefox")) {
			driver = new FirefoxDriver();
		} else if (BROWSER.equals("edge")) {
			driver = new EdgeDriver();
		} else {
			driver = new ChromeDriver();
		}
		wLib.maximizePage(driver);
	    wLib.waitForPageToLoad(driver);

		// 1) Navigate to application
		driver.get(URL);

		// 2)Login to app
		driver.findElement(By.name("user_name")).sendKeys(UN);
		driver.findElement(By.name("user_password")).sendKeys(PW);
		driver.findElement(By.id("submitButton")).click();

		// 3)Navigate to product
		driver.findElement(By.linkText("Products")).click();

		// 4)Navigate to create new product page by click on + image
		driver.findElement(By.xpath("//img[@title='Create Product...']")).click();

		/*
		 * 5)create a product with mandatory name field with support date
		 */
		driver.findElement(By.name("productname")).sendKeys(productname);
	
		if (driver.findElement(By.xpath("//input[@name='discontinued']")).isSelected()) {
			System.out.println("product active is verified");
		} else {
			System.out.println("product active is verified");
		}
		
		//support start date
		String startDate = jLib.getSystemDateYYYYMMDD();
		
		//support end date
		String endDate = jLib.getRequiredDateYYYYMMDD(730);
		
		driver.findElement(By.name("start_date")).sendKeys(startDate);
		driver.findElement(By.name("expiry_date")).sendKeys(endDate);
		
		driver.findElement(By.xpath("//input[@title='Save [Alt+S]']")).click();

		
		
		 //verify Header msg Expected result 
		 String headerInfo = driver.findElement(By.xpath("//span[@class='lvtHeaderText']")).getText();
		 //dynamic data is there so use contains() to get partial text
		 if(headerInfo.contains(productname)){
			 System.out.println(productname+" is created == PASS"); 
		 }
		 else{
			 System.out.println(productname+" is not created == FAIL"); 
		 } 
		 
		 //verify Header last Expected result
		 String actualLastName = driver.findElement(By.id("dtlview_Product Name")).getText(); 
		 if(actualLastName.equals(productname)){
			 System.out.println(productname+" information is created == PASS");
		 }
		else {
			System.out.println(productname+" information is not created == FAIL");
		 }
		 
		String actStartdate = driver.findElement(By.id("dtlview_Support Start Date")).getText();
		if(actStartdate.equals(startDate)) {
			System.out.println(actStartdate+" information is created == PASS");
		}
		else {
			System.out.println(actStartdate+" information is created == FAIL");
		}
		String actEnddate = driver.findElement(By.id("dtlview_Support Expiry Date")).getText();
		if(actEnddate.equals(endDate)) {
			System.out.println(actEnddate+" information is created == PASS");
		}
		else {
			System.out.println(actEnddate+" information is created == FAIL");
		}

		// 6)logout
		wLib.mouseMoveOnElement(driver,driver.findElement(By.xpath("//img[@src='themes/softed/images/user.PNG']")));
		driver.findElement(By.xpath("//a[text()='Sign Out']")).click();
		 
		driver.quit();

	}

}
