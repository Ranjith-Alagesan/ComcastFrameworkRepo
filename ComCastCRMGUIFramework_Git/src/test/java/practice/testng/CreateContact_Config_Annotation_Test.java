package practice.testng;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class CreateContact_Config_Annotation_Test {
	@BeforeSuite
	public void configBS() {
		System.out.println("=== execute BS===");
	}
	@AfterSuite
	public void configAS() {
		System.out.println("=== execute AS ===");
	}
	@BeforeClass
	public void configBC() {
		System.out.println("=== execute BC ===");
	}
	@BeforeMethod
	public void configBM() {
		System.out.println("=== execute BM ===");
	}
	@Test
	public void createContact() {
		System.out.println("=== execute createContact ===");
	}
	@Test
	public void createContactWithDate() {
		System.out.println("=== execute createContactWithDate ===");
	}
	@AfterMethod
	public void configAM() {
		System.out.println("=== execute AM ===");
	}
	@AfterClass
	public void configAC() {
		System.out.println("=== execute AC ===");
	}

}
