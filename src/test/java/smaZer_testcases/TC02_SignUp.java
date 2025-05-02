package smaZer_testcases;

import static org.testng.Assert.fail;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pages.AddDevicePage;
import pages.DeviceMenuPage;
import pages.HomePage;
import pages.LandingPage;
import pages.OtpPage;
import pages.SignInPage;
import pages.SignUpPage;
import utils.GetAppLog;
import utils.PassSTComment;
import utils.logReadandWrite;
import wrappers.MobileAppWrappers;

public class TC02_SignUp extends MobileAppWrappers {

	LandingPage landingpage;
	SignInPage loginpage;
	HomePage homepage;
	OtpPage otppage;
	AddDevicePage adddevicepage;
	DeviceMenuPage devicemenupage;
	SignUpPage signuppage;

	@BeforeClass
	public void startTestCase() {
		testCaseName = "TC02_Sign Up with already registered username";
		testDescription = "Try to Sign Up with already registered username";
	}


	@Test(priority = 1)
	public void signUp() throws Exception {

		initAndriodDriver();
		loginpage = new SignInPage(driver);
		landingpage = new LandingPage(driver);
		otppage = new OtpPage(driver);
		signuppage =new SignUpPage(driver);
		logReadandWrite readwrite = logReadandWrite.getInstance(loadProp("COM"));
		try {
		readwrite.openPort();

		
		signuppage.uninstall_reinstall();
		Thread.sleep(5000);
		landingpage.clickSignUpLink();

		
		signuppage.enterUserName(loadProp("USERNAME"));
		signuppage.enterEmailId(loadProp("EMAILID"));
		signuppage.clickSignUpTCCheckBox();
		signuppage.clickSignUpButton();
		signuppage.checkUserNameExistToast("Username and Email ID both are already exists");
		readwrite.closePort();
		}
		catch (Exception e) {
			readwrite.closePort();
			fail(e);
		}

	}

}