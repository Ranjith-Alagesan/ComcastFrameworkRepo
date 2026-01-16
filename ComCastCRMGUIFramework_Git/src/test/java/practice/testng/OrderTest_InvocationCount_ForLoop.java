package practice.testng;

import org.testng.annotations.Test;

public class OrderTest_InvocationCount_ForLoop {
	/*for loop treat all 10Tc as single Tc and give result*/
	@Test
	public void createOrderTest() {
		//negative flow: if one iteration get fail entire test case gets fail
		for(int i=0;i<10;i++) {
			System.out.println("Execute Create Order Test ==>123");
		String s=null;
		System.out.println(s.equals("1"));
		}
	}
	@Test
	public void billingAnOrderTest() {
		//positive flow
		for(int i=0;i<10;i++) {
			System.out.println("Execute billingAnOrderTest ==>123");
		}
	}

}
