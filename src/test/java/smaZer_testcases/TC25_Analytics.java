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

public class TC25_Analytics  extends MobileAppWrappers {

	HomePage homepage;
	AddDevicePage adddevicepage;
	DeviceMenuPage devicemenupage;
	Analytics analyticspage;
	
	 StoreLogPage logpage;  
	 
	 @BeforeClass   
	public void startTestCase() {
		testCaseName = "TC_25_Analytics_SmartConfig Mode";
		testDescription = "Pairing mode=Smartconfig <br> Connectivity :Remote <br>Turn on device for 5min using relay  <br> check for analytivs value<br>Energy duration and Energy used for 5 min should update";
	}
	
	@Test(priority = 24)
	public void TC_25_Analytics_SmartConfig_Mode() throws Exception {
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
		analyticspage.navigatehomepage();
		Thread.sleep(2000);
		homepage.clickONOFFButton();
		Thread.sleep(1*60*1000);
		homepage.clickONOFFButton();
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
