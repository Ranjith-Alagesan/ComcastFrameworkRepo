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

public class CreateLead {

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
		String lastname = eu.getDataFromExcel("lead", 1, 2)+jLib.getRandomNumber();
		String company = eu.getDataFromExcel("lead", 1, 3)+jLib.getRandomNumber();
		String assign=eu.getDataFromExcel("lead", 1, 4);

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
		 * 5)create a lead with mandatory name field (last name, company, check assign
		 * to user radio button is selected and check the drop down option is selected
		 * with default content
		 */

		driver.findElement(By.name("lastname")).sendKeys(lastname);
		driver.findElement(By.name("company")).sendKeys(company);
		if (driver.findElement(By.xpath("//input[@type='radio']/preceding-sibling::input")).isSelected()) {
			System.out.println("User selected by default is verified Successfully");
		} else {
			System.out.println("User is not Selected");
		}
		
		WebElement opt= driver.findElement(By.name("assigned_user_id"));
		//opt.selectByVisibleText(assign);
		wLib.select(opt, assign);
		//System.out.println(opt.getFirstSelectedOption().getText());
		if(((opt.getText().trim().equals(assign)))){
			System.out.println("Administrator drop down Selected and verified Successfully");
		} else {
			System.out.println("Administrator drop down not Selected ");
		}

		driver.findElement(By.xpath("//input[@title='Save [Alt+S]']")).click();

		
		 //verify Header msg Expected result 
		 String headerInfo = driver.findElement(By.xpath("//span[@class='dvHeaderText']")).getText();
		 //dynamic data is there so use contains() to get partial text
		 if(headerInfo.contains(lastname)){
			 System.out.println(lastname+" is created == PASS"); 
		 }
		 else{
			 System.out.println(lastname+" is not created == FAIL"); 
		 } 
		 
		 //verify Header last Expected result
		 String actualLastName = driver.findElement(By.id("dtlview_Last Name")).getText(); 
		 if(actualLastName.equals(lastname)){
			 System.out.println(lastname+" information is created == PASS");
		 }
		else {
			System.out.println(lastname+" information is not created == FAIL");
		 }
		 
		 String actualCompany = driver.findElement(By.id("dtlview_Company")).getText(); 
		 if(actualCompany.equals(company)){
			 System.out.println(company+" information is created == PASS");
		 }
		else {
			System.out.println(company+" information is not created == FAIL");
		 }
		 
		 

		// 6)logout
		 
		 wLib.mouseMoveOnElement(driver,driver.findElement(By.xpath("//img[@src='themes/softed/images/user.PNG']")));
		 driver.findElement(By.xpath("//a[text()='Sign Out']")).click();
		 
		 driver.quit();

	}

}
