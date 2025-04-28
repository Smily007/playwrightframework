package com.tests;

import org.testng.annotations.Test;
import org.testng.Assert;
import com.base.Baseclass;
import com.pages.LoginPage;
import com.utilities.Testdata;
import com.utilities.UpdateExcel;

public class LoginTest extends Baseclass {

	UpdateExcel excel = new UpdateExcel();
	String sheetName = "Enquiry";

//	// TC_001
//	@Epic("Login")
//	@Feature("UserLogin")
//	@Story("User should be login successfully.")
//	@Description("Verify that edit login functionality is working properly with valid credentials.")
	@Test(enabled = true)
	public void testValidLogin() throws InterruptedException {
		setup("chromium");
		navigate(Testdata.devUrl);
		LoginPage loginPage = new LoginPage(page);
		loginPage.login("student", "Password123");
		Thread.sleep(5000);
		try {
			Assert.assertTrue(loginPage.isLoginSuccessful(), "Logout button should be visible after successful login");
			System.out.println("✅ testValidLogin passed.");
			excel.updateTestResult(sheetName, "TC_001", "Pass", "LoginSuccessFull");

		} catch (AssertionError e) {
			captureScreen(page, "Login");
			System.out.println("❌ testValidLogin failed: " + e.getMessage());
			excel.updateTestResult(sheetName, "TC_001", "Fail", e.getMessage());
			throw e;
		}
	}
}