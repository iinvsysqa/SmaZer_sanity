package utils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import wrappers.GenericWrappers;
public class ADBconnections extends GenericWrappers{

	    
	    public static List<String> getConnectedDevices() throws IOException, InterruptedException {
	        List<String> devices = new ArrayList<>();
	        
	        Process process = Runtime.getRuntime().exec("adb devices");
	        process.waitFor();
	        
	        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
	            String line;
	            // Skip the first line (header)
	            reader.readLine();
	            while ((line = reader.readLine()) != null) {
	                if (!line.trim().isEmpty()) {
	                    String deviceId = line.split("\\s+")[0];
	                    devices.add(deviceId);
	                }
	            }
	        }
	        
	        return devices;
	    }
	    
	    public static boolean isDeviceConnected() throws IOException, InterruptedException {
	        return !getConnectedDevices().isEmpty();
	    }
	
}
