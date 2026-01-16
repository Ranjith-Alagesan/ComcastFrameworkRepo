package com.comcast.crm.contacttest_using_pom;

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
import com.comcast.crm.objectrepositoryutility.ContactInfoPage;
import com.comcast.crm.objectrepositoryutility.Contactpage;
import com.comcast.crm.objectrepositoryutility.CreateNewContactPage;
import com.comcast.crm.objectrepositoryutility.HomePage;
import com.comcast.crm.objectrepositoryutility.LoginPage;

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
		LoginPage lp=new LoginPage(driver);
		lp.login(UN, PW);
				
		//3)place mouse cursor and click contact link
		HomePage hp=new HomePage(driver);
		hp.getContactLink().click();
				
		//4)Navigate to create new contact page by click on + image
		Contactpage cp=new Contactpage(driver);
		cp.getCreateNewContactBtn().click();
	
				
		//5)create a contact with mandatory name field and save
		CreateNewContactPage cnp=new CreateNewContactPage(driver);
		cnp.getLastNameEdit().sendKeys(lastname);
		
		//capture current date
		
		String startdate=jLib.getSystemDateYYYYMMDD();
		String enddate = jLib.getRequiredDateYYYYMMDD(30);
		//System.out.println(dateRequires);
		cnp.getSupportStartDate().clear();
		cnp.getSupportStartDate().sendKeys(startdate);
		cnp.getSupportEndDate().clear();
		cnp.getSupportEndDate().sendKeys(enddate);
		
		cnp.getSaveBtn().click();
				
		//verification
		ContactInfoPage cip=new ContactInfoPage(driver);
		String headerInfo = cip.getHeaderMsg().getText();
		if(headerInfo.contains(lastname)) {
			System.out.println(lastname+" is created == PASS");
		}
		else {
			System.out.println(lastname+" is not created == FAIL");
		}
		//verify  Header orgName Expected result
		String actualLastname = cip.getActualLastName().getText();
		if(actualLastname.equals(lastname)) {
			System.out.println(lastname+" information is created == PASS");
		}
		else {
			System.out.println(lastname+" information is not created == PASS");
		}
			
		//verify support start and end date
		String actStartDate = cip.getActualSupportStartDate().getText();
		if(actStartDate.equals(startdate)) {
			System.out.println(startdate+" information is created == PASS");
		}
		else {
			System.out.println(startdate+" information is not created == FAIL");
		}
		String actEndDate = cip.getActualSupportEndDate().getText();
		if(actEndDate.equals(enddate)){
			System.out.println(enddate+" information is created == PASS");
		}
		else {
			System.out.println(enddate+" information is not created == FAIL");
		}
		
					
		//6)logout
		hp.logout();
		driver.quit();
					

	}

}
