package com.comcast.crm.contacttest_using_genericutility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

public class CreateContact {

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
				
		//read testscript data drom excel
		String lastname = eu.getDataFromExcel("contact",1, 2)+jLib.getRandomNumber();
		
				
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
		
		
		driver.findElement(By.xpath("//input[@title='Save [Alt+S]']")).click();
				
		//verify  Header msg Expected result
		String headerInfo = driver.findElement(By.xpath("//span[@class='dvHeaderText']")).getText();
		//dynamic data is there so use contains() to get partial text
		if(headerInfo.contains(lastname)) {
			System.out.println(lastname+" is created == PASS");
		}
		else {
			System.out.println(lastname+" is not created == FAIL");
		}
		//verify  Header orgName Expected result
		String actualLastname = driver.findElement(By.id("dtlview_Last Name")).getText();
		//not dyanamic complete data is there so use equals()
		if(actualLastname.equals(lastname)) {
			System.out.println(lastname+" information is created == PASS");
		}
		else {
			System.out.println(lastname+" information is not created == PASS");
		}
					
		//6)logout
		wLib.mouseMoveOnElement(driver,driver.findElement(By.xpath("//img[@src='themes/softed/images/user.PNG']")));
		driver.findElement(By.xpath("//a[text()='Sign Out']")).click();
		driver.quit();
					

	}

}
