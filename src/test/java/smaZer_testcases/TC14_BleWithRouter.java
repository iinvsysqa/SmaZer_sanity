
package smaZer_testcases;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.AddDevicePage;
import pages.DeviceMenuPage;
import pages.HomePage;
import pages.LandingPage;
import pages.OTA_Status_monitor;
import pages.OtpPage;
import pages.SignInPage;
import pages.SignUpPage;
import pages.StoreLogPage;
import utils.logReadandWrite;
import wrappers.MobileAppWrappers;


public class  TC14_BleWithRouter extends MobileAppWrappers {

	LandingPage landingpage;
	SignInPage loginpage;
	HomePage homepage;
	OtpPage otppage;
	AddDevicePage adddevicepage;
	DeviceMenuPage devicemenupage;
	OTA_Status_monitor ota_Status_monitor;
	SignUpPage signUppage;	
	StoreLogPage logpage;

	
	 @BeforeClass   
	public void startTestCase() {
		testCaseName = "TC14_BlewithRouter_CONNECTIVITY";
		testDescription = "CONNECTIVITY_MOD_2_TC_02-BLE connectivity establishment"+"<br>"+"CONNECTIVITY_MOD_2_TC_03-APP kill and re Open"+"<br>"+"CONNECTIVITY_MOD_1_TC_04-5 times App ON/OFF";
	}
	
	
	@Test(priority = 13)
	public void TC14_BlewithRouter_CONNECTIVITY() throws Exception {
		initAndriodDriver();
		pairBlewithRouter();
	}


	
	public void pairBlewithRouter() throws Exception {
		adddevicepage= new AddDevicePage(driver);
		homepage = new HomePage(driver);
		devicemenupage= new DeviceMenuPage(driver);
		logpage= new StoreLogPage(driver);

		
		logReadandWrite readwrite = logReadandWrite.getInstance(loadProp("COM"));
		try {
		readwrite.openPort();
		
		adddevicepage.pair(2);
		adddevicepage.clickNextButtonsZephyrInfo();
		adddevicepage.clickBleokbutton();
//		adddevicepage.checkdevicedetailstoast();
		adddevicepage.clickSubmitButtonDeviceSetting();
		adddevicepage.checkdevicesettingstoast();
		
		adddevicepage.bleConnectivityCheck();
		
		for(int i=0;i<2;i++) {
		homepage.clickONOFFButton();
		Thread.sleep(1000);
		}
		homepage.disableBLE();
		Thread.sleep(5000);
		adddevicepage.staConnectivityCheck();
		//homepage.enableBLE();
		for(int i=0;i<5;i++) {
			homepage.clickONOFFButton();
		}
		
		homepage.getCurrentvalue();
		homepage.getVoltvalue();
		homepage.getPowervalue();
		
		
		
		//////////////
		homepage.disableBLE();
		
		Thread.sleep(5000);
		adddevicepage.staConnectivityCheck();
		homepage.clickONOFFButton();
		homepage.clickONOFFButton();
		
		homepage.killandopen();
		adddevicepage.ClickOkButtonBLEpopUP();
		homepage.verifyusername();
		Thread.sleep(5000);
		adddevicepage.staConnectivityCheck();
		homepage.clickONOFFButton();
		homepage.clickONOFFButton();
		homepage.getCurrentvalue();
		homepage.getVoltvalue();
		homepage.getPowervalue();
		
		
		/////////////////////
		
		homepage.enableBLE();
		Thread.sleep(5000);
		adddevicepage.bleConnectivityCheck();
		for(int i=0;i<2;i++) {
			homepage.clickONOFFButton();
			Thread.sleep(1000);
			}
	    homepage.disableWIFI();
	    Thread.sleep(5000);
	    adddevicepage.bleConnectivityCheck();
	    for(int i=0;i<2;i++) 
	    {
			homepage.clickONOFFButton();
			Thread.sleep(1000);
			}
	    homepage.getCurrentvalue();
		homepage.getVoltvalue();
		homepage.getPowervalue();
	  ///CONNECTIVITY_MOD_2_TC_5--   Check Kill and Open //
	    
		homepage.killandopen();
		Thread.sleep(5000);
		adddevicepage.bleConnectivityCheck();
		homepage.clickONOFFButton();
		Thread.sleep(3000);
		homepage.clickONOFFButton();
	 ///CONNECTIVITY_MOD_2_TC_6  ---- 5 Times ON/OFF//	
		 for(int i=0;i<5;i++) 
		    {
				homepage.clickONOFFButton();
				Thread.sleep(3000);
				}
		 homepage.getCurrentvalue();
			homepage.getVoltvalue();
			homepage.getPowervalue();
			homepage.enableWiFi();
		homepage.WifiSwitch(loadProp("WIFINAME"), loadProp("WIFIPASSWORD"));
		
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

