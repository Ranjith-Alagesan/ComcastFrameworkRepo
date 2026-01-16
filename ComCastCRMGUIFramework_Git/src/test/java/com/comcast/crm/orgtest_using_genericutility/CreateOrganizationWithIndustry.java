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
import org.openqa.selenium.support.ui.Select;

import com.comcast.crm.generic.fileutility.ExcelUtility;
import com.comcast.crm.generic.fileutility.FileUtility;
import com.comcast.crm.generic.webdriverutility.JavaUtility;
import com.comcast.crm.generic.webdriverutility.WebDriverUtility;

public class CreateOrganizationWithIndustry {

	public static void main(String[] args) throws IOException {
		
		/*Create Object*/
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
		
		String orgname = eu.getDataFromExcel("org",4, 2)+jLib.getRandomNumber();
		String industry = eu.getDataFromExcel("org",4, 3);
		String type = eu.getDataFromExcel("org",4, 4);

		
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
		
		//5)create a org with mandatory name field
		driver.findElement(By.name("accountname")).sendKeys(orgname);
		
		//drop down
		
		wLib.select(driver.findElement(By.name("industry")), industry);
		wLib.select(driver.findElement(By.name("accounttype")), type);
		driver.findElement(By.xpath("//input[@title='Save [Alt+S]']")).click();
		
		//verify the industries and type info
		String actualIndustry=driver.findElement(By.id("dtlview_Industry")).getText();
		if(actualIndustry.equals(industry)) {
			System.out.println(industry+ " information is verified == PASS");
		}
		else {
			System.out.println(industry+ " information is not verified == FAIL");
			
		}
		
		
		String actualType=driver.findElement(By.id("dtlview_Type")).getText();
		if(actualType.equals(type)) {
			System.out.println(type+ " information is verified == PASS");
		}
		else {
			System.out.println(type+ " information is not verified == FAIL");
			
		}
	
		
		//6)logout
		wLib.mouseMoveOnElement(driver,driver.findElement(By.xpath("//img[@src='themes/softed/images/user.PNG']")));
		driver.findElement(By.xpath("//a[text()='Sign Out']")).click();
		
		driver.quit();
		
		

	}

}
