package practice.testng;

import org.testng.annotations.Test;

public class OrderTest_PositiveFlow {
	@Test
	public void createOrderTest() {
		System.out.println("Execute Create Order Test ==>123");
	}
	@Test(dependsOnMethods = "createOrderTest")
	public void billingAnOrderTest() {
		System.out.println("Execute billingAnOrderTest ==>123");
	}

}
