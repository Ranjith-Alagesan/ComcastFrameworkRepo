package practice.testng;

import java.lang.reflect.Method;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

public class HomeSampleTest_Reporter_Log_ExecutionTime {
	@Test
	public void homePageTest(Method mtd) {
		Reporter.log(mtd.getName()+" Test Start");
		Reporter.log("Step-1",true);
		Reporter.log("Step-2",true);
		SoftAssert assertobj=new SoftAssert();
		assertobj.assertEquals("Home", "Home");
		Reporter.log("Step-3",true);
		assertobj.assertEquals("Home-CRM", "Home-CRM");
		Reporter.log("Step-4",true);
		assertobj.assertAll();
		Reporter.log(mtd.getName()+" Test End");
		
	}
	@Test
	public void verifyLogoHomePageTest(Method mtd) {
		Reporter.log(mtd.getName()+" Test Start");
		Reporter.log("Step-1",true);
		Reporter.log("Step-2",true);
		SoftAssert assertobj=new SoftAssert();
		assertobj.assertTrue(true);
		Reporter.log("Step-3",true);
		assertobj.assertEquals("Home-CRM", "Home-CRM");
		Reporter.log("Step-4",true);
		Reporter.log(mtd.getName()+" Test End");
		
		
	}

}
