package pages;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.google.common.collect.ImmutableMap;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.appmanagement.ApplicationState;
import wrappers.GenericWrappers;

public class StoreLogPage extends GenericWrappers{

	private AndroidDriver driver;
	public String userName = loadProp("USERNAME");
	public String emaId = loadProp("EMAILID");
	
	
	 public StoreLogPage(AndroidDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	 
	 
		@FindBy(xpath = "//*[@resource-id='menu_icon_sharelog']")
		private WebElement shareLogbtn;
		@FindBy(xpath = "//android.widget.TextView[@text='File Manager +']")
		private WebElement FTPicon;
		@FindBy(xpath = "//android.widget.TextView[@text='Downloads']")
		private WebElement FTPDownloads;
		@FindBy(xpath = "//android.widget.TextView[@text='Save']")
		private WebElement FTPsaveBtn;
		@FindBy(xpath = "//*[@resource-id='menu_bar']")
		private WebElement menuBarButton;
		 @FindBy(xpath = "//*[@resource-id='Launch_SignInText']")
		    private WebElement launchSignInButton;
		 @FindBy(xpath = "//*[@resource-id='SignIn_SignInText']")
			private WebElement signInButton;
		 @FindBy(xpath = "//*[@resource-id='SignIn_Email_or_UserName']")
			private WebElement userNameField;
		 
		 @FindBy(xpath = "(//android.widget.EditText[@resource-id='textInput'])[1]")
			private WebElement otpField1;

			@FindBy(xpath = "(//android.widget.EditText[@resource-id='textInput'])[2]")
			private WebElement otpField2;
			
			@FindBy(xpath = "(//android.widget.EditText[@resource-id='textInput'])[3]")
			private WebElement otpField3;
			
			@FindBy(xpath = "(//android.widget.EditText[@resource-id='textInput'])[4]")
			private WebElement otpField4;

			@FindBy(xpath = "//*[@resource-id='OTP_ButtonText']")
			private WebElement submitBtn;
			
			@FindBy(xpath = "//*[@resource-id='com.android.permissioncontroller:id/permission_allow_foreground_only_button']")
			private WebElement locationPopUp;

			@FindBy(xpath = "//*[@resource-id='com.android.permissioncontroller:id/permission_allow_button']")
			private WebElement nearByPermisson;
		
		public void storeLogToDownloads() {
			killAndReopenApp();
			turnOnBT();
			if (isElementDisplayedCheck(menuBarButton)) {
				
				clickbyXpath(menuBarButton, " Menu Bar ");
				clickbyXpath(shareLogbtn, "ShareLog Button");
				clickbyXpath(FTPicon, "Filemanager Button");
				clickbyXpath(FTPDownloads, "Filemanager downloads Button");
				clickbyXpath(FTPsaveBtn, "Filemanager save Button");
			}else {
				clickbyXpath(launchSignInButton, " Launch Sign In Button " );
				entervaluebyXpath(userNameField, " User Name " , userName);
				clickbyXpath(signInButton, " Sign In ");
				entervaluebyXpath(otpField1, " OTP Box 1 " , "1");
				entervaluebyXpath(otpField2, " OTP Box 2 " , "2");
				entervaluebyXpath(otpField3, " OTP Box 3 " , "3");
				entervaluebyXpath(otpField4, " OTP Box 4 " , "4");
				clickbyXpath(submitBtn," Submit Button ");
				
				driver.executeScript("mobile: shell", ImmutableMap.of("command", "pm grant com.iinvsys.szephyr android.permission.ACCESS_FINE_LOCATION"));
				driver.executeScript("mobile: shell", ImmutableMap.of("command", "pm grant com.iinvsys.szephyr android.permission.BLUETOOTH_SCAN"));
				driver.executeScript("mobile: shell", ImmutableMap.of("command", "pm grant com.iinvsys.szephyr android.permission.BLUETOOTH_CONNECT"));
//				if (isElementDisplayedCheck(locationPopUp)) {
//					clickbyXpath(locationPopUp, "Location pop-up");
//				} else {
//					System.out.println("not asked for precise or approx location");
//
//				}
//				if (isElementDisplayedCheck(nearByPermisson)) {
//
//					clickbyXpathwithoutReport(nearByPermisson, " Near by devices Permission  ");
//				}
				
				clickbyXpath(menuBarButton, " Menu Bar ");
				clickbyXpath(shareLogbtn, "ShareLog Button");
				clickbyXpath(FTPicon, "Filemanager Button");
				clickbyXpath(FTPDownloads, "Filemanager downloads Button");
				clickbyXpath(FTPsaveBtn, "Filemanager save Button");
				
			}
			
			
		}
		public void takeAppLog() throws FileNotFoundException, IOException, Exception {
			File f= new File(".//smaZerLOG.txt");
			if (f.exists()) {
				f.delete();
				}
			Runtime.getRuntime().exec("adb shell rm /storage/emulated/0/Download/*.txt");// to delete all .txt files 
		    storeLogToDownloads();
		    
			String projectRoot = System.getProperty("user.dir");
			Thread.sleep(5000);
			String pullCommand = "adb pull /storage/emulated/0/Download/smaZerLOG.txt \"" + projectRoot + "\\smaZerLOG.txt\"";
			Runtime.getRuntime().exec(pullCommand);

//		    Runtime.getRuntime().exec("adb pull /storage/emulated/0/Download/sZephyrLOG.txt "+projectRoot+"/sZephyrLOG.txt");
		    if (driver.queryAppState(packages) != ApplicationState.RUNNING_IN_FOREGROUND) {
				driver.activateApp(packages); 
			}
			}
		
		
}
