package com.comcast.crm.objectrepositoryutility;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CreateNewLeadPage {
	
	public CreateNewLeadPage(WebDriver driver) {
		PageFactory.initElements(driver,this);
	}
	@FindBy(name = "lastname")
	private WebElement lastNameEdit;
	@FindBy(name = "company")
	private WebElement companyEdit;
	@FindBy(xpath = "//input[@type='radio']/preceding-sibling::input")
	private WebElement assignToRadio;
	@FindBy(name = "assigned_user_id")
	private WebElement assignToDD;
	@FindBy(name = "industry")
	private WebElement industryDD;
	@FindBy(name = "annualrevenue")
	private WebElement annualRevenue;
	@FindBy(id = "mouseArea_Industry")
	private WebElement mouseAreaIndustry;
	@FindBy(xpath ="//a[text()='Edit']" )
	private WebElement editIndustryBtn;
	@FindBy(id = "txtbox_Industry")
	private WebElement txtBoxIndustryEdit;
	@FindBy(name = "button_Industry")
	private WebElement editIndustrySavebtn;
	
	
	@FindBy(xpath = "//input[@title='Save [Alt+S]']")
	private WebElement saveBtn;
	public WebElement getLastNameEdit() {
		return lastNameEdit;
	}
	public WebElement getCompanyEdit() {
		return companyEdit;
	}
	public WebElement getAssignToRadio() {
		return assignToRadio;
	}
	public WebElement getAssignToDD() {
		return assignToDD;
	}
	public WebElement getIndustryDD() {
		return industryDD;
	}
	public WebElement getAnnualRevenue() {
		return annualRevenue;
	}
	
	public WebElement getMouseAreaIndustry() {
		return mouseAreaIndustry;
	}
	public WebElement getEditIndustryBtn() {
		return editIndustryBtn;
	}
	
	public WebElement getTxtBoxIndustryEdit() {
		return txtBoxIndustryEdit;
	}
	public WebElement getEditIndustrySavebtn() {
		return editIndustrySavebtn;
	}
	public WebElement getSaveBtn() {
		return saveBtn;
	}
	
	
	
	

}
