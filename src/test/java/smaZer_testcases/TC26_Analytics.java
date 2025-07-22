package smaZer_testcases;

import static org.testng.Assert.fail;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pages.AddDevicePage;
import pages.Analytics;
import pages.DeviceMenuPage;
import pages.HomePage;
import pages.StoreLogPage;
import utils.logReadandWrite;
import wrappers.MobileAppWrappers;

public class TC26_Analytics  extends MobileAppWrappers {

	HomePage homepage;
	AddDevicePage adddevicepage;
	DeviceMenuPage devicemenupage;
	Analytics analyticspage;
	
	 StoreLogPage logpage;  
	 
	 @BeforeClass   
	public void startTestCase() {
		testCaseName = "TC_26_Analytics_Smartconfig_App close";
		testDescription = "Pairing mode=Smartconfig <br> Connectivity :Remote <br>After connectivity established close application<br>Turn on device for 5min using device button  <br> check for analytivs value<br>Energy duration and Energy used for 5 min should update";
	}
	
	@Test(priority = 25)
	public void TC_26_Analytics_Smartconfig_App_close() throws Exception {
		initAndriodDriver();
		pairBlewithoutRouter();
	}
	
	public void pairBlewithoutRouter() throws Exception {
		adddevicepage= new AddDevicePage(driver);
		homepage = new HomePage(driver);
		devicemenupage= new DeviceMenuPage(driver);
		analyticspage= new Analytics(driver);
		logpage= new StoreLogPage(driver);
		
		logReadandWrite readwrite = logReadandWrite.getInstance(loadProp("COM"));
		try {
		readwrite.openPort();
		
		adddevicepage.pair(3);
		adddevicepage.clickNextButtonsZephyrInfo();
		adddevicepage.clickBleokbutton();
//		adddevicepage.checkdevicedetailstoast();
		adddevicepage.clickSubmitButtonDeviceSetting();
		
		turnOffBT();
		homepage.WifiSwitch(loadProp("REMOTEWIFINAME"),loadProp("REMOTEWIFIPASSWORD"));
		adddevicepage.remoteConnectivityCheck();
		
		
		analyticspage.navigateAnalyticsPage();
		analyticspage.getenergydurationvalue();
		closeApp();
		readwrite.write("button_press\r");
		Thread.sleep(1*60*1000);
		readwrite.write("button_press\r");
		openapp();
		adddevicepage.ClickOkButtonBLEpopUP();
		homepage.HomepageDevicenameCheck();
		turnOffBT();
		
		analyticspage.navigateAnalyticsPage();
		analyticspage.checkenrgyduration(1);
		
				
		
		
		}
		catch (Exception e) {
			readwrite.closePort();
			logpage.CollectLogOnFailure(testCaseName,testDescription);
			fail(e);
		}
	}
}
