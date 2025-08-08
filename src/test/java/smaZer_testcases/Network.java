package smaZer_testcases;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class Network {
	
	public static AndroidDriver<AndroidElement> driver;
	static String wifiName="TP-Link_6D38_With_Internet";
	public static void main(String[] args) {
        try {
	 executeAdbCommand("adb shell am start -a android.intent.action.MAIN -n com.android.settings/.wifi.WifiSettings");

     // Wait for the Wi-Fi settings page to open
     Thread.sleep(2000);

     driver.findElement(MobileBy.AndroidUIAutomator(
				"new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView(new UiSelector().textContains(\""
						+ wifiName + "\"))"));
//     // Input Wi-Fi name
//     executeAdbCommand("adb shell input tap 500 300"); // Tap on the "Add Network" button (adjust coordinates as needed)
//     Thread.sleep(1000);
//     executeAdbCommand("adb shell input text \"TP-Link_6D38_With_Internet\"");
//     Thread.sleep(1000);
//
//     // Move to the password field
//     executeAdbCommand("adb shell input keyevent KEYCODE_TAB");
//     Thread.sleep(1000);
//
//     // Input Wi-Fi password
//     executeAdbCommand("adb shell input text \"12345678904\"");
//     Thread.sleep(1000);
//
//     // Press Enter to connect
//     executeAdbCommand("adb shell input keyevent KEYCODE_ENTER");

     System.out.println("Wi-Fi connection process completed.");
 } catch (Exception e) {
     e.printStackTrace();
 }}


private static void executeAdbCommand(String command) {
 try {
     @SuppressWarnings("deprecation")
	 Process process = Runtime.getRuntime().exec(command);
     BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
     String line;
     while ((line = reader.readLine()) != null) {
         System.out.println(line);
     }
     process.waitFor();
 } catch (Exception e) {
     e.printStackTrace();
 }
}
}
