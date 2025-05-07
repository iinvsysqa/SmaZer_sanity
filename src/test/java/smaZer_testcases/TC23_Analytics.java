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

public class TC23_Analytics  extends MobileAppWrappers {

	HomePage homepage;
	AddDevicePage adddevicepage;
	DeviceMenuPage devicemenupage;
	Analytics analyticspage;
	StoreLogPage logpage; 
	 
	 @BeforeClass   
	public void startTestCase() {
		testCaseName = "TC_23_Analytics_BLE withoutRouter_App Open";
		testDescription = "Pairing mode=Ble without router <br> Connectivity :BLE <br>Turn on device for 5min using relay  <br> check for analytivs value<br>Energy duration and Energy used for 5 min should update";
	}
	
	@Test(priority = 22)
	public void removerepair() throws Exception {
		initAndriodDriver();
		pairBlewithoutRouter();
	}
	//only checking energy duration 
	
	public void pairBlewithoutRouter() throws Exception {
		adddevicepage= new AddDevicePage(driver);
		homepage = new HomePage(driver);
		devicemenupage= new DeviceMenuPage(driver);
		analyticspage= new Analytics(driver);
		logpage= new StoreLogPage(driver);
		
		logReadandWrite readwrite = logReadandWrite.getInstance(loadProp("COM"));

		try {
		readwrite.openPort();
		
		adddevicepage.pair(1);
		adddevicepage.clickNextButtonsZephyrInfo();
		adddevicepage.checkdevicedetailstoast();
		adddevicepage.clickSubmitButtonDeviceSetting();
		adddevicepage.checkdevicesettingstoast();
		
		
		
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
