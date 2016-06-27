package com.itc.amazon.tests;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

public class TestClassBaseTest {

	@Parameters({ "url", "browser", "customer", "mode" })
	@BeforeSuite(alwaysRun = true)
	public void initialize(String url, String browser, String customer,
			String mode) {

		System.out.println(url + browser + customer + mode);
	}

	@AfterSuite(alwaysRun = true)
	public void down(String url, String browser, String customer,
			String mode) {

		System.out.println(url + browser + customer + mode);
	}
}
