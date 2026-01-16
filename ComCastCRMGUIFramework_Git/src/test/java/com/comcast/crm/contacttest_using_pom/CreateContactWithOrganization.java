package com.comcast.crm.contacttest_using_pom;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.Duration;
import java.util.Iterator;
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
import com.comcast.crm.objectrepositoryutility.CreateNewOrganizationPage;
import com.comcast.crm.objectrepositoryutility.HomePage;
import com.comcast.crm.objectrepositoryutility.LoginPage;
import com.comcast.crm.objectrepositoryutility.OrganizationInfoPage;
import com.comcast.crm.objectrepositoryutility.OrganizationPage;
import com.comcast.crm.orgtest_using_pom.CreateOrganization;

public class CreateContactWithOrganization {

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
			    String orgname = eu.getDataFromExcel("contact",7, 2)+jLib.getRandomNumber();
			    String contactLastname = eu.getDataFromExcel("contact",7, 3)+jLib.getRandomNumber();
				
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
				
				//Precondition: create a org with mandatory name field
				CreateNewOrganizationPage cop=new CreateNewOrganizationPage(driver);
				cop.getOrgNameEdit().sendKeys(orgname);
				cop.getSaveBtn().click();
				
				
				
				//verify  Header msg Expected result
				OrganizationInfoPage oip=new OrganizationInfoPage(driver);
				String headerInfo = oip.getHeaderMsg().getText();
				//dynamic data is there so use contains() to get partial text
				if(headerInfo.contains(orgname)) {
					System.out.println(orgname+" is created == PASS");
				}
				else {
					System.out.println(orgname+" is not created == FAIL");
				}
		
				
				//5) navigate to contact module (Integration TC)
				hp.getContactLink().click();
				
				//6)Navigate to create new contact page by click on + image
				Contactpage cp=new Contactpage(driver);
				cp.getCreateNewContactBtn().click();
						
				//7)create a contact with mandatory name field and save
				CreateNewContactPage cnp=new CreateNewContactPage(driver);
				cnp.getLastNameEdit().sendKeys(contactLastname);
				
				//8)click on org name look up image  "+"
				
				//driver.findElement(By.xpath("//img[@src='themes/softed/images/select.gif']")).click(); --> two matches indexing is not good practice
				//following sibling xpath
				cnp.getOrgNameLookUpImgBtn().click();
				
				
				//Switch to child window
				
				wLib.switchToTabOnURL(driver,"module=Accounts");
				
				//enter org name and click on search
				CreateNewOrganizationPage cnop=new CreateNewOrganizationPage(driver);
				cnop.getOrgSearchEdit().sendKeys(orgname);
				cnop.getOrgSearchNowBtn().click();

				//click the dynamic search result 
				
				//driver.findElement(By.xpath("//a[text()='FaceBook_564']")).click();-->(static xpath) fails next time while executing it keep on changing can't use this xpath
				//(limit of dynamic data is 1000)
				
				//dynamic xpath:xpath also should getting created in runtime is called dynamic xpath instead of FaceBook_564 this static data
				//break down the xpath to concatenate the variable (i.e dynamic orgname ).next time if we execute fb_500 that will come and sit here and perform click action
				driver.findElement(By.xpath("//a[text()='"+orgname+"']")).click();//(before swtich the control we run it fails);
				
				
				//switch to parent window
				wLib.switchToTabOnURL(driver,"Contacts&action");
				
				//CreateNewContactPage cnp=new CreateNewContactPage(driver);
				cnp.getSaveBtn().click();
				
				//verify  Header msg Expected result
				ContactInfoPage cip=new ContactInfoPage(driver);
			    headerInfo = cip.getHeaderMsg().getText();
				//dynamic data is there so use contains() to get partial text
				if(headerInfo.contains(contactLastname)) {
					System.out.println(contactLastname+" is created == PASS");
				}
				else {
					System.out.println(contactLastname+" is not created == FAIL");
				}
				
				//verify  Header orgName Expected result (verify data flow is happening between org name to contact module)
				//in contact module page details page try to verify orgname (very important validation to check data flow)
				String actualOrgName = cip.getActOrgNameIntegration().getText();
				System.out.println(actualOrgName);//op have additional space so verification fails
				//use trim() to remove the space from start and end of string
				if(actualOrgName.trim().equals(orgname)) {
					System.out.println(orgname+" information is created == PASS");
				}
				else {
					System.out.println(orgname+" information is not created == FAIL");
				}
				
				//6)logout
				hp.logout();
				driver.quit();
				

	}

}
