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
		LoginPage lp=new LoginPage(driver);
		lp.login(UN, PW);

		// 3)Navigate to leads
		HomePage hp=new HomePage(driver);
		hp.getLeadLink().click();

		// 4)Navigate to create new lead page by click on + image
		LeadPage ldp=new LeadPage(driver);
		ldp.getCreateNewLeadBtn().click();

		/*
		 * 5)create a lead with mandatory name field add industry and edit industry
		 */
		
		CreateNewLeadPage cnp=new CreateNewLeadPage(driver);
		cnp.getLastNameEdit().sendKeys(lastname);
		cnp.getCompanyEdit().sendKeys(company);
		
		
		wLib.select(cnp.getIndustryDD(), industry);
		
		cnp.getAnnualRevenue().clear();
		cnp.getAnnualRevenue().sendKeys(annualIncome);
		cnp.getSaveBtn().click();
		
		wLib.mouseMoveOnElement(driver, cnp.getMouseAreaIndustry());
		cnp.getEditIndustryBtn().click();
		
		
		wLib.select(cnp.getTxtBoxIndustryEdit(),editIndustry);
		cnp.getEditIndustrySavebtn().click();
		
		//verification
		LeadInfoPage lip=new LeadInfoPage(driver);
		String editedIndustry = lip.getEditedIndustry().getText();
		//System.out.println(editedIndustry);
		if(editedIndustry.equals(editIndustry)) {
			System.out.println(editedIndustry+ " is verified after edit == PASS");
		}
		else {
			System.out.println(editedIndustry+ " is not verified after edit == FAIL");
		}
		String actualAnnualRev = lip.getActualAnnualRev().getText();
		String actAnnualRev = actualAnnualRev.replaceAll(",","");
		if(actAnnualRev.equals(annualIncome)) {
			System.out.println(actAnnualRev+ " is verified == PASS");
		}
		else {
			System.out.println(actAnnualRev+ " is not verified == FAIL");
		}
	
		//6)logout
	     hp.logout();
		 driver.quit();

	}

}
