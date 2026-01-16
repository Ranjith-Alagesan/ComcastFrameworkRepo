package com.crm.comcast.orgtest_using_testng;

import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.comcast.crm.objectrepositoryutility.CreateNewOrganizationPage;
import com.comcast.crm.objectrepositoryutility.HomePage;
import com.comcast.crm.objectrepositoryutility.OrganizationInfoPage;
import com.comcast.crm.objectrepositoryutility.OrganizationPage;
import com.crm.generic.baseutility.BaseClass;


public class CreateOrganizationTest extends BaseClass {
	@Test
	public void createOrganizationTest() throws EncryptedDocumentException, IOException {
		String orgname = eLib.getDataFromExcel("org", 1, 2) + jLib.getRandomNumber();
		// Navigate to org
		HomePage hp = new HomePage(driver);
		hp.getOrgLink().click();
		// Navigate to create new org page by click on + image
		OrganizationPage op = new OrganizationPage(driver);
		op.getCreateNewOrgbtn().click();
		// create a org with mandatory name field
		CreateNewOrganizationPage cop = new CreateNewOrganizationPage(driver);
		cop.createOrg(orgname);
		// verify Header msg Expected result
		OrganizationInfoPage oip = new OrganizationInfoPage(driver);
		String headerInfo = oip.getHeaderMsg().getText();
		boolean status=headerInfo.contains(orgname);
		Assert.assertEquals(status, true);
		// verify actual orgName Expected result
		String actualOrgName = oip.getActualOrgName().getText();
		SoftAssert  assertObj=new SoftAssert();
		assertObj.assertEquals(actualOrgName, orgname);
		assertObj.assertAll();
	}

	@Test
	public void CreateOrganizationWithIndustry() throws EncryptedDocumentException, IOException {
		String orgname = eLib.getDataFromExcel("org", 4, 2) + jLib.getRandomNumber();
		String industry = eLib.getDataFromExcel("org", 4, 3);
		String type = eLib.getDataFromExcel("org", 4, 4);
		//Navigate to org
		HomePage hp = new HomePage(driver);
		hp.getOrgLink().click();
		//Navigate to create new org page by click on + image
		OrganizationPage op = new OrganizationPage(driver);
		op.getCreateNewOrgbtn().click();
		//create a new org with mandatory name field
		CreateNewOrganizationPage cnop = new CreateNewOrganizationPage(driver);
		cnop.createOrg(orgname, industry, type);
		//verify Header msg Expected result
		OrganizationInfoPage oip = new OrganizationInfoPage(driver);
		String headerInfo = oip.getHeaderMsg().getText();
		boolean status=headerInfo.contains(orgname);
		Assert.assertTrue(status);
		// verify the industries and type info
		String actualIndustry = oip.getActualInd().getText();
		Assert.assertEquals(actualIndustry,industry);

		String actualType = oip.getActualTyp().getText();
		Assert.assertEquals(actualType, type);
		

	}

}
