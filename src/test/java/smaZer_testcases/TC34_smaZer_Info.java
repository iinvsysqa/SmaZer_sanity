package smaZer_testcases;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pages.AddDevicePage;
import pages.DeviceMenuPage;
import pages.HomePage;
import pages.LandingPage;
import pages.OtpPage;
import pages.SignInPage;
import pages.SignUpPage;
import utils.logReadandWrite;
import wrappers.MobileAppWrappers;

public class TC34_smaZer_Info extends MobileAppWrappers {

	LandingPage landingpage;
	SignInPage loginpage;
	HomePage homepage;
	OtpPage otppage;
	AddDevicePage adddevicepage;
	DeviceMenuPage devicemenupage;
	SignUpPage signuppage;

	@BeforeClass
	public void startTestCase() {
		testCaseName = "TC34_Szephyr Info check";
		testDescription = " User should be allowed to edit and save the AC Info page values without any issue\r\n"
				+ "";
	}



	@Test(priority = 33)
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

			adddevicepage.pair(5);
			adddevicepage.aCBrandNameClick();
			adddevicepage.enterAcModelName("G20");
			adddevicepage.verifyDefaultCapacity();
			adddevicepage.verifyDefaultCapacity();
			adddevicepage.clickNextButtonsZephyrInfo();
			adddevicepage.checkdevicedetailstoast();
			adddevicepage.clickSubmitButtonDeviceSetting();
			adddevicepage.checkdevicesettingstoast();
			Thread.sleep(1000);
			homepage.clickONOFFButton();
			Thread.sleep(2000);
			
			homepage.clickONOFFButton();
			Thread.sleep(2000);

			homepage.clickMenuBarButton();
			devicemenupage.ClickSzephyrInfoButton();
			Thread.sleep(1000);
			devicemenupage.CheckSzephyrInfPageBrandName();
			devicemenupage.CheckSzephyrInfPageModelName();
			devicemenupage.CheckSzephyrInfPageCapacity();
			devicemenupage.clickDeviceSettingsBackButton();
			Thread.sleep(1000);


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
			fail(e);
		}
	}

}
