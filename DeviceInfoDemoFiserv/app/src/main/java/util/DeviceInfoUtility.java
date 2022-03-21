package util;

import static android.content.Context.BATTERY_SERVICE;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.os.BatteryManager;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.deviceinfodemofiserv.BuildConfig;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import interfaces.INetworkUpdate;
import model.AgentHealthInfo;

public class DeviceInfoUtility implements INetworkUpdate {
    Context ctx;

    private static int netWorkStrenght;
    private static String paperRolStatus;
    private int nwStatus;
    public DeviceInfoUtility(){

    }
    public DeviceInfoUtility(Context ctx) {
        this.ctx = ctx;

    }

    public static int getNetWorkStrenght() {
        return netWorkStrenght;
    }

    public static void setNetWorkStrenght(int netWorkSgt) {
        netWorkStrenght = netWorkSgt;
    }

    public static String getPaperRolStatus() {
        return paperRolStatus;
    }

    public static void setPaperRolStatus(String paperRolStatus) {
        DeviceInfoUtility.paperRolStatus = paperRolStatus;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public AgentHealthInfo getDeviceInfo() {
        if (BuildConfig.BUILD_TYPE.equals("release")) {
            Log.d("shrishail", ">>>>>>>>>>>>> hey BuildConfig.BUILD_TYPE = " + BuildConfig.BUILD_TYPE);
        } else if (BuildConfig.BUILD_TYPE.equals("debug")) {
            Log.d("shrishail", ">>>>>>>>>>>>> hey BuildConfig.BUILD_TYPE = " + BuildConfig.BUILD_TYPE);
        }
/*        DeviceInfo deviceInfo = new DeviceInfo(getRamDetails(), getBateryPercentage(), Build.BOARD, Build.FINGERPRINT, Build.HOST,
                Build.TYPE, Build.BRAND, Build.MANUFACTURER, Build.ID, Build.MODEL, Build.USER, Build.DEVICE,
                Build.PRODUCT, Build.BOOTLOADER, Build.DISPLAY, Build.HARDWARE);*/
        AgentHealthInfo agentHealthInfo = new AgentHealthInfo(Build.PRODUCT, "Dominos",fetchNetworkStatusString(), Build.DISPLAY,
                "Yes", generateTerminalID(), getTimeStamp(), getRamDetails(), Build.MANUFACTURER, installedApps(),
                getBateryPercentage(), Build.SERIAL, Build.BOOTLOADER, Build.HOST, Build.MODEL, Build.BRAND, Build.DEVICE, Build.BOARD,
                Build.HARDWARE);
        return agentHealthInfo;
    }

    private String fetchNetworkStatusString() {
        String nwStatusStr = "";
        //int nwStatus = DeviceInfoUtility.getNetWorkStrenght();
        switch (nwStatus) {
            case -50:
                nwStatusStr = "good";
                break;
            default:
                nwStatusStr = "bad";
        }
        return nwStatusStr;
    }

    private String getTimeStamp() {
        // 2021-03-24 16:48:05
        String timeStampRes = "";
        try {
            final SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            timeStampRes = sdf3.format(timestamp);
            System.out.println(timeStampRes);
        } catch (Exception e) {
            System.out.println(" exception " + e);
        }

        return timeStampRes;
    }

    public String getRamDetails() {
        ActivityManager actManager = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memInfo = new ActivityManager.MemoryInfo();
        actManager.getMemoryInfo(memInfo);
        long size = memInfo.totalMem;
        long Kb = 1024;
        long Mb = Kb * 1024;
        long Gb = Mb * 1024;
        long Tb = Gb * 1024;
        long Pb = Tb * 1024;
        long Eb = Pb * 1024;

        if (size < Kb) return floatForm(size) + " byte";
        if (size >= Kb && size < Mb) return floatForm((double) size / Kb) + " KB";
        if (size >= Mb && size < Gb) return floatForm((double) size / Mb) + " MB";
        if (size >= Gb && size < Tb) return floatForm((double) size / Gb) + " GB";
        if (size >= Tb && size < Pb) return floatForm((double) size / Tb) + " TB";
        if (size >= Pb && size < Eb) return floatForm((double) size / Pb) + " Pb";
        if (size >= Eb) return floatForm((double) size / Eb) + " Eb";

        return "0";

    }

    private String floatForm(double d) {
        return String.format(Locale.US, "%.2f", d);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    String getBateryPercentage() {
        BatteryManager bm = (BatteryManager) ctx.getSystemService(BATTERY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int percentage = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
            return percentage + "%";
        } else return " %";
    }

    public List<String> installedApps() {
        ArrayList<String> listOfApps = new ArrayList<String>();
        List<PackageInfo> packList = ctx.getPackageManager().getInstalledPackages(0);
        for (int i = 0; i < packList.size(); i++) {
            PackageInfo packInfo = packList.get(i);
            if ((packInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                String appName = packInfo.applicationInfo.loadLabel(ctx.getPackageManager()).toString();
                listOfApps.add(appName);
                Log.e("App № " + Integer.toString(i), appName);
            }
        }
        return listOfApps;
    }

    public int generateTerminalID() {
        String terminalId = new DecimalFormat("000000").format(new Random().nextInt(999999));
        System.out.println(DeviceInfoUtility.class.getSimpleName() + " >>>>>>>>>> terminalId >>>>>>. " + terminalId);
        return Integer.valueOf(terminalId);
    }

    @Override
    public void onNetworkStatusChanged(int networkStatus) {
        nwStatus = networkStatus;
        DeviceInfoUtility.setNetWorkStrenght(nwStatus);
    }
}
