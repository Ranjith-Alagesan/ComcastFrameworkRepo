package com.crm.generic.baseutility;

import java.io.IOException;

import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.comcast.crm.generic.databaseutility.DataBaseUtility;
import com.comcast.crm.generic.fileutility.ExcelUtility;
import com.comcast.crm.generic.fileutility.FileUtility;
import com.comcast.crm.generic.webdriverutility.JavaUtility;
import com.comcast.crm.generic.webdriverutility.WebDriverUtility;
import com.comcast.crm.objectrepositoryutility.HomePage;
import com.comcast.crm.objectrepositoryutility.LoginPage;

public class BaseClass{
	public WebDriver driver=null;
	public static WebDriver sdriver=null;
	public FileUtility fLib=new FileUtility();
	public ExcelUtility eLib=new ExcelUtility();
	public JavaUtility jLib=new JavaUtility();
	public DataBaseUtility dLib=new DataBaseUtility();
	public WebDriverUtility wLib=new WebDriverUtility();
	@BeforeSuite(groups= {"Smoke Test","Regression Test"})
	public void configBS() {
		System.out.println("===Connect to DB, Report Config===");
		dLib.getDbConnection();
	}
	//@org.testng.annotations.Parameters("BROWSER")
	@BeforeClass(groups= {"Smoke Test","Regression Test"})
	public void configBC() throws IOException
	{
		System.out.println("===Launch the BROWSER====");
		String browser=fLib.getDataFromPropertiesFile("browser");
		//System.out.println(browser);
		//String browser=brows;
		if(browser.equals("chrome")) {
			driver=new ChromeDriver();
		}
		else if(browser.equals("firefox")) {
			driver=new FirefoxDriver();
		}
		
		sdriver=driver;
	}
	@BeforeMethod(groups= {"Smoke Test","Regression Test"})
	public void configBM() throws IOException{
		System.out.println("===Login====");
		String url=fLib.getDataFromPropertiesFile("url");
		String un=fLib.getDataFromPropertiesFile("username");
		String pswd=fLib.getDataFromPropertiesFile("password");
		LoginPage p=new LoginPage(driver);
		driver.get(url);
		p.login( un, pswd);
	}
	@AfterMethod(groups= {"Smoke Test","Regression Test"})
	public void configAM()
	{
		System.out.println("===Logout====");
		HomePage hp=new HomePage(driver);
		hp.logout();
	}
	@AfterClass(groups= {"Smoke Test","Regression Test"})
	public void configAC() {
		System.out.println("===Close the BROWSER===");
		driver.quit();
	}
	@AfterSuite(groups= {"Smoke Test","Regression Test"})
	public void configAS() {
		System.out.println("===Close DB, Report backUp===");
		dLib.closeConnection();
	}
}
