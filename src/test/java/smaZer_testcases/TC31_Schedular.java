package smaZer_testcases;

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

public class TC31_Schedular extends MobileAppWrappers {
	


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
		testCaseName = "TC31_Schedular_BLE Without Router_App close mode";
		testDescription = "Pair in Ble without router mode <br> create 3 schedule and check schedule worked or not <br> check device in off state after schedule completion";
	}

	@Test(priority = 30)
	public void TC31_Schedular_BLE_Without_Router_App_close() throws Exception {
		initAndriodDriver();
		landingpage = new LandingPage(driver);
		loginpage = new SignInPage(driver);
		homepage=new HomePage(driver);
		otppage = new OtpPage(driver);
		adddevicepage = new AddDevicePage(driver);
		devicemenupage = new DeviceMenuPage(driver);
		schedulepage = new Schedularpage(driver);
		analytics=new Analytics(driver);
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
		schedulepage.createSchedules(5, 3, 1);//mention the time to start ,how many schedules need to keep,interval between next schedule
		schedulepage.backToHomepage();
		
		Thread.sleep(9*60*1000);//set thread values based on schedule duration kept .
		analytics.navigateAnalyticsPage();
		analytics.checkenrgyduration(3);//schedule duration value is (1) and this value should be same as no.of schedule kept 
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
