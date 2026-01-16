package com.comcast.crm.objectrepositoryutility;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ProductInfoPage {
	public ProductInfoPage(WebDriver driver) {
		PageFactory.initElements(driver,this);
	}
	@FindBy(xpath = "//span[@class='lvtHeaderText']")
	private WebElement headerMsg;
	@FindBy(id = "dtlview_Product Name")
	private WebElement actualProductName;
	@FindBy(id = "dtlview_Support Start Date")
	private WebElement actualSupportStartDate;
	@FindBy(id="dtlview_Support Expiry Date")
	private WebElement actualSupportExpiryDate;
	@FindBy(xpath = "//span[@class='genHeaderSmall']")
	private WebElement actResult;
	public WebElement getHeaderMsg() {
		return headerMsg;
	}
	public WebElement getActualProductName() {
		return actualProductName;
	}
	public WebElement getActualSupportStartDate() {
		return actualSupportStartDate;
	}
	public WebElement getActualSupportExpiryDate() {
		return actualSupportExpiryDate;
	}
	public WebElement getActResult() {
		return actResult;
	}
	
	
	
	

}
