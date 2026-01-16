package contact_test_group_execution_and_parallel_execution;

import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.comcast.crm.objectrepositoryutility.ContactInfoPage;
import com.comcast.crm.objectrepositoryutility.Contactpage;
import com.comcast.crm.objectrepositoryutility.CreateNewContactPage;
import com.comcast.crm.objectrepositoryutility.HomePage;
import com.crm.generic.baseutility.BaseClass;
import com.crm.generic.baseutility.BaseClass_Cross_Browser;

public class CreateContactTest extends BaseClass_Cross_Browser{
	
	
	//CreateNewContactPage cnp=new CreateNewContactPage(driver);
	
	//read data from excel file  it extends from base class where we create obj for all and give public acess no need for obj creation
	@Test(groups ="Smoke Test")
	public void createContactTest() throws EncryptedDocumentException, IOException {
		//read  testscript data from excel file
		String lastName=eLib.getDataFromExcel("contact", 1, 2)+jLib.getRandomNumber();
		//navigate to contact page
		HomePage hp=new HomePage(driver);
		hp.getContactLink().click();
		//click on create  contact button
		Contactpage cp=new Contactpage(driver);
		cp.getCreateNewContactBtn().click();
		//enter all mandatory  details and create new contact
		CreateNewContactPage cnp=new CreateNewContactPage(driver);
		cnp.getLastNameEdit().sendKeys(lastName);
		cnp.getSaveBtn().click();
		//verification
		ContactInfoPage cip=new ContactInfoPage(driver);
		String headerMsg=cip.getHeaderMsg().getText();
		boolean status=headerMsg.contains(lastName);
		Assert.assertTrue(status);
		String actLastName=cip.getActualLastName().getText();
		Assert.assertEquals(actLastName, lastName);
	}
	@Test(groups ="Regression Test")
	public void createContactWithSupportDate() throws EncryptedDocumentException, IOException {
		String lastName=eLib.getDataFromExcel("contact", 4, 2)+jLib.getRandomNumber();
		HomePage hp=new HomePage(driver);
		hp.getContactLink().click();
		Contactpage cp=new Contactpage(driver);
		cp.getCreateNewContactBtn().click();
		CreateNewContactPage cnp=new CreateNewContactPage(driver);
		cnp.getLastNameEdit().sendKeys(lastName);
		String startdate=jLib.getSystemDateYYYYMMDD();
		String enddate=jLib.getRequiredDateYYYYMMDD(30);
		cnp.getSupportStartDate().clear();
		cnp.getSupportStartDate().sendKeys(startdate);
		cnp.getSupportEndDate().clear();
		cnp.getSupportEndDate().sendKeys(enddate);
		cnp.getSaveBtn().click();
		//verify  Header orgName Expected result
		ContactInfoPage cip=new ContactInfoPage(driver);
		String actualLastname = cip.getActualLastName().getText();
		Assert.assertEquals(actualLastname, lastName);
		// verify support start and end date
		String actStartDate = cip.getActualSupportStartDate().getText();
		Assert.assertEquals(actStartDate, startdate);
		String actEndDate = cip.getActualSupportEndDate().getText();
		Assert.assertEquals(actEndDate, enddate);

	}
	/*
	 * @Test public void createContactWithOrgTest() {
	 * 
	 * 
	 * 
	 * }
	 */
    
}
