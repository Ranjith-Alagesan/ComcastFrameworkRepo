package com.comcast.crm.opportunitiestest_using_pom;

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
import com.comcast.crm.objectrepositoryutility.ContactInfoPage;
import com.comcast.crm.objectrepositoryutility.Contactpage;
import com.comcast.crm.objectrepositoryutility.CreateNewContactPage;
import com.comcast.crm.objectrepositoryutility.CreateNewOpportunitiesPage;
import com.comcast.crm.objectrepositoryutility.CreateNewOrganizationPage;
import com.comcast.crm.objectrepositoryutility.HomePage;
import com.comcast.crm.objectrepositoryutility.LoginPage;
import com.comcast.crm.objectrepositoryutility.OpportunitiesInfoPage;
import com.comcast.crm.objectrepositoryutility.OpportunitiesPage;

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
		LoginPage lp=new LoginPage(driver);
		lp.login(UN, PW);

		// 3)Navigate to contact
		HomePage hp=new HomePage(driver);
		hp.getContactLink().click();
	
		// 4)Navigate to create new contact page by click on + image
		Contactpage cp=new Contactpage(driver);
		cp.getCreateNewContactBtn().click();

		//5)create a contact with mandatory name field
		CreateNewContactPage cnp=new CreateNewContactPage(driver);
		cnp.getLastNameEdit().sendKeys(lastname);
		cnp.getSaveBtn().click();
		
		//verify  Header msg Expected result
		ContactInfoPage cip=new ContactInfoPage(driver);
		String headerInfo = cip.getHeaderMsg().getText();
		//dynamic data is there so use contains() to get partial text
		if(headerInfo.contains(lastname)) {
			System.out.println(lastname+" is created == PASS");
		}
		else {
			System.out.println(lastname+" is not created == FAIL");
		}
		
		//6)Navigate to opportunity 
		hp.getOpportunitiesLink().click();
		
		//7)Click + to create new opportunity
		OpportunitiesPage op=new OpportunitiesPage(driver);
		op.getCreateNewOpportunityBtn().click();
		
		//create opportunity with mandatory field
		CreateNewOpportunitiesPage cnop=new CreateNewOpportunitiesPage(driver);
		cnop.getOpportunityNameEdt().sendKeys(opportunityName);
		
		//related_to_type
		
		wLib.select(cnop.getRelatedToDD(), relatedTo);
		
		//click on contact (last name) look up image  "+"
		cnop.getContactLookUpImgBtn().click();
		
		//switch to childwindow
		wLib.switchToTabOnURL(driver, "module=Accounts");
		
		//search element
		CreateNewContactPage cncp=new CreateNewContactPage(driver);
		cncp.getContactSearchEdit().sendKeys(lastname);
		cncp.getContactSearchNowBtn().click();
		//driver.findElement(By.xpath("//a[contains(text(),'Ranjith_452')]"))-->static xapth
		driver.findElement(By.xpath("//a[contains(text(),'"+lastname+"')]")).click();
		
		//switch back to parent
		wLib.switchToTabOnURL(driver,"module=Potentials&action");
		
		
		//assigned to
		cnop.getAssignedToRadioBtn().click();
		if(cnop.getAssignedToRadioBtn().isSelected()) {
			System.out.println("Assigned user changed to groups");
		}
		else {
			System.out.println("Assigned user not changed to groups");
		}
		
		
		//change group
		
		wLib.select(cnop.getAssignedToDD(), group);
		
		//close date
		
		String currentDate = jLib.getSystemDateYYYYMMDD();
		System.out.println(currentDate);
		String expectedCloseDate = jLib.getRequiredDateYYYYMMDD(30);
		
		cnop.getExpectedCloseDateEdit().clear();
		cnop.getExpectedCloseDateEdit().sendKeys(expectedCloseDate);
		
		//sales satge
		wLib.select(cnop.getSaleStageDD(),salesStage);
		//save
		cnop.getOppSaveBtn().click();
		
		
		//verify  Header msg Expected result
		OpportunitiesInfoPage oip=new OpportunitiesInfoPage(driver);
	    headerInfo = oip.getHeaderMsg().getText();
		if(headerInfo.contains(opportunityName)) {
			System.out.println(opportunityName+" is created == PASS");
		}
		else {
			System.out.println(opportunityName+" is not created == FAIL");
		}
		
		//verify  Header orgName Expected result (verify data flow is happening between org name to contact module)
		//in opportunity module page details page try to verify lastname from contact module(very important validation to check data flow)
		String actualLastName = oip.getActuaLLastName().getText();
		//System.out.println(actualLastName);//op have additional space so verification fails-->getting from a tag so no need of trim
		//use trim() to remove the space from start and end of string
		if(actualLastName.equals(lastname)) {
			System.out.println(lastname+" information is created == PASS");
		}
		else {
			System.out.println(lastname+" information is not created == FAIL");
		}
		
		//close date verification
		//String expectedEndDate = driver.findElement(By.xpath("(//td[@class='dvtCellInfo'])[6]")).getText();
		
		String expectedEndDate = oip.getActualExpectedCloseDate().getText();
		if(expectedEndDate.trim().equals(expectedCloseDate)) {
			System.out.println(expectedCloseDate+" information is created == PASS");
		}
		else {
			System.out.println(expectedCloseDate+" information is not created == FAIL");
		}
		
		//stage verification
		
		
		String actSaleStage = oip.getActualSaleStage().getText();
		if( actSaleStage.trim().equals(salesStage)) {
			System.out.println(salesStage+" information is created == PASS");
		}
		else {
			System.out.println(salesStage+" information is not created == FAIL");
		}
	 
		// 6)logout
		 
		hp.logout();
		driver.quit();

	}

	

}
