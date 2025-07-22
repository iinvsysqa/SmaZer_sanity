package smaZer_testcases;

import static org.testng.Assert.fail;

import java.io.IOException;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pages.AddDevicePage;
import pages.Analytics;
import pages.DeviceMenuPage;
import pages.HomePage;
import pages.LandingPage;
import pages.OtpPage;
import pages.Schedularpage;
import pages.SignInPage;
import pages.StoreLogPage;
import utils.logReadandWrite;
import wrappers.MobileAppWrappers;

public class TC30_Schedular extends MobileAppWrappers {

	LandingPage landingpage;
	SignInPage loginpage;
	HomePage homepage;
	OtpPage otppage;
	AddDevicePage adddevicepage;
	DeviceMenuPage devicemenupage;
	Schedularpage schedulepage;
    Analytics analytics;
    
	 StoreLogPage logpage;  
	 
	 @BeforeClass   
	public void startTestCase() {
		testCaseName = "TC30_Schedular_BLE without router";
		testDescription = "Paired in Blewithoutrouter mode <br>create a schedule and disable it <br> check schedule should not work";
	}

	@Test(priority = 29)
	public void TC30_Schedular_BLE_without_router() throws Exception {
		initAndriodDriver();

		landingpage = new LandingPage(driver);
		loginpage = new SignInPage(driver);
		homepage=new HomePage(driver);
		otppage = new OtpPage(driver);
		adddevicepage = new AddDevicePage(driver);
		devicemenupage = new DeviceMenuPage(driver);
		schedulepage = new Schedularpage(driver);
		analytics = new Analytics(driver);
		logpage= new StoreLogPage(driver);
		
		logReadandWrite readwrite = logReadandWrite.getInstance(loadProp("COM"));
		try {
		readwrite.openPort();

		
		adddevicepage.pair(1);
		adddevicepage.clickNextButtonsZephyrInfo();
		adddevicepage.clickBleokbutton();
//		adddevicepage.checkdevicedetailstoast();
		adddevicepage.clickSubmitButtonDeviceSetting();
		
		
		
		analytics.navigateAnalyticsPage();
		analytics.getenergydurationvalue();
		schedulepage.backToHomepage();
		schedulepage.clickSchedulebtn();
		schedulepage.createSchedules(3, 1, 1);//mention the time to start ,how many schedules need to keep,interval between next schedule
		schedulepage.disableschedule(0);
		schedulepage.backToHomepage();
		
		Thread.sleep(4*60*1000);//set thread values based on schedule duration kept .
		analytics.navigateAnalyticsPage();
		analytics.checkenrgyduration(0);
		schedulepage.backToHomepage();
		schedulepage.clickSchedulebtn();
		schedulepage.deleteschedule();
		schedulepage.backToHomepage();
		
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
