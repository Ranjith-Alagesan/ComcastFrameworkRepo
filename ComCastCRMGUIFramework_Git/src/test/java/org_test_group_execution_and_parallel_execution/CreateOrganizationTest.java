package org_test_group_execution_and_parallel_execution;

import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.testng.annotations.Test;

import com.comcast.crm.objectrepositoryutility.CreateNewOrganizationPage;
import com.comcast.crm.objectrepositoryutility.HomePage;
import com.comcast.crm.objectrepositoryutility.OrganizationInfoPage;
import com.comcast.crm.objectrepositoryutility.OrganizationPage;
import com.crm.generic.baseutility.BaseClass;
import com.crm.generic.baseutility.BaseClass_Cross_Browser;

public class CreateOrganizationTest extends BaseClass_Cross_Browser {
	@Test(groups="Smoke Test")
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
		if (headerInfo.contains(orgname)) {
			System.out.println(orgname + " is created  == PASS");
		} else {
			System.out.println(orgname + " is not created == FAIL");
		}
		// verify Header orgName Expected result
		String actualOrgName = oip.getActualOrgName().getText();
		if (actualOrgName.equals(orgname)) {
			System.out.println(orgname + " actual information is matched == PASS");
		} else {
			System.out.println(orgname + " actual information is not matched == FAIL");
		}

	}

	@Test(groups="Regression Test")
	public void createOrganizationWithIndustry() throws EncryptedDocumentException, IOException {
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
		if (headerInfo.contains(orgname)) {
			System.out.println(orgname + " is created  == PASS");
		} else {
			System.out.println(orgname + " is not created == FAIL");
		}
		// verify the industries and type info
		String actualIndustry = oip.getActualInd().getText();
		if (actualIndustry.equals(industry)) {
			System.out.println(industry + " information is verified == PASS");
		} else {
			System.out.println(industry + " information is not verified == FAIL");

		}

		String actualType = oip.getActualTyp().getText();
		if (actualType.equals(type)) {
			System.out.println(type + " information is verified == PASS");
		} else {
			System.out.println(type + " information is not verified == FAIL");

		}

	}

}
