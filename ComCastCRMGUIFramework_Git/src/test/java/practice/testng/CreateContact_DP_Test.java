package practice.testng;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class CreateContact_DP_Test {
	@Test(dataProvider = "getData")
	public void createContactTest(String firstName,String lastName) {
		System.out.println("FN: "+firstName+", LastName: "+lastName);
	}
	@DataProvider
	public Object[][] getData(){
		Object[][] objArr=new Object[3][2];
		objArr[0][0]="Ranjith";
		objArr[0][1]="M A";
		objArr[1][0]="Sam";
		objArr[1][1]="K";
		objArr[2][0]="Kamal";
		objArr[2][1]="K M";
		return objArr;
		
	}

}
