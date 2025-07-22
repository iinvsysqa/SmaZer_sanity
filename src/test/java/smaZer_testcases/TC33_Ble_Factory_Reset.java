package smaZer_testcases;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pages.AddDevicePage;
import pages.DeviceMenuPage;
import pages.HomePage;
import pages.LandingPage;
import pages.OtpPage;
import pages.SignInPage;
import pages.SignUpPage;
import pages.StoreLogPage;
import utils.logReadandWrite;
import wrappers.MobileAppWrappers;

public class TC33_Ble_Factory_Reset extends MobileAppWrappers {

	LandingPage landingpage;
	SignInPage loginpage;
	HomePage homepage;
	OtpPage otppage;
	AddDevicePage adddevicepage;
	DeviceMenuPage devicemenupage;
	SignUpPage signuppage;
	
	 StoreLogPage logpage;   
	 
	 
	 @BeforeClass   
	public void startTestCase() {
		testCaseName = "TC33_Ble Without Router Factory Reset";
		testDescription = "Paired with device Ble without router mode and try to do factory reset using via app";
	}



@Test(priority = 32)
public void TC33_Ble_Without_Router_Factory_Reset() throws Exception {
	initAndriodDriver();
	pairBlewithoutRouter();
}



public void pairBlewithoutRouter() throws Exception {
	adddevicepage= new AddDevicePage(driver);
	homepage = new HomePage(driver);
	devicemenupage= new DeviceMenuPage(driver);
	logpage= new StoreLogPage(driver);
	
	logReadandWrite readwrite = logReadandWrite.getInstance(loadProp("COM"));
	try {
	readwrite.openPort();
	
	adddevicepage.pair(1);
	adddevicepage.clickNextButtonsZephyrInfo();
	adddevicepage.clickBleokbutton();
//	adddevicepage.checkdevicedetailstoast();	
	adddevicepage.clickSubmitButtonDeviceSetting();
	
	homepage.clickONOFFButton();
	Thread.sleep(2000);
	
	homepage.clickONOFFButton();
	Thread.sleep(2000);
	
	homepage.killandopen();
	homepage.clickMenuBarButton();
	devicemenupage.clickDeviceSettingsButton();
	devicemenupage.clickResetDeviceButton();
	devicemenupage.clickResetConfirmationYesButton();
	adddevicepage.checkdeviceresettoast();
	devicemenupage.AddDevicePagedisplayed();
	 readwrite.closePort();
	}
	catch (Exception e) {
		readwrite.closePort();
		logpage.CollectLogOnFailure(testCaseName,testDescription);
		fail(e);
	}
}

}
