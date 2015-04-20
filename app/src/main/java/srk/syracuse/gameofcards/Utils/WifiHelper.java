package srk.syracuse.gameofcards.Utils;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;


public class WifiHelper {
    public static ArrayList<String> deviceList = new ArrayList<String>();

    public static ArrayList<String> getDeviceList() {

        BufferedReader br = null;
        boolean isFirstLine = true;

        try {
            br = new BufferedReader(new FileReader("/proc/net/arp"));
            String line;
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                String[] splitted = line.split(" +");
                if (splitted != null && splitted.length >= 4) {
                    String ipAddress = splitted[0];
                    boolean isReachable = InetAddress.getByName(
                            splitted[0]).isReachable(500);
                    if (isReachable) {
                        deviceList.add(ipAddress);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return deviceList;
    }
}
