package com.comcast.crm.leadtest_using_pom;

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
import com.comcast.crm.objectrepositoryutility.CreateNewLeadPage;
import com.comcast.crm.objectrepositoryutility.HomePage;
import com.comcast.crm.objectrepositoryutility.LeadInfoPage;
import com.comcast.crm.objectrepositoryutility.LeadPage;
import com.comcast.crm.objectrepositoryutility.LoginPage;

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
		LoginPage lp=new LoginPage(driver);
		lp.login(UN, PW);

		// 3)Navigate to leads
		HomePage hp=new HomePage(driver);
		hp.getLeadLink().click();

		// 4)Navigate to create new lead page by click on + image
		LeadPage lep=new LeadPage(driver);
		lep.getCreateNewLeadBtn().click();

		/*
		 * 5)create a lead with mandatory name field (last name, company, check assign
		 * to user radio button is selected and check the drop down option is selected
		 * with default content
		 */
		CreateNewLeadPage cnp=new CreateNewLeadPage(driver);
		cnp.getLastNameEdit().sendKeys(lastname);
		cnp.getCompanyEdit().sendKeys(company);
		if (cnp.getAssignToRadio().isSelected()) {
			System.out.println("User selected by default is verified Successfully");
		} else {
			System.out.println("User is not Selected");
		}
		
		WebElement opt= cnp.getAssignToDD();
		wLib.select(opt, assign);
		//opt.getText()
		System.out.println(opt.getText());
		if((opt.getText().trim().equals(assign))){
			System.out.println("Administrator drop down Selected and verified Successfully");
		} else {
			System.out.println("Administrator drop down not Selected ");
		}
		
		cnp.getSaveBtn().click();

		
		 //verify Header msg Expected result
		LeadInfoPage lip=new LeadInfoPage(driver);
		 String headerInfo = lip.getHeaderMsg().getText();
		 //dynamic data is there so use contains() to get partial text
		 if(headerInfo.contains(lastname)){
			 System.out.println(lastname+" is created == PASS"); 
		 }
		 else{
			 System.out.println(lastname+" is not created == FAIL"); 
		 } 
		 
		 //verify lastname Expected result
		 String actualLastName = lip.getActLastName().getText(); 
		 if(actualLastName.equals(lastname)){
			 System.out.println(lastname+" information is created == PASS");
		 }
		else {
			System.out.println(lastname+" information is not created == FAIL");
		 }
		 
		 String actualCompany = lip.getActCompany().getText(); 
		 if(actualCompany.equals(company)){
			 System.out.println(company+" information is created == PASS");
		 }
		else {
			System.out.println(company+" information is not created == FAIL");
		 }
		 
		 

		// 6)logout
		 
		 hp.logout(); 
		 driver.quit();

	}

}
