package com.comcast.crm.objectrepositoryutility;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.comcast.crm.generic.webdriverutility.WebDriverUtility;

public class LoginPage extends WebDriverUtility {
	WebDriver driver;
	public LoginPage(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver,this);
		 
		// PageFactory.initElements(driver,LoginPage.class);---->Error
		
	}
	@FindBy(name="user_name")
	private WebElement usernameEdit;
	@FindBy(name="user_password")
	private WebElement passwordEdit;
	@FindBy(id="submitButton")
	private WebElement submitBtn;
	public WebDriver getDriver() {
		return driver;
	}
	public WebElement getUsernameEdit() {
		return usernameEdit;
	}
	public WebElement getPasswordEdit() {
		return passwordEdit;
	}
	public WebElement getSubmitBtn() {
		return submitBtn;
	}
	//business action
	public void login(String username, String password) {
		//driver.manage().window().maximize();
		//driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		//here we inherit with webdriver so without creating object for generic utility we can access
		maximizePage(driver);
		waitForPageToLoad(driver);
		usernameEdit.sendKeys(username);
		passwordEdit.sendKeys(password);
		submitBtn.click();
	}
	
	

}
