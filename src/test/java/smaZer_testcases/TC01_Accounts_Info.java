package smaZer_testcases;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.AccountsInfoPage;
import pages.AddDevicePage;
import pages.DeviceMenuPage;
import pages.HomePage;
import pages.LandingPage;
import pages.SignInPage;
import utils.logReadandWrite;
import pages.OtpPage;
import wrappers.MobileAppWrappers;



public class TC01_Accounts_Info extends MobileAppWrappers {

	LandingPage landingpage;
	SignInPage loginpage;
	HomePage homepage;
	OtpPage otppage;
	AddDevicePage adddevicepage;
	AccountsInfoPage accountinfopage;
	DeviceMenuPage devicesettingpage;


	@BeforeClass
	public void startTestCase() {
		testCaseName = "TC_01_Account_Info_Changes";
		//check login username and Accounts info username are same or not 
		//check for language selection 
		//pair with device try to del account and check for popup and try to remove device and try to del acnt and check add device page .

		testDescription = "Change language and check changed language ,before removing device try to delete account then try to remove device and delete account";
	}


	@Test(priority = 0)
	public void TC01_Accounts_Info_Check() throws Exception {
		initAndriodDriver();
		pairBlewithoutRouter();
	}

	static String wifiName="TP-Link_6D38_With_Internet";
	public void pairBlewithoutRouter() throws Exception {
		adddevicepage= new AddDevicePage(driver);
		homepage = new HomePage(driver);
		accountinfopage= new AccountsInfoPage(driver);
		devicesettingpage= new DeviceMenuPage(driver);

		logReadandWrite readwrite = logReadandWrite.getInstance(loadProp("COM"));
		try {
			readwrite.openPort();
			     
			adddevicepage.pair(1);
			adddevicepage.clickNextButtonsZephyrInfo();
//			adddevicepage.checkdevicedetailstoast();
			adddevicepage.clickBleokbutton();
			adddevicepage.clickSubmitButtonDeviceSetting();
			adddevicepage.checkdevicesettingstoast();




			homepage.clickONOFFButton();
			Thread.sleep(2000);
			
			homepage.clickONOFFButton();
			Thread.sleep(2000);
			
			accountinfopage.checkAccountsinfousername_email_Language();

			readwrite.closePort();
		}
		catch (Exception e) {
			readwrite.closePort();
			fail(e);
		}
	}

}	

