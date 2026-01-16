package com.comcast.crm.contacttest_using_pom;

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
import com.comcast.crm.objectrepositoryutility.ContactInfoPage;
import com.comcast.crm.objectrepositoryutility.Contactpage;
import com.comcast.crm.objectrepositoryutility.CreateNewContactPage;
import com.comcast.crm.objectrepositoryutility.HomePage;
import com.comcast.crm.objectrepositoryutility.LoginPage;

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
		LoginPage lp=new LoginPage(driver);
		lp.login(UN, PW);
				
		//3)place mouse cursor and click contact link
		HomePage hp=new HomePage(driver);
		hp.getContactLink().click();
				
		//4)Navigate to create new contact page by click on + image
		Contactpage cp=new Contactpage(driver);
		//Thread.sleep(2000);
		cp.getCreateNewContactBtn().click();
		
		//5)create a contact with mandatory name field and save
		CreateNewContactPage cnp=new CreateNewContactPage(driver);
		cnp.getLastNameEdit().sendKeys(lastname);
		cnp.getSaveBtn().click();
				
		//verify  Header msg Expected result
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
					
		//6)logout
		hp.logout();
		driver.quit();
					

	}

}
