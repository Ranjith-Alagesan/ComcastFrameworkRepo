package com.comcast.crm.orgtest_using_pom;

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
import com.comcast.crm.objectrepositoryutility.CreateNewOrganizationPage;
import com.comcast.crm.objectrepositoryutility.HomePage;
import com.comcast.crm.objectrepositoryutility.LoginPage;
import com.comcast.crm.objectrepositoryutility.OrganizationInfoPage;
import com.comcast.crm.objectrepositoryutility.OrganizationPage;

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
		LoginPage lp=new LoginPage(driver);
		lp.login(UN, PW);
		
		//3)Navigate to org 
		HomePage hp=new HomePage(driver);
		hp.getOrgLink().click();
		
		//4)Navigate to create new org page by click on + image
		OrganizationPage op=new OrganizationPage(driver);
		op.getCreateNewOrgbtn().click();
		
		//5)create a org with mandatory name field and phone number
		CreateNewOrganizationPage cnop=new CreateNewOrganizationPage(driver);
		cnop.getOrgNameEdit().sendKeys(orgname);
		cnop.getPhoneEdit().sendKeys(phone);
		cnop.getSaveBtn().click();
		
	
		
		//verify phone number info Expected result
		OrganizationInfoPage oip=new OrganizationInfoPage(driver);
		String headerInfo = oip.getHeaderMsg().getText();
		if(headerInfo.contains(orgname)) {
			System.out.println(orgname+" is created  == PASS");
		}
		else {
			System.out.println(orgname+" is not created == FAIL");
		}
		
		String actualPhone = oip.getActualphone().getText();
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
