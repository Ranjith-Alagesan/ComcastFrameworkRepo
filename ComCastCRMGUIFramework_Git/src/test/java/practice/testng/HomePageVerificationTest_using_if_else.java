package practice.testng;

import java.lang.reflect.Method;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class HomePageVerificationTest_using_if_else {
	@Test
	public void homePageTest(Method mtd) {
		System.out.println(mtd.getName()+" Test Start");
	//	String expPage="Home";
    	String expPage="Home Page";
    	//dont have capacity to fail test case even it is fail so we go for assertion
		WebDriver driver=new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		driver.get("http://localhost:8888/");
		driver.findElement(By.name("user_name")).sendKeys("admin");
		driver.findElement(By.name("user_password")).sendKeys("admin");
		driver.findElement(By.id("submitButton")).click();
		String actTitle = driver.findElement(By.xpath("//a[contains(text(),'Home')]")).getText();
		if(actTitle.equals(expPage)) {
			System.out.println(expPage+" page is verified == PASS");
		}
		else {
			System.out.println(expPage+" page is NOT  verified == FAIL");
		}
		System.out.println(mtd.getName()+" Test End");
		driver.quit();
	}
	@Test
	public void verifyLogoHome(Method mtd) {
		System.out.println(mtd.getName()+" Test Start");
		WebDriver driver=new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		driver.get("http://localhost:8888/");
		driver.findElement(By.name("user_name")).sendKeys("admin");
		driver.findElement(By.name("user_password")).sendKeys("admin");
		driver.findElement(By.id("submitButton")).click();
		boolean status = driver.findElement(By.xpath("//img[@src='test/logo/vtiger-crm-logo.gif']")).isEnabled();
		if(status) {
			System.out.println("Logo is verified == PASS");
		}
		else {
			System.out.println("Logo is not verified == FAIL");
		}
		driver.quit();
	
	}
}
