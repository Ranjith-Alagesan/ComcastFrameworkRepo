package com.comcast.crm.producttest_using_pom;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.invoke.MethodHandles.Lookup.ClassOption;
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
import com.comcast.crm.objectrepositoryutility.CreateNewProductPage;
import com.comcast.crm.objectrepositoryutility.HomePage;
import com.comcast.crm.objectrepositoryutility.LoginPage;
import com.comcast.crm.objectrepositoryutility.ProductInfoPage;
import com.comcast.crm.objectrepositoryutility.ProductPage;

public class CreateProductWithSupportDate {

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
		String productname= eu.getDataFromExcel("product", 1, 2)+jLib.getRandomNumber();
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

		// 3)Navigate to product
		HomePage hp=new HomePage(driver);
		hp.getProductLink().click();

		// 4)Navigate to create new product page by click on + image
		ProductPage p=new ProductPage(driver);
		p.getCreateNewProductBtn().click();

		/*
		 * 5)create a product with mandatory name field with support date
		 */
		CreateNewProductPage cnp=new CreateNewProductPage(driver);
		cnp.getProductNameEdt().sendKeys(productname);
	
		if (cnp.getProductActive().isSelected()) {
			System.out.println("product active is verified");
		} else {
			System.out.println("product active is verified");
		}
		
		//support start date
		String startDate = jLib.getSystemDateYYYYMMDD();
		
		//support end date
		String endDate = jLib.getRequiredDateYYYYMMDD(730);
		
		cnp.getSupportStartDate().sendKeys(startDate);
		cnp.getSupportExpiryDate().sendKeys(endDate);
		cnp.getSavebtn().click();

		
		
		 //verify Header msg Expected result 
		ProductInfoPage pip=new ProductInfoPage(driver);
		 String headerInfo = pip.getHeaderMsg().getText();
		 //dynamic data is there so use contains() to get partial text
		 if(headerInfo.contains(productname)){
			 System.out.println(productname+" is created == PASS"); 
		 }
		 else{
			 System.out.println(productname+" is not created == FAIL"); 
		 } 
		 
		 //verify Header last Expected result
		 
		 String actualLastName =pip.getActualProductName().getText(); 
		 if(actualLastName.equals(productname)){
			 System.out.println(productname+" information is created == PASS");
		 }
		else {
			System.out.println(productname+" information is not created == FAIL");
		 }
		 
		String actStartdate = pip.getActualSupportStartDate().getText();
		if(actStartdate.equals(startDate)) {
			System.out.println(actStartdate+" information is created == PASS");
		}
		else {
			System.out.println(actStartdate+" information is created == FAIL");
		}
		String actEnddate = pip.getActualSupportExpiryDate().getText();
		if(actEnddate.equals(endDate)) {
			System.out.println(actEnddate+" information is created == PASS");
		}
		else {
			System.out.println(actEnddate+" information is created == FAIL");
		}

		// 6)logout
		hp.logout();
		driver.quit();

	}

}
