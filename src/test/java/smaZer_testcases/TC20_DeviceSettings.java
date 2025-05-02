package smaZer_testcases;

import static org.testng.Assert.fail;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.AddDevicePage;
import pages.DeviceMenuPage;
import pages.HomePage;
import utils.logReadandWrite;
import wrappers.MobileAppWrappers;


public class TC20_DeviceSettings extends MobileAppWrappers {

	HomePage homepage;
	AddDevicePage adddevicepage;
	DeviceMenuPage devicemenupage;
	
	
	@BeforeClass
	public void startTestCase() {
		testCaseName = "TC20_DeviceSettings_Remove Router";
		testDescription = "Remove the Added Router Details Test Case";
	}
	

	@Test(priority = 19)
	public void removerepair() throws Exception {
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
		
		adddevicepage.pair(2);
		adddevicepage.clickNextButtonsZephyrInfo();
		adddevicepage.checkdevicedetailstoast();
		adddevicepage.clickSubmitButtonDeviceSetting();
		adddevicepage.checkdevicesettingstoast();
		Thread.sleep(2000);
			homepage.clickMenuBarButton();
			devicemenupage.clickDeviceSettingsButton();
	//Remove Router Test Case	
			
			devicemenupage.removerouter();
			Thread.sleep(5000);
			turnOffBT();
//			Thread.sleep(3000);
//			devicemenupage.shellAllowpopup();
			Thread.sleep(5000);
			turnOnBT();
//			devicemenupage.shellAllowpopup();
			
//			devicemenupage.clickDeviceSettingsButton();
		    Thread.sleep(5000);
		    adddevicepage.bleConnectivityCheck();
		    homepage.getCurrentvalue();
			homepage.getVoltvalue();
			homepage.getPowervalue();
			for(int i=0;i<2;i++) {
				homepage.clickONOFFButton();
				Thread.sleep(1000);
				}
			
			homepage.clickMenuBarButton();
			devicemenupage.clickMenuBarRemoveDevice();
			devicemenupage.clickRemoveDevicePopupNoButton();
			Thread.sleep(1000);
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


