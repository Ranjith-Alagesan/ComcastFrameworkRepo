package practice.testng;

import org.testng.annotations.Test;

public class OrderTest_InvocationCount {
	@Test(invocationCount = 10)
	public void createOrderTest() {
		System.out.println("Execute Create Order Test ==>123");
	}
	@Test(enabled=false)
	public void billingAnOrderTest() {
		System.out.println("Execute billingAnOrderTest ==>123");
	}

}
