package com.comcast.crm.leadtest_using_genericutility;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.invoke.MethodHandles.Lookup.ClassOption;
import java.time.Duration;
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

public class CreateLeadEditIndustry {

	public static void main(String[] args) throws IOException, InterruptedException {
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
		String lastname = eu.getDataFromExcel("lead", 5, 2)+jLib.getRandomNumber();
		String company = eu.getDataFromExcel("lead", 5, 3)+jLib.getRandomNumber();
	
		String industry=eu.getDataFromExcel("lead", 5, 4);
		String editIndustry=eu.getDataFromExcel("lead", 6, 4);
		String annualIncome=eu.getDataFromExcel("lead", 5, 5);

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

		// 3)Navigate to leads
		driver.findElement(By.linkText("Leads")).click();

		// 4)Navigate to create new lead page by click on + image
		driver.findElement(By.xpath("//img[@title='Create Lead...']")).click();

		/*
		 * 5)create a lead with mandatory name field add industry and edit industry
		 */

		driver.findElement(By.name("lastname")).sendKeys(lastname);
		driver.findElement(By.name("company")).sendKeys(company);
		
		wLib.select(driver.findElement(By.name("industry")), industry);
		
		
		
		driver.findElement(By.name("annualrevenue")).clear();
		driver.findElement(By.name("annualrevenue")).sendKeys(annualIncome);
	

		driver.findElement(By.xpath("//input[@title='Save [Alt+S]']")).click();
		
	
		wLib.mouseMoveOnElement(driver, driver.findElement(By.id("mouseArea_Industry")));
		driver.findElement(By.xpath("//a[text()='Edit']")).click();
		
		
		wLib.select(driver.findElement(By.id("txtbox_Industry")),editIndustry);
		driver.findElement(By.name("button_Industry")).click();
		
		//verification
		String editedIndustry = driver.findElement(By.id("dtlview_Industry")).getText();
		//System.out.println(editedIndustry);
		if(editedIndustry.equals(editIndustry)) {
			System.out.println(editedIndustry+ " is verified after edit == PASS");
		}
		else {
			System.out.println(editedIndustry+ " is not verified after edit == FAIL");
		}
		
	
		// 6)logout
	     wLib.mouseMoveOnElement(driver, driver.findElement(By.xpath("//img[@src='themes/softed/images/user.PNG']")));
		 driver.findElement(By.xpath("//a[text()='Sign Out']")).click();
		 
		driver.quit();

	}

}
