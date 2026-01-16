package com.comcast.crm.objectrepositoryutility;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CreateNewContactPage {
	public CreateNewContactPage(WebDriver driver) {
		PageFactory.initElements(driver,this);
		
	}
	@FindBy(name="lastname")
	private WebElement lastNameEdit;
	@FindBy(name="support_start_date")
	private WebElement supportStartDate;
	@FindBy(name="support_end_date")
	private WebElement supportEndDate;
	@FindBy(xpath = "//input[@name='account_name']/following-sibling::img")
	private WebElement orgNameLookUpImgBtn;
	@FindBy(xpath = "//input[@title='Save [Alt+S]']")
	private WebElement saveBtn;
	
	
	@FindBy(name = "search_text")
	private WebElement contactSearchEdit;
	@FindBy(name = "search")
	private WebElement contactSearchNowBtn;

	
	public WebElement getLastNameEdit() {
		return lastNameEdit;
	}
	public WebElement getSupportStartDate() {
		return supportStartDate;
	}
	public WebElement getSupportEndDate() {
		return supportEndDate;
	}
	
	public WebElement getOrgNameLookUpImgBtn() {
		return orgNameLookUpImgBtn;
	}
	public WebElement getSaveBtn() {
		return saveBtn;
	}
	public WebElement getContactSearchEdit() {
		return contactSearchEdit;
	}
	public WebElement getContactSearchNowBtn() {
		return contactSearchNowBtn;
	}
	

	
	

}
