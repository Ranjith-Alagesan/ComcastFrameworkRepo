package com.comcast.crm.generic.listenerUtility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.logging.FileHandler;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.crm.generic.baseutility.BaseClass;

public class ListenerImpClass implements ITestListener,ISuiteListener{
	@Override
	public void onTestStart(ITestResult result) {
		System.out.println(result.getName()+"start");
	}
	@Override
	public void onTestSuccess(ITestResult result) {
		System.out.println(result.getName()+"success");
	}
	@Override
	public void onTestFailure(ITestResult result) {
		String testName=result.getName();
		TakesScreenshot tk=(TakesScreenshot)BaseClass.sdriver;
		File src=tk.getScreenshotAs(OutputType.FILE);
		//File dest=new File("./Screenshot/test.png");
		//to prevent overriding we take ss with testName
		
		//time based screenshot
		String time=new Date().toString().replace(" ","_").replace(":","_");		
		File dest=new File("./Screenshot/"+testName+"+"+time+".png");
		try {
			org.openqa.selenium.io.FileHandler.copy(src, dest);
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
	@Override
	public void onTestSkipped(ITestResult result) {
		System.out.println(result.getName()+"skipped");
	}
	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
	
	}
	@Override
	public void onTestFailedWithTimeout(ITestResult result) {
		
	}
	@Override
	public void onStart(ITestContext context) {
		
	}
	@Override
	public void onFinish(ITestContext context) {
		
	}
	

}
