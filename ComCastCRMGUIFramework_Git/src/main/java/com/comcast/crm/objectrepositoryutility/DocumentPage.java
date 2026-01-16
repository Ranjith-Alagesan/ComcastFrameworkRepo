package com.comcast.crm.objectrepositoryutility;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class DocumentPage {
	public DocumentPage(WebDriver driver) {
		PageFactory.initElements(driver,this);
	}
	@FindBy(xpath = "//img[@alt='Create Document...']")
	private WebElement createNewDocumentBtn;
	public WebElement getCreateNewDocumentBtn() {
		return createNewDocumentBtn;
	}
	

}
