package practice.testng;

import java.lang.reflect.Method;

import org.testng.Assert;
import org.testng.annotations.Test;

public class HomeSampleTest_Hard_Assert {
	@Test
	public void homePageTest(Method mtd) {
		System.out.println(mtd.getName()+" Test Start");
		System.out.println("Step-1");
		System.out.println("Step-2");
		Assert.assertEquals("Home", "Home Page");
		System.out.println("Step-3");
		Assert.assertEquals("Home-CRM", "Home-CRM");
		System.out.println("Step-4");
		System.out.println(mtd.getName()+" Test End");
	}
	@Test
	public void verifyLogoHomePageTest(Method mtd) {
		System.out.println(mtd.getName()+" Test Start");
		System.out.println("Step-1");
		System.out.println("Step-2");
		Assert.assertTrue(true);
		System.out.println("Step-3");
		Assert.assertEquals("Home-CRM", "Home-CRM");
		System.out.println("Step-4");
		System.out.println(mtd.getName()+" Test End");
		
	}

}
