package com.comcast.crm.objectrepositoryutility;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class CreateNewOrganizationPage {
	//WebDriver driver;
		public CreateNewOrganizationPage(WebDriver driver) {
		//	this.driver=driver;
			PageFactory.initElements(driver,this);
		}
		@FindBy(name="accountname")
		private WebElement orgNameEdit;
		
		@FindBy(name = "industry")
		private WebElement industryDD;
		@FindBy(name="accounttype")
		private WebElement typeDD;
		@FindBy(id = "phone")
		private WebElement phoneEdit;
		@FindBy(xpath = "//input[@title='Save [Alt+S]']")
		private WebElement saveBtn;
		
		@FindBy(name = "search_text")
		private WebElement orgSearchEdit;
		@FindBy(name = "search")
		private WebElement orgSearchNowBtn;
		
		public WebElement getIndustryDD() {
			return industryDD;
		}
		public WebElement getTypeDD() {
			return typeDD;
		}
		public WebElement getOrgNameEdit() {
			return orgNameEdit;
		}
		public WebElement getSaveBtn() {
			return saveBtn;
		}
		
		public WebElement getPhoneEdit() {
			return phoneEdit;
		}
		
		public WebElement getOrgSearchEdit() {
			return orgSearchEdit;
		}
		public WebElement getOrgSearchNowBtn() {
			return orgSearchNowBtn;
		}
		//business action
		public void createOrg(String orgName) {
			orgNameEdit.sendKeys(orgName);
			saveBtn.click();
		}
		public void createOrg(String orgName, String industry,String type) {
			orgNameEdit.sendKeys(orgName);
			Select s=new Select(industryDD);
			s.selectByVisibleText(industry);
			Select s1=new Select(typeDD);
			s1.selectByVisibleText(type);
			saveBtn.click();
		}

}
