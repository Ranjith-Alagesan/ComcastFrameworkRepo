package com.crm.comcast.orgtest_using_testng;

import org.testng.annotations.Test;

import com.crm.generic.baseutility.BaseClass;


public class Create_Sample_OrgTest extends BaseClass {
	@Test
	public void createOrgTest() {
		System.out.println("execute createOrgTest & verify");
	}
	@Test
	public void createOrgwithIndustries() {
		System.out.println("execute createOrgWithIndustries & verify");
	}

}
