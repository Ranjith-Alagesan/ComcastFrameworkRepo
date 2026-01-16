package com.comcast.crm.objectrepositoryutility;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class CreateNewProductPage {
	public CreateNewProductPage(WebDriver driver) {
		PageFactory.initElements(driver,this);
	}
	@FindBy(name = "productname")
	private WebElement productNameEdt;
	@FindBy(name = "discontinued")
	private WebElement productActive;
	@FindBy(name = "start_date")
	private WebElement supportStartDate;
	@FindBy(name = "expiry_date")
	private WebElement supportExpiryDate;
	@FindBy(name = "search_text")
	private WebElement searchEdit;
	@FindBy(name = "search_field")
	private WebElement searchInDD;
	@FindBy(xpath = "//input[@name='submit']")
	private WebElement searchNowBtn;
	@FindBy(name = "selected_id")
	private WebElement selectSearchResult;
	@FindBy(xpath = "//input[@title='Save [Alt+S]']")
	private WebElement savebtn;
	@FindBy(xpath = "//input[@class='crmbutton small delete']")
	private WebElement deleteProduct;
	
	public WebElement getProductNameEdt() {
		return productNameEdt;
	}
	
	public WebElement getProductActive() {
		return productActive;
	}
	public WebElement getSupportStartDate() {
		return supportStartDate;
	}
	public WebElement getSupportExpiryDate() {
		return supportExpiryDate;
	}
	public WebElement getSearchEdit() {
		return searchEdit;
	}
	
	public WebElement getSearchInDD() {
		return searchInDD;
	}

	public WebElement getSearchNowBtn() {
		return searchNowBtn;
	}

	public WebElement getSelectSearchResult() {
		return selectSearchResult;
	}

	public WebElement getSavebtn() {
		return savebtn;
	}
	
	public WebElement getDeleteProduct() {
		return deleteProduct;
	}
	

	

	public void searchProduct(String productName,String searchIn) {
		searchEdit.sendKeys(productName);
		Select s=new Select(searchInDD);
		s.selectByVisibleText(searchIn);
		searchNowBtn.click();
	}
	
	

}
