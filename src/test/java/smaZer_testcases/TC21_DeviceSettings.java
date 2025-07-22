package smaZer_testcases;

import static org.testng.Assert.fail;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.AddDevicePage;
import pages.DeviceMenuPage;
import pages.HomePage;
import utils.logReadandWrite;
import wrappers.MobileAppWrappers;


public class TC21_DeviceSettings extends MobileAppWrappers {

	HomePage homepage;
	AddDevicePage adddevicepage;
	DeviceMenuPage devicemenupage;
	
	
	@BeforeClass
	public void startTestCase() {
		testCaseName = "TC21_DeviceSettings_LED Quite Mode";
		testDescription = "Enable The Quite LED Mode and Do Relay ON&OFF Check LED Working Or Not";
	}
	

	@Test(priority = 20)
	public void TC21_DeviceSettings_LED_Quite_Mode() throws Exception {
		initAndriodDriver();
		pairBlewithoutRouter();
	}

	
	public void pairBlewithoutRouter() throws Exception {
		adddevicepage= new AddDevicePage(driver);
		homepage = new HomePage(driver);
		devicemenupage= new DeviceMenuPage(driver);
		

		logReadandWrite readwrite = logReadandWrite.getInstance(loadProp("COM"));
		try {
		readwrite.openPort();
		
		adddevicepage.pair(1);
		adddevicepage.clickNextButtonsZephyrInfo();
		adddevicepage.clickBleokbutton();
//		adddevicepage.checkdevicedetailstoast();
		adddevicepage.clickSubmitButtonDeviceSetting();
		adddevicepage.checkdevicesettingstoast();
		
		
		for(int i=0;i<2;i++) {
		homepage.clickONOFFButton();
		Thread.sleep(2000);
		}
		
//LED Working Properly	
		    
		homepage.clickMenuBarButton();
		devicemenupage.clickDeviceSettingsButton();
			
			
//Enable The Quiet LED Mode Test Case
		devicemenupage.clickQuietLEDToggleForOn();
		devicemenupage.clickDeviceSettingsBackButton();
			
//Relay ON/OFF Time Device LED Was Not Working Expected Scenario 
		for(int i=0;i<4;i++) {
			homepage.clickONOFFButton();
			Thread.sleep(1000);
			}
			
//Disable The Quiet LED Mode Test Case
		homepage.clickMenuBarButton();
		devicemenupage.clickDeviceSettingsButton();
		devicemenupage.clickQuietLEDToggleForOff();
		devicemenupage.clickDeviceSettingsBackButton();
		
//Relay ON/OFF Time Device LED Was Working Expected Scenario 
		for(int i=0;i<4;i++) {
			homepage.clickONOFFButton();
			Thread.sleep(1000);
			}
			
		homepage.clickMenuBarButton();
		devicemenupage.clickMenuBarRemoveDevice();
		devicemenupage.clickRemoveDevicePopupNoButton();
		homepage.clickMenuBarButton();
		devicemenupage.clickMenuBarRemoveDevice();
		devicemenupage.clickRemoveDevicePopupYesButton();
		adddevicepage.checkdeviceremovedtoast();
		devicemenupage.AddDevicePagedisplayed();
		readwrite.closePort();
		}
		catch (Exception e) {
			readwrite.closePort();
			fail(e);
		}

	}

}


