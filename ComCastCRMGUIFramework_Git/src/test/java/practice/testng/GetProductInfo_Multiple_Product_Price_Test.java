package practice.testng;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class GetProductInfo_Multiple_Product_Price_Test {
	@Test(dataProvider = "getData")
	public void getProductInfoTest(String brand,String productName) {
		WebDriver driver=new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		driver.get("https://www.amazon.in/");
		driver.findElement(By.id("twotabsearchtextbox")).sendKeys(brand,Keys.ENTER);
		//static xpath we are getting multiple product so product name get change dynamically so use dynamic xpath
		//String xp="//span[text()='iPhone 15 (128 GB) - Blue']/../../../../div[3]/div[1]/div/div[1]/div[1]/div[1]/a/span/span[2]";
		
		String xp="//span[text()='"+productName+"']/../../../../div[3]/div[1]/div/div[1]/div[1]/div[1]/a/span/span[2]";
		String price =driver.findElement(By.xpath(xp)).getText();
		System.out.println(price);
		driver.quit();
	}
	@DataProvider
	public Object[][] getData() {
		Object[][] objArr=new Object[3][2];
		objArr[0][0]="iphone";
		objArr[0][1]="iPhone 15 (128 GB) - Black";
		objArr[1][0]="iphone";
		objArr[1][1]="iPhone 17 Pro Max 512 GB: 17.42 cm (6.9″) Display with Promotion, A19 Pro Chip, Best Battery Life in Any iPhone Ever, Pro Fusion Camera System, Center Stage Front Camera; Deep Blue";
		objArr[2][0]="iphone";
		objArr[2][1]="iPhone 17 Pro 256 GB: 15.93 cm (6.3″) Display with Promotion up to 120Hz, A19 Pro Chip, Breakthrough Battery Life, Pro Fusion Camera System with Center Stage Front Camera; Silver";
		return objArr;
		
	}
	

}
