package com.crm.comcast.contacttest_using_testng;

import org.testng.annotations.Test;

import com.crm.generic.baseutility.BaseClass;


public class Create_Sample_ContactTest extends BaseClass {
	@Test
	public void createContact() {
		System.out.println("execute CreateContact & verify");
	}
	@Test
	public void createContactWithDate() {
		System.out.println("execute CreateContactWithDate & verify");
	}

}
