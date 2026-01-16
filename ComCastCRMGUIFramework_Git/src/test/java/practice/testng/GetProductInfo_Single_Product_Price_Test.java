package practice.testng;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class GetProductInfo_Single_Product_Price_Test {
	@Test
	public void getProductInfoTest() {
		WebDriver driver=new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		driver.get("https://www.amazon.in/");
		driver.findElement(By.id("twotabsearchtextbox")).sendKeys("iphone",Keys.ENTER);
		String xp="//span[text()='iPhone 15 (128 GB) - Blue']/../../../../div[3]/div[1]/div/div[1]/div[1]/div[1]/a/span/span[2]";
		String price =driver.findElement(By.xpath(xp)).getText();
		System.out.println(price);
		driver.quit();
	}
	

}
