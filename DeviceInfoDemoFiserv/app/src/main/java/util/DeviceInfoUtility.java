package util;

import static android.content.Context.BATTERY_SERVICE;

import android.app.ActivityManager;
import android.content.Context;
import android.os.BatteryManager;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.deviceinfodemofiserv.BuildConfig;

import model.DeviceInfo;

public class DeviceInfoUtility {
    Context ctx;
    public DeviceInfoUtility(Context ctx){
        this.ctx=ctx;

    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public DeviceInfo getDeviceInfo(){
       if (BuildConfig.BUILD_TYPE.equals("release")){
           Log.d("shrishail",">>>>>>>>>>>>> hey BuildConfig.BUILD_TYPE = "+BuildConfig.BUILD_TYPE);
       }else if (BuildConfig.BUILD_TYPE.equals("debug")) {
           Log.d("shrishail", ">>>>>>>>>>>>> hey BuildConfig.BUILD_TYPE = " + BuildConfig.BUILD_TYPE);
       }
        DeviceInfo deviceInfo = new DeviceInfo(getRamDetails(),getBateryPErcentage(),Build.BOARD,Build.FINGERPRINT,Build.HOST,
                Build.TYPE,Build.BRAND,Build.MANUFACTURER,Build.ID,Build.MODEL,Build.USER,Build.DEVICE,
                Build.PRODUCT,Build.BOOTLOADER,Build.DISPLAY,Build.HARDWARE);
        return deviceInfo;
    }
   public String getRamDetails(){
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
        return String.format(java.util.Locale.US, "%.2f", d);
    }

   @RequiresApi(api = Build.VERSION_CODES.M)
   String getBateryPErcentage(){
       BatteryManager bm = (BatteryManager)ctx.getSystemService(BATTERY_SERVICE);
       if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
           int percentage = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
           return percentage+"%";
       }else return " %";
    }
}
