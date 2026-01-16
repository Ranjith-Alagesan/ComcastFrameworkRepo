package com.comcast.crm.objectrepositoryutility;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LeadInfoPage {
	public LeadInfoPage(WebDriver driver) {
		PageFactory.initElements(driver,this);
	}
	@FindBy(xpath = "//span[@class='dvHeaderText']")
	private WebElement headerMsg;
	@FindBy(id = "dtlview_Last Name")
	private WebElement actLastName;
	@FindBy(id = "dtlview_Company")
	private WebElement actCompany;
	@FindBy(id = "dtlview_Industry")
	private WebElement EditedIndustry;
	@FindBy(id="dtlview_Annual Revenue")
	private WebElement actualAnnualRev;
	public WebElement getHeaderMsg() {
		return headerMsg;
	}
	public WebElement getActLastName() {
		return actLastName;
	}
	public WebElement getActCompany() {
		return actCompany;
	}
	public WebElement getEditedIndustry() {
		return EditedIndustry;
	}
	public WebElement getActualAnnualRev() {
		return actualAnnualRev;
	}
	
	
	
	
	

}
