package practice.testng;

import org.testng.annotations.Test;

public class ContactTest_dependsOnMethod {
	@Test
	public void createContactTest() {
		System.out.println("execute createContact with-->HDFC");
	}
	@Test(dependsOnMethods = "createContactTest")
	public void modifyContactTest() {
		System.out.println("execute modifyContact HDFC==>ICICI");
	}
	@Test(dependsOnMethods = "modifyContactTest")
	public void deleteContactTest() {
		System.out.println("execute deleteContact ICICI");
	}

}
