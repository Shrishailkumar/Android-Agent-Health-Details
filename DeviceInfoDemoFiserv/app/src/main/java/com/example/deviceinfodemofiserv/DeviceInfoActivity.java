package com.example.deviceinfodemofiserv;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import interfaces.IUpdateAgentData;
import model.AgentHealthInfo;
import service.POSHealthCheckService;
import util.DeviceInfoUtility;
import util.NetworkConnectivity;
import util.ReactCall;

public class DeviceInfoActivity extends Activity implements IUpdateAgentData, POSHealthCheckService.IUpdateDevcieInfoActivity {
    TextView tvDeviceInfo;
    //DeviceInfoUtility deviceInfoUtility;
    NetworkConnectivity networkConnectivity;
    AgentHealthInfo agentHealthInfo = null;
    //ReactCall reactCall;
    IUpdateAgentData iUpdateAgentData;
    ProgressDialog progressBar;

    private boolean bound = false;
    private POSHealthCheckService myService;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvDeviceInfo = findViewById(R.id.id_deviceInfo);
        //deviceInfoUtility = new DeviceInfoUtility(getApplication());
        progressBar = new ProgressDialog(DeviceInfoActivity.this);
        registerNetworkCallback();

        //register scehduled react call to RestApi
       // reactCall = new ReactCall(DeviceInfoActivity.this, deviceInfoUtility);
        // move the react scheduling of api cal to service
        Intent intent = new Intent(DeviceInfoActivity.this,POSHealthCheckService.class);

        //bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent);
        } else {
            startService(new Intent(DeviceInfoActivity.this, POSHealthCheckService.class));
        }
        progressBar.setCancelable(true);//you can cancel it by pressing back button
        progressBar.setMessage("Loading ...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.show();
        //reactCall.addEtaUpdateDisposable();
    }
    @Override
    protected void onStop() {
        super.onStop();
        // Unbind from service
        /*if (bound) {
            myService.setCallbacks(null); // unregister
            unbindService(serviceConnection);
            bound = false;
        }*/
    }

    /** Callbacks for service binding, passed to bindService() */
    private ServiceConnection serviceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            // cast the IBinder and get MyService instance
            POSHealthCheckService.LocalBinder binder = (POSHealthCheckService.LocalBinder) service;
            myService = binder.getService();
            bound = true;
            myService.setCallbacks(DeviceInfoActivity.this); // register
        }
        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            bound = false;
        }
    };


    private void registerNetworkCallback() {
        ActivityCompat.requestPermissions(DeviceInfoActivity.this,
                new String[]{Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.READ_PHONE_STATE, Manifest.permission.CHANGE_NETWORK_STATE},
                1);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    networkConnectivity = new NetworkConnectivity(DeviceInfoActivity.this, POSHealthCheckService.deviceInfoUtility);
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(DeviceInfoActivity.this, "Permission denied to read Network State Access", Toast.LENGTH_SHORT).show();
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private void updateView(AgentHealthInfo build) {
        tvDeviceInfo.setText(
                "TERMINAL ID: " + build.getTerminalId() + "\n\n" +
                        "MERCHANTNAME:  " + build.getMerchantName() + "\n\n" +
                        "PAPER ROL: " + build.getPaperRoll() + "\n\n" +
                        "RAM: " + build.getRamDetails() + "\n\n" +
                        "Battery:  " + build.getBatteryStatus() + "\n\n" +
                        "NetWork Status:  " + build.getNetworkStatus() + "\n\n" +
                        "TimeStamp: " + build.getTimeStamp() + "\n\n" +
                        "DEVICE: " + build.getDevice() + "\n\n" +
                        "BOOTLOADER: " + build.getBootloader() + "\n\n" +
                        "MODEL: " + build.getModel() + "\n\n" +
                        "HOST: " + build.getHost() + "\n\n" +
                        "Manufacture: " + build.getManufacture() + "\n\n" +
                        "Brand: " + build.getBrand() + "\n\n" +
                        "DISPLAY: " + build.getDisplay() + "\n\n" +
                        "BOARD: " + build.getBoard() + "\n\n" +
                        "HARDWARE:  " + build.getHardware() + "\n\n" +
                        "INSTALLED APPS:  " + build.getInstalledApps() + "\n\n" +
                        "product: " + build.getProduct());
        tvDeviceInfo.invalidate();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            reactCall.disposeEtaUpdate();
        }*/
    }

    @Override
    public void UpdateAgentData(AgentHealthInfo agentHealthInfo) {
        if (progressBar.isShowing())
            progressBar.cancel();
        updateView(agentHealthInfo);
    }

    @Override
    public void onUpdateUi(AgentHealthInfo agentHealthInfo) {
        if (progressBar.isShowing())
            progressBar.cancel();
        updateView(agentHealthInfo);
    }
}

