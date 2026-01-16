package com.comcast.crm.orgtest_using_pom;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.Random;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;

import com.comcast.crm.generic.fileutility.ExcelUtility;
import com.comcast.crm.generic.fileutility.FileUtility;
import com.comcast.crm.generic.webdriverutility.JavaUtility;
import com.comcast.crm.generic.webdriverutility.WebDriverUtility;
import com.comcast.crm.objectrepositoryutility.CreateNewOrganizationPage;
import com.comcast.crm.objectrepositoryutility.HomePage;
import com.comcast.crm.objectrepositoryutility.LoginPage;
import com.comcast.crm.objectrepositoryutility.OrganizationInfoPage;
import com.comcast.crm.objectrepositoryutility.OrganizationPage;

public class CreateOrganization {

	public static void main(String[] args) throws IOException {
		//read common data from property file
		
		/* Create Object*/
		
		FileUtility fil=new FileUtility();
		ExcelUtility eu=new ExcelUtility();
		JavaUtility jLib=new JavaUtility();
		WebDriverUtility wLib=new WebDriverUtility();
		
		
		String BROWSER=fil.getDataFromPropertiesFile("browser");
		String URL=fil.getDataFromPropertiesFile("url");
		String UN=fil.getDataFromPropertiesFile("username");
		String PW=fil.getDataFromPropertiesFile("password");
		
		//read testscript data from excel
		
	    String orgname = eu.getDataFromExcel("org",1, 2)+jLib.getRandomNumber();
	    
	    
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
		
		//1) Navigate to application
		driver.get(URL);
		
		//2)Login to app
		LoginPage lp=new LoginPage(driver);
		/*lp.getUsernameEdit().sendKeys(UN);
		lp.getPasswordEdit().sendKeys(PW);
		lp.getSubmitBtn().click();*/
		lp.login(UN, PW);
		
		//3)Navigate to org 
		HomePage hp=new HomePage(driver);
		hp.getOrgLink().click();
		
		//4)Navigate to create new org page by click on + image
		OrganizationPage op=new OrganizationPage(driver);
		op.getCreateNewOrgbtn().click();
		
		//5)create a org with mandatory name field
		CreateNewOrganizationPage cop=new CreateNewOrganizationPage(driver);
		cop.createOrg(orgname);
		
		
		
		//verify  Header msg Expected result
		OrganizationInfoPage oip=new OrganizationInfoPage(driver);
		String headerInfo = oip.getHeaderMsg().getText();
		if(headerInfo.contains(orgname)) {
			System.out.println(orgname+" is created  == PASS");
		}
		else {
			System.out.println(orgname+" is not created == FAIL");
		}
		//verify  Header orgName Expected result
		String actualOrgName =oip.getActualOrgName().getText();
		if(actualOrgName.equals(orgname)) {
			System.out.println(orgname+" actual information is matched == PASS");
		}
		else {
			System.out.println(orgname+" actual information is not matched == FAIL");
		}
		
		//6)logout
		
		hp.logout();
		driver.quit();
	
		

	}

}
