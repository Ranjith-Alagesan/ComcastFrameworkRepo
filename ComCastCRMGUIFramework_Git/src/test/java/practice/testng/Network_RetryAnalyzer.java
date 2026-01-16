package practice.testng;

import org.testng.Assert;
import org.testng.annotations.Test;

public class Network_RetryAnalyzer {
	@Test(retryAnalyzer = com.comcast.crm.generic.listenerUtility.RetryListenerImp.class)
	public void activateSim() {
		System.out.println("execute activateSim");
		Assert.assertEquals("","Login");
		System.out.println("Step-1");
		System.out.println("Step-2");
		System.out.println("Step-3");
		System.out.println("Step-4");
	}

}
