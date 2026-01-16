package com.comcast.crm.objectrepositoryutility;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
	WebDriver driver;
	public HomePage(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver,this);
	}
	@FindBy(linkText = "Organizations")
	private WebElement orgLink;
	@FindBy(linkText = "Contacts")
	private WebElement contactLink;
	@FindBy(linkText = "Leads")
	private WebElement leadLink;
	@FindBy(linkText = "Products")
	private WebElement productLink;
	@FindBy(linkText = "Opportunities")
	private WebElement opportunitiesLink;
	@FindBy(linkText = "Campaigns")
	private WebElement campaignsLink;
	@FindBy(linkText = "More")
	private WebElement moreLink;
	
	
	@FindBy(xpath = "//img[@src='themes/softed/images/user.PNG']")
	private WebElement adminImg;
	@FindBy(linkText = "Sign Out")
	private WebElement logoutBtn;
	public WebDriver getDriver() {
		return driver;
	}
	public WebElement getOrgLink() {
		return orgLink;
	}
	public WebElement getContactLink() {
		return contactLink;
	}
	
	public WebElement getLeadLink() {
		return leadLink;
	}
	public WebElement getProductLink() {
		return productLink;
	}
	public WebElement getOpportunitiesLink() {
		return opportunitiesLink;
	}
	public WebElement getAdminImg() {
		return adminImg;
	}
	public WebElement getLogoutBtn() {
		return logoutBtn;
	}
	public WebElement getCampaignsLink() {
		return campaignsLink;
	}
	public WebElement getMoreLink() {
		return moreLink;
	}
	public void navigateToCampagin() {
		Actions act=new Actions(driver);
		act.moveToElement(moreLink).perform();
		campaignsLink.click();
	}
	public void logout() {
		Actions act =new Actions(driver);
		act.moveToElement(adminImg).perform();
		logoutBtn.click();
	}
	
}
