package practice.testng;

import org.testng.annotations.Test;

public class ContactTest_Priority {
	/* without giving priority it will execute in alphabetical order
	 * 
	 * op:execute createContact
		  execute deleteContact
		  execute modifyContact
	 * 
	 * */
	@Test(priority=1)
	public void createContactTest() {
		System.out.println("execute createContact with-->HDFC");
	}
	@Test(priority=2)
	public void modifyContactTest() {
		//refer notes under disv of priority
		System.out.println("execute query insert contact in DB==>HDFC");
		System.out.println("execute modifyContact HDFC==>ICICI");
	}
	@Test(priority=3)
	public void deleteContactTest() {
		System.out.println("execute query insert contact in DB==>HDFC");
		System.out.println("execute deleteContact");
	}

}
