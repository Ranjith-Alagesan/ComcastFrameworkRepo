package com.comcast.crm.objectrepositoryutility;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class OpportunitiesInfoPage {
	public OpportunitiesInfoPage(WebDriver driver){
		PageFactory.initElements(driver,this);
	}
	@FindBy(className = "dvHeaderText")
	private WebElement headerMsg;
	@FindBy(xpath = "//a[@title='Contacts']")
	private WebElement actuaLLastName;
	@FindBy(xpath = "(//td[@class='dvtCellInfo'])[6]")
	private WebElement actualExpectedCloseDate;
	@FindBy(id = "mouseArea_Sales Stage")
	private WebElement actualSaleStage;
	public WebElement getHeaderMsg() {
		return headerMsg;
	}
	public WebElement getActuaLLastName() {
		return actuaLLastName;
	}
	public WebElement getActualExpectedCloseDate() {
		return actualExpectedCloseDate;
	}
	public WebElement getActualSaleStage() {
		return actualSaleStage;
	}
	
	
	
	
	
	

}
