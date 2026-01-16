package practice.testng;

import java.io.IOException;
import java.time.Duration;

import org.apache.poi.EncryptedDocumentException;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.comcast.crm.generic.fileutility.ExcelUtility;

public class GetProductInfo_Multiple_Product_Price_FromExcel_Test {
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
	public Object[][] getData() throws EncryptedDocumentException, IOException {
		ExcelUtility elib=new ExcelUtility();
		int rowCount=elib.getRowCount("SampleAmazon");
		Object[][] objArr=new Object[rowCount][2];
		for(int i=0;i<rowCount;i++) {
			objArr[i][0]=elib.getDataFromExcel("SampleAmazon", i+1, 0);
			objArr[i][1]=elib.getDataFromExcel("SampleAmazon", i+1, 1);
		}
		return objArr;
		
	}
	

}
