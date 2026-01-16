package com.comcast.crm.objectrepositoryutility;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ProductPage {
	public ProductPage(WebDriver driver) {
		PageFactory.initElements(driver,this);
	}
	@FindBy(xpath = "//img[@alt='Create Product...']")
	private WebElement CreateNewProductBtn;
	public WebElement getCreateNewProductBtn() {
		return CreateNewProductBtn;
	}
	

}
