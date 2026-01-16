package com.comcast.crm.orgtest_using_genericutility;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
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

public class CreateOrganizationWithPhoneNumber {

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
		
		//read testscript data drom excel
		
		String orgname = eu.getDataFromExcel("org",7,2)+jLib.getRandomNumber();
		String phone = eu.getDataFromExcel("org",7,3);

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
		
		//3)Navigate to org 
		driver.findElement(By.linkText("Organizations")).click();
		
		//4)Navigate to create new org page by click on + image
		driver.findElement(By.xpath("//img[@alt='Create Organization...']")).click();
		
		//5)create a org with mandatory name field and phone number
		driver.findElement(By.name("accountname")).sendKeys(orgname);
		driver.findElement(By.id("phone")).sendKeys(phone);
		
		driver.findElement(By.xpath("//input[@title='Save [Alt+S]']")).click();
		
		
		//verify phone number info Expected result
		String actualPhone = driver.findElement(By.id("dtlview_Phone")).getText();
		//not dyanamic complete data is there so use equals()
		//if not convert the phone num  to string in excel will get wrong value  9.87654321E9 becuase phone num is numeric data
		if(actualPhone.equals(phone)) {
			System.out.println(phone+" information is verified == PASS");
		}
		else {
			System.out.println(phone+" information is not verified == PASS");
		}
		
	
		
		//6)logout
		wLib.mouseMoveOnElement(driver,driver.findElement(By.xpath("//img[@src='themes/softed/images/user.PNG']")));
		driver.findElement(By.xpath("//a[text()='Sign Out']")).click();
		driver.quit();
		

	}

}
