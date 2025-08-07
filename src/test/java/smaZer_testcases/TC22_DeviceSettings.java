package smaZer_testcases;

import static org.testng.Assert.fail;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.AddDevicePage;
import pages.Analytics;
import pages.DeviceMenuPage;
import pages.HomePage;
import pages.Schedularpage;
import pages.StoreLogPage;
import utils.logReadandWrite;
import wrappers.MobileAppWrappers;


public class TC22_DeviceSettings extends MobileAppWrappers {

		HomePage homepage;
		AddDevicePage adddevicepage;
		DeviceMenuPage devicemenupage;
		StoreLogPage logpage;   
		Analytics analytics;
		Schedularpage schedulepage;
		
	 @BeforeClass   
	public void startTestCase() {
		testCaseName = "TC22_DeviceSettings_Pairing Time Settings change";
		testDescription = "Pairing Time Change the Device Setting Value And Check MenuBar Device Settings Page Reflect The Same";
	}
	

	@Test(priority = 21)
	public void TC22_DeviceSettings_Pairing_Time_Settings_change() throws Exception {
		initAndriodDriver();
		pairBlewithoutRouter();
	}


	
	public void pairBlewithoutRouter() throws Exception {
		adddevicepage= new AddDevicePage(driver);
		homepage = new HomePage(driver);
		devicemenupage= new DeviceMenuPage(driver);
		logpage= new StoreLogPage(driver);
		analytics=new Analytics(driver);
		schedulepage = new Schedularpage(driver);
		
		logReadandWrite readwrite = logReadandWrite.getInstance(loadProp("COM"));
		
		try {
		readwrite.openPort();
		
		adddevicepage.pair(4);
		adddevicepage.clickNextButtonsZephyrInfo();
		adddevicepage.clickBleokbutton();
//		adddevicepage.checkdevicedetailstoast();
		devicemenupage.clickPairingTimeQuietLEDEnable();
		devicemenupage.clickInfinitePowerToggle();
		devicemenupage.clickHoursPlusButton();
		Thread.sleep(1000);
		adddevicepage.clickSubmitButtonDeviceSetting();
		adddevicepage.checkdevicesettingstoast();
		
		
		
//Pairing Time Changed Data Should Reflected In Device Settings Page Duration For ON	
		homepage.clickMenuBarButton();
		devicemenupage.clickDeviceSettingsButton();
		
		devicemenupage.checkDurationforOnDefautvalue_devicesettings();
		devicemenupage.ReduceDurationminbutton();
		
		analytics.navigateAnalyticsPage();
		analytics.getenergydurationvalue();
		schedulepage.backToHomepage();
		
			homepage.clickONOFFButton();
			Thread.sleep(1*1000*60);			
		analytics.navigateAnalyticsPage();
		analytics.checkenrgyduration(1);//schedule duration value is (1) and this value should be same as no.of schedule kept 
		schedulepage.backToHomepage();
		
//		devicemenupage.clickDeviceSettingsBackButton();
		
//		high voltage 
//		low voltage
		homepage.clickMenuBarButton();
		devicemenupage.clickDeviceSettingsButton();
		devicemenupage.checkLowVoltDefautvalue_devicesettings();
		devicemenupage.backnavigation();
		homepage.clickMenuBarButton();
		devicemenupage.clickDeviceSettingsButton();
		devicemenupage.checkHighVoltDefautvalue_devicesettings();
		devicemenupage.backnavigation();
		
		
		
		
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
			logpage.CollectLogOnFailure(testCaseName,testDescription);
			fail(e);
		}
	}

}
