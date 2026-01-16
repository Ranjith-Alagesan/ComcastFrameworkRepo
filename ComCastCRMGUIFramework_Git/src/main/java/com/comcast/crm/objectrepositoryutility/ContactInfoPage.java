package com.comcast.crm.objectrepositoryutility;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ContactInfoPage {
	public ContactInfoPage(WebDriver driver) {
		PageFactory.initElements(driver,this);
	}
	@FindBy(className = "dvHeaderText")
	private WebElement headerMsg;
	@FindBy(id = "dtlview_Last Name")
	private WebElement actualLastName;
	@FindBy(id = "dtlview_Support Start Date")
	private WebElement actualSupportStartDate;
	@FindBy(id = "dtlview_Support End Date")
	private WebElement actualSupportEndDate;
	@FindBy(id = "mouseArea_Organization Name")
	private WebElement actOrgNameIntegration;
	public WebElement getHeaderMsg() {
		return headerMsg;
	}
	public WebElement getActualLastName() {
		return actualLastName;
	}
	public WebElement getActualSupportStartDate() {
		return actualSupportStartDate;
	}
	public WebElement getActualSupportEndDate() {
		return actualSupportEndDate;
	}
	public WebElement getActOrgNameIntegration() {
		return actOrgNameIntegration;
	}
	
	
	

}
