
package smaZer_testcases;

import org.testng.annotations.BeforeClass;
import pages.AddDevicePage;
import pages.DeviceMenuPage;
import pages.HomePage;
import pages.LandingPage;
import pages.OTA_Status_monitor;
import pages.OtpPage;
import pages.SignInPage;
import pages.SignUpPage;
import pages.SmaZer_info_Page;
import pages.StoreLogPage;
import utils.logReadandWrite;
import wrappers.MobileAppWrappers;


public class  TC35_OTA_TC_01_BLE extends MobileAppWrappers {

	LandingPage landingpage;
	SignInPage loginpage;
	HomePage homepage;
	OtpPage otppage;
	AddDevicePage adddevicepage;
	DeviceMenuPage devicemenupage;
	SmaZer_info_Page szephyrinfoPage;
	OTA_Status_monitor ota_Status_monitor;
	SignUpPage signuppage;
	
	 StoreLogPage logpage;
	 
	 @BeforeClass   
	public void startTestCase() {
		testCaseName = "TC35_OTA_Update_check_Ble";
		testDescription = "Paired with device BLE without Router mode and do OTA update";
	}
	

//	@Test(priority = 34)
	public void removerepair() throws Exception {
		login();
		for(int i=0;i<1;i++) {
		pairBlewithoutRouter();}
	}

	public void login() {
		loginpage = new SignInPage(driver);
		landingpage = new LandingPage(driver);
		otppage = new OtpPage(driver);
		ota_Status_monitor=new OTA_Status_monitor(driver);
		signuppage = new SignUpPage(driver);
		
		landingpage.clickSignInButton();
		signuppage.enterEmailId("varadharajanram95@gmail.com");
		loginpage.clickSignInButton();
		otppage.enterOTPField1("1");
		otppage.enterOTPField2("2");
		otppage.enterOTPField3("3");
		otppage.enterOTPField4("4");
		otppage.submitButton();
		
	}
	
	public void pairBlewithoutRouter() throws Exception {
		adddevicepage= new AddDevicePage(driver);
		homepage = new HomePage(driver);
		devicemenupage= new DeviceMenuPage(driver);
		szephyrinfoPage= new SmaZer_info_Page(driver);
		logpage= new StoreLogPage(driver);
		
		logReadandWrite readwrite = logReadandWrite.getInstance("COM4");
		try {
		readwrite.openPort();
		
		adddevicepage.clickAddDeviceButton();
		adddevicepage.checkBoxPairing();
		adddevicepage.nextButtonPairing();
		adddevicepage.startPairingButton();
		adddevicepage.locationPopUpPermission();
		adddevicepage.nearByPermission();
		adddevicepage.clickWifiCancelButton();
		//adddevicepage.enterWiFiPassword("12345678908");
		//adddevicepage.clickEnterButton();
		adddevicepage.clickNextButtonsZephyrInfo();
		adddevicepage.clickSubmitButtonDeviceSetting();
		
		//for(int i=0;i<2;i++) {
		//homepage.clickONOFFButton();
		//Thread.sleep(1000);
		//}
		
		homepage.clickMenuBarButton();
		devicemenupage.clickszephyr_info_button();
		szephyrinfoPage.clickdownload_firmwareButton();
		szephyrinfoPage.clickfirmware_update();
		szephyrinfoPage.clickfirmware_update_popup_button();
		szephyrinfoPage.clickOTA_OK_Button();
		ota_Status_monitor.waitforstatusbar();
		szephyrinfoPage.clickbackButton();
	    homepage.clickMenuBarButton();
		devicemenupage.clickDeviceSettingsButton();
		devicemenupage.clickResetDeviceButton();
		devicemenupage.clickResetConfirmationYesButton();
		
	    readwrite.closePort();
		}
		catch (Exception e) {
			readwrite.closePort();
			logpage.CollectLogOnFailure();
			fail(e);
		}
	

}}
