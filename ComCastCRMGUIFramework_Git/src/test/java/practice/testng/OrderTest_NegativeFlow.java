package practice.testng;

import org.testng.annotations.Test;

public class OrderTest_NegativeFlow {
	@Test
	public void createOrderTest() {
		System.out.println("Execute Create Order Test ==>123");
		String s=null;
		System.out.println(s.equals("123"));
	}
	@Test(dependsOnMethods = "createOrderTest")
	public void billingAnOrderTest() {
		System.out.println("Execute billingAnOrderTest ==>123");
	}

}
