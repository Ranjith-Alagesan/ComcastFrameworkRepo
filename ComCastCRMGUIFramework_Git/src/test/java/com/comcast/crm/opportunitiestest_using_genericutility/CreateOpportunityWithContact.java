package com.comcast.crm.opportunitiestest_using_genericutility;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.invoke.MethodHandles.Lookup.ClassOption;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.Random;
import java.util.Set;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.jspecify.annotations.Nullable;
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

public class CreateOpportunityWithContact {

	public static void main(String[] args) throws IOException {
		/*create Object*/
		FileUtility fil=new FileUtility();
		ExcelUtility eut=new ExcelUtility();
		JavaUtility jLib=new JavaUtility();
		WebDriverUtility wLib=new WebDriverUtility();
		
		//read common data from property file			
		String BROWSER=fil.getDataFromPropertiesFile("browser");
		String URL=fil.getDataFromPropertiesFile("url");
		String UN=fil.getDataFromPropertiesFile("username");
		String PW=fil.getDataFromPropertiesFile("password");

		// read testscript data drom excel
		String lastname = eut.getDataFromExcel("opportunities",1,2)+jLib.getRandomNumber();
		String opportunityName = eut.getDataFromExcel("opportunities",1,3)+jLib.getRandomNumber();
		String relatedTo=eut.getDataFromExcel("opportunities",2,4);
		String group=eut.getDataFromExcel("opportunities",1,5);
		String salesStage=eut.getDataFromExcel("opportunities",2,6);

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
		driver.findElement(By.name("user_name")).sendKeys(UN);
		driver.findElement(By.name("user_password")).sendKeys(PW);
		driver.findElement(By.id("submitButton")).click();

		// 3)Navigate to organization
		driver.findElement(By.linkText("Contacts")).click();

		// 4)Navigate to create new organization page by click on + image
		driver.findElement(By.xpath("//img[@title='Create Contact...']")).click();

		//5)create a organization with mandatory name field
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
		
		//6)Navigate to opportunity  
		driver.findElement(By.partialLinkText("Opportunities")).click();
		
		//7)Click + to create new opportunity
		driver.findElement(By.xpath("//img[@title='Create Opportunity...']")).click();
		
		//create opportunity with mandatory field
		driver.findElement(By.name("potentialname")).sendKeys(opportunityName);
		
		//related_to_type
		wLib.select(driver.findElement(By.id("related_to_type")), relatedTo);
		
		//click on contact (last name) look up image  "+"
		driver.findElement(By.xpath("//input[@id='related_to']/following-sibling::img")).click();
		
		//switch to childwindow
		wLib.switchToTabOnURL(driver, "module=Accounts");
		
		//search element
		driver.findElement(By.id("search_txt")).sendKeys(lastname);
		driver.findElement(By.name("search")).click();
		//driver.findElement(By.xpath("//a[contains(text(),'Ranjith_452')]"))-->static xapth
		driver.findElement(By.xpath("//a[contains(text(),'"+lastname+"')]")).click();
		
		//switch back to parent
		wLib.switchToTabOnURL(driver,"module=Potentials&action");
		
		
		//assigned to
		driver.findElement(By.xpath("//input[@value='T']")).click();
		if(driver.findElement(By.xpath("//input[@value='T']")).isSelected()) {
			System.out.println("Assigned user changed to groups");
		}
		else {
			System.out.println("Assigned user not changed to groups");
		}
		
		
		//change group
		
		wLib.select(driver.findElement(By.name("assigned_group_id")), group);
		
		//close date
		
		String currentDate = jLib.getSystemDateYYYYMMDD();
		System.out.println(currentDate);
		String expectedCloseDate = jLib.getRequiredDateYYYYMMDD(30);
		
		driver.findElement(By.name("closingdate")).clear();
		driver.findElement(By.name("closingdate")).sendKeys(expectedCloseDate);
		
		//sales satge
		wLib.select(driver.findElement(By.name("sales_stage")),salesStage);
		//save
		driver.findElement(By.xpath("//input[@title='Save [Alt+S]']")).click();
		
		
		//verify  Header msg Expected result
	    headerInfo = driver.findElement(By.xpath("//span[@class='dvHeaderText']")).getText();
		if(headerInfo.contains(opportunityName)) {
			System.out.println(opportunityName+" is created == PASS");
		}
		else {
			System.out.println(opportunityName+" is not created == FAIL");
		}
		
		//verify  Header orgName Expected result (verify data flow is happening between org name to contact module)
		//in opportunity module page details page try to verify lastname from contact module(very important validation to check data flow)
		String actualLastName = driver.findElement(By.xpath("(//td[@class='dvtCellInfo'])[3]")).getText();
		System.out.println(actualLastName);//op have additional space so verification fails
		//use trim() to remove the space from start and end of string
		if(actualLastName.trim().equals(lastname)) {
			System.out.println(lastname+" information is created == PASS");
		}
		else {
			System.out.println(lastname+" information is not created == FAIL");
		}
		
		//close date verification
		String expectedEndDate = driver.findElement(By.xpath("(//td[@class='dvtCellInfo'])[6]")).getText();
		if(expectedEndDate.trim().equals(expectedCloseDate)) {
			System.out.println(expectedCloseDate+" information is created == PASS");
		}
		else {
			System.out.println(expectedCloseDate+" information is not created == FAIL");
		}
		
		//stage verification
		
		
		String actSaleStage = driver.findElement(By.id("mouseArea_Sales Stage")).getText();
		if( actSaleStage.trim().equals(salesStage)) {
			System.out.println(salesStage+" information is created == PASS");
		}
		else {
			System.out.println(salesStage+" information is not created == FAIL");
		}
	 
		// 6)logout
		 
		wLib.mouseMoveOnElement(driver,driver.findElement(By.xpath("//img[@src='themes/softed/images/user.PNG']")));
		driver.findElement(By.xpath("//a[text()='Sign Out']")).click();
		driver.quit();

	}

	

}
