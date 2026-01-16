package com.comcast.crm.objectrepositoryutility;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CreateNewOpportunitiesPage {
	public CreateNewOpportunitiesPage(WebDriver driver) {
		PageFactory.initElements(driver,this);
	}
	@FindBy(name = "potentialname")
	private WebElement opportunityNameEdt;
	@FindBy(id="related_to_type")
	private WebElement relatedToDD;
	@FindBy(xpath = "//input[@id='related_to_display']/following-sibling::img")
	private WebElement contactLookUpImgBtn;
	
	
	
	@FindBy(xpath = "//input[@value='T']")
	private WebElement assignedToRadioBtn;
	@FindBy(name = "assigned_group_id")
	private WebElement assignedToDD;
	
	@FindBy(name = "closingdate")
	private WebElement expectedCloseDateEdit;
	@FindBy(name = "sales_stage")
	private WebElement saleStageDD;
	@FindBy(xpath = "//input[@title='Save [Alt+S]']")
	private WebElement OppSaveBtn;
	public WebElement getOpportunityNameEdt() {
		return opportunityNameEdt;
	}
	public WebElement getRelatedToDD() {
		return relatedToDD;
	}
	public WebElement getContactLookUpImgBtn() {
		return contactLookUpImgBtn;
	}
	public WebElement getAssignedToRadioBtn() {
		return assignedToRadioBtn;
	}
	public WebElement getAssignedToDD() {
		return assignedToDD;
	}
	public WebElement getExpectedCloseDateEdit() {
		return expectedCloseDateEdit;
	}
	public WebElement getSaleStageDD() {
		return saleStageDD;
	}
	public WebElement getOppSaveBtn() {
		return OppSaveBtn;
	}
	
	

}
