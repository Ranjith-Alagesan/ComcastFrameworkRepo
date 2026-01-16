package practice.pom.repository;

import java.lang.reflect.Method;

import org.testng.annotations.Test;

public class HomePageVerificationTest {
	@Test
	public void homePageTest(Method mtd) {
		System.out.println(mtd.getName()+"Test Start");
		String expPage="Home";
	}

}
