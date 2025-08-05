package pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.android.AndroidDriver;
import utils.Reporter;
import wrappers.GenericWrappers;

public class Analytics  extends GenericWrappers {

	private AndroidDriver driver;
	public Analytics(AndroidDriver driver) {

		this.driver = driver;
		PageFactory.initElements(driver, this);
		this.js = (JavascriptExecutor) driver;
		this.wait=new WebDriverWait(driver, 10);
	}
	
	JavascriptExecutor js = (JavascriptExecutor)driver;
	@FindBy(xpath = "//*[@resource-id='energy_used_live_units_watts']")
	private WebElement energyUsedunit;
	@FindBy(xpath = "//*[@resource-id='energy_used_live_units_minutes']")
	private WebElement enrgyDurationmin;
	
	@FindBy(xpath = "//*[@resource-id='EnergyDuration_TouchableOpacity']")
	private WebElement energyDuration;
	
	@FindBy(xpath="//android.widget.FrameLayout[@resource-id='android:id/content']/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[1]/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[7]/com.horcrux.svg.SvgView/com.horcrux.svg.GroupView/com.horcrux.svg.CircleView[1]")
	private WebElement deviceONOFFButton;
	@FindBy(xpath = "//*[@resource-id='Home_Analytics_Button']")
	private WebElement analyticsButton;
	@FindBy(xpath = "//*[@resource-id='Device_BackIcon']")
	private WebElement BackButton;
//	@FindBy(xpath = "")
//	private WebElement ;
//	@FindBy(xpath = "")
//	private WebElement ;
//	@FindBy(xpath = "")
//	private WebElement ;
//	@FindBy(xpath = "")
//	private WebElement ;

	
	
	public void navigateAnalyticsPage() {

		expshortWaittwenty(analyticsButton);
		clickbyXpath(analyticsButton, "analytics button");
	}
	
	
	String oldvalue;
	public String getenergydurationvalue() {
           expWait(enrgyDurationmin);
		 oldvalue = enrgyDurationmin.getText();
		 Reporter.reportStep("Analytics value before Start of the session : " + oldvalue, "PASS");
		System.out.println(oldvalue);
	return oldvalue;
	}
	

	
	public void navigatehomepage() {

		clickbyXpath(BackButton, "Back button");
	}
	
//	public boolean checkenrgyduration(int value) throws Exception {	
//		boolean bReturn = false;
//		expshortWaittwenty(enrgyDurationmin);
////		clickbyXpath(enrgyDurationmin, "energy duration");
//		
//		int newvalue=extractMinutes(oldvalue)+value;
//		System.out.println("new value :"+newvalue);
//		String text = enrgyDurationmin.getText();
//		System.out.println("after analytics :"+text);
//		if (extractMinutes( enrgyDurationmin.getText())==newvalue) {
//			
//			Reporter.reportStep("Analytics value updated after session : " + text, "PASS");
//			bReturn = true;
//		}
//		else {
//			Reporter.reportStep("Wrong Analytics value updated: " + text, "FAIL");
//			
//		}
//		return bReturn;
//		
//	}
	
	public boolean checkenrgyduration(int value) throws Exception {
	    boolean bReturn = false;
	    expshortWaittwenty(enrgyDurationmin);

	    int expectedValue = extractMinutes(oldvalue) + value;
	    System.out.println("Expected new value: " + expectedValue);

	    String currentText = enrgyDurationmin.getText();
	    System.out.println("Analytics value after session: " + currentText);

	    int actualMinutes = extractMinutes(currentText);

	    // Check if it's 59 seconds (acceptable for <1min session)
	    if (currentText.trim().equalsIgnoreCase("59s") && value == 1) {
	        Reporter.reportStep("Analytics value updated with 59s (less than 1 minute), which is acceptable", "PASS");
	        bReturn = true;
	    }
	    // Normal case comparison
	    else if (actualMinutes == expectedValue) {
	        Reporter.reportStep("Analytics value updated after session: " + currentText, "PASS");
	        bReturn = true;
	    } else {
	        Reporter.reportStep("Wrong Analytics value updated: " + currentText, "FAIL");
	    }

	    return bReturn;
	}


}
