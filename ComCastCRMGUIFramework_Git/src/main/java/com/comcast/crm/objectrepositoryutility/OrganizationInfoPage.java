package com.comcast.crm.objectrepositoryutility;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class OrganizationInfoPage {
	public OrganizationInfoPage(WebDriver driver) {
		PageFactory.initElements(driver,this);
	}
	@FindBy(className ="dvHeaderText")
	private WebElement headerMsg;
	@FindBy(id="dtlview_Organization Name")
	private WebElement actualOrgName;
	@FindBy(id="dtlview_Industry")
	private WebElement actualInd;
	@FindBy(id="dtlview_Type")
	private WebElement actualTyp;
	@FindBy(id = "dtlview_Phone")
	private WebElement actualphone;
	public WebElement getHeaderMsg() {
		return headerMsg;
	}
	public WebElement getActualOrgName() {
		return actualOrgName;
	}
	public WebElement getActualInd() {
		return actualInd;
	}
	public WebElement getActualTyp() {
		return actualTyp;
	}
	public WebElement getActualphone() {
		return actualphone;
	}
	
	
	
	
	
	
	
	

}
