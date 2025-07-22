package smaZer_testcases;

import static org.testng.Assert.fail;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.AddDevicePage;
import pages.DeviceMenuPage;
import pages.HomePage;
import utils.logReadandWrite;
import wrappers.MobileAppWrappers;


public class TC22_DeviceSettings extends MobileAppWrappers {

		HomePage homepage;
		AddDevicePage adddevicepage;
		DeviceMenuPage devicemenupage;
	
	
	@BeforeClass
	public void startTestCase() {
		testCaseName = "TC22_DeviceSettings_Pairing Time Settings change";
		testDescription = "Pairing Time Change the Device Setting Value And Check MenuBar Device Settings Page Reflect The Same";
	}
	

	@Test(priority = 21,groups = {"skip"})
	public void TC22_DeviceSettings_Pairing_Time_Settings_change() throws Exception {
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
		
		adddevicepage.pair(5);
		adddevicepage.clickNextButtonsZephyrInfo();
		adddevicepage.clickBleokbutton();
//		adddevicepage.checkdevicedetailstoast();
		devicemenupage.clickPairingTimeQuietLEDEnable();
		devicemenupage.clickInfinitePowerToggle();
		devicemenupage.clickHoursPlusButton();
		Thread.sleep(1000);
		adddevicepage.clickSubmitButtonDeviceSetting();
		adddevicepage.checkdevicesettingstoast();
		
		for(int i=0;i<2;i++) {
		homepage.clickONOFFButton();
		Thread.sleep(1000);
		}
		
//Pairing Time Changed Data Should Reflected In Device Settings Page Duration For ON	
		homepage.clickMenuBarButton();
		devicemenupage.clickDeviceSettingsButton();
		devicemenupage.clickDurationForONButton();
		adddevicepage.Hourstextbox("1");
	    adddevicepage.Minutestextbox("20");
	    adddevicepage.ClickokdurationON();
//		devicemenupage.clickDeviceSettingsBackButton();
		devicemenupage.clickDeviceSettingsBackButton();
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
