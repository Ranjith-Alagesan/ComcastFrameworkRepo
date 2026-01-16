package practice.testng;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class CreateContact_DP_Test2 {
	@Test(dataProvider = "getData")
	public void createContactTest(String firstName,String lastName, long phoneNum) {
		System.out.println("FN: "+firstName+", LastName: "+lastName+" PhoneNum:"+phoneNum);
	}
	@DataProvider
	public Object[][] getData(){
		Object[][] objArr=new Object[3][3];
		objArr[0][0]="Ranjith";
		objArr[0][1]="M A";
		objArr[0][2]=9876543210l;
		objArr[1][0]="Sam";
		objArr[1][1]="K";
		objArr[1][2]=9876543211l;
		objArr[2][0]="Kamal";
		objArr[2][1]="K M";
		objArr[2][2]=9876543212l;
		return objArr;
		
	}

}
