package service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.Context;
import android.graphics.Color;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.example.deviceinfodemofiserv.DeviceInfoActivity;

import interfaces.IUpdateAgentData;
import model.AgentHealthInfo;
import util.DeviceInfoUtility;
import util.ReactCall;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class POSHealthCheckService extends IntentService implements IUpdateAgentData {
    public static DeviceInfoUtility getDeviceInfoUtility() {
        return deviceInfoUtility;
    }

    public static void setDeviceInfoUtility(DeviceInfoUtility deviceInfoUtility) {
        POSHealthCheckService.deviceInfoUtility = deviceInfoUtility;
    }

    public static DeviceInfoUtility deviceInfoUtility;

    DeviceInfoUtility deviceInfoUtil;
    ReactCall reactCall;

    private final IBinder binder = new LocalBinder();
    // Registered callbacks
    private IUpdateDevcieInfoActivity serviceCallbacks;

    // Class used for the client Binder.
    public class LocalBinder extends Binder {
        public POSHealthCheckService getService() {
            // Return this instance of MyService so clients can call public methods
            return POSHealthCheckService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public void setCallbacks(IUpdateDevcieInfoActivity iUpdateDevcieInfoActivity) {
        serviceCallbacks = iUpdateDevcieInfoActivity;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        deviceInfoUtil = new DeviceInfoUtility(getApplication());
        setDeviceInfoUtility(deviceInfoUtil);

        //register scehduled react call to RestApi
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O)
            reactCall = new ReactCall(POSHealthCheckService.this, deviceInfoUtil);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O)
            startMyOwnForeground();
        else
            startForeground(1, new Notification());

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startMyOwnForeground() {
        String NOTIFICATION_CHANNEL_ID = "POS Agent Device";
        String channelName = "Background Service";
        NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_NONE);
        chan.setLightColor(Color.BLUE);
        chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert manager != null;
        manager.createNotificationChannel(chan);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        Notification notification = notificationBuilder.setOngoing(true)
                .setContentTitle("App is running in background")
                .setPriority(NotificationManager.IMPORTANCE_HIGH)
                .setCategory(Notification.CATEGORY_SERVICE)
                .build();
        startForeground(2, notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O)
            reactCall.addEtaUpdateDisposable();
        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            reactCall.disposeEtaUpdate();
        }
        //stoptimertask();

       /* Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("restartservice");
        broadcastIntent.setClass(this, Restarter.class);
        this.sendBroadcast(broadcastIntent);*/
    }



    public POSHealthCheckService() {
        super("POSHealthCheckService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O)
                reactCall.addEtaUpdateDisposable();
        }
    }


    @Override
    public void UpdateAgentData(AgentHealthInfo agentHealthInfo) {
      //  serviceCallbacks.onUpdateUi(agentHealthInfo);
    }

    public interface IUpdateDevcieInfoActivity {
        public void onUpdateUi(AgentHealthInfo agentHealthInfo);
    }

}