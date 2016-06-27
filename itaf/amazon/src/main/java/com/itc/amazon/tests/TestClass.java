package com.itc.amazon.tests;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class TestClass extends TestClassBaseTest {

	@Test
	@Parameters({ "url", "browser", "customer", "mode" })
	public void xml(String url, String browser, String customer, String mode) {

		System.out.println(url + browser + customer + mode);
	}
}
