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
import org.openqa.selenium.Alert;
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

public class SearchAndDeleteProduct {

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
		String productname= eu.getDataFromExcel("product", 5, 2)+jLib.getRandomNumber();
		String searchIn=eu.getDataFromExcel("product", 5, 3);
		
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

		//search product
		hp.getProductLink().click();
		/*cnp.getSearchEdit().sendKeys(productname);
		wLib.select(cnp.getSearchIn(), searchIn);
		cnp.getSearchNowBtn();*/
		cnp.searchProduct(productname, searchIn);		
		Thread.sleep(10000);
		
		//select the search result
		cnp.getSelectSearchResult().click();
		//delete
		cnp.getDeleteProduct().click();
		wLib.switchToAlertAccept(driver);
		 //verify Header last Expected result
		ProductInfoPage pip=new ProductInfoPage(driver);
		String data = pip.getActResult().getText();
		System.out.println(productname+" is not available "+data);
		// 6)logout
		hp.logout();
		driver.quit();

	}

}
