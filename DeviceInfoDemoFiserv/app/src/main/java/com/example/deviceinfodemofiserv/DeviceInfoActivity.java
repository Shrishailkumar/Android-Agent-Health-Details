package com.example.deviceinfodemofiserv;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

import model.DeviceInfo;
import util.DeviceInfoUtility;

public class DeviceInfoActivity extends Activity {
    TextView tvDeviceInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvDeviceInfo = findViewById(R.id.id_deviceInfo);
        DeviceInfoUtility deviceInfoUtility = new DeviceInfoUtility(getApplication());
        DeviceInfo deviceInfo = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            deviceInfo = deviceInfoUtility.getDeviceInfo();
        }
        updateView(deviceInfo);
    }

    private void updateView(DeviceInfo build) {

        tvDeviceInfo.setText("SERIAL: " + build.getSerial() + "\n\n" +
                "MODEL: " + build.getModel() + "\n\n" +
                "ID: " + build.getId() + "\n\n" +
                "Manufacture: " + build.getManufacture() + "\n\n" +
                "Brand: " + build.getBrad() + "\n\n" +
                "Type: " + build.getType() + "\n\n" +
                "BOARD: " + build.getBoard() + "\n\n" +
                "RAM: " + build.getRamDetails() + "\n\n" +
                "Battery:  " + build.getBatteryPercentage() + "\n\n" +
                "BRAND: " + build.getBrad() + "\n\n" +
                "HOST: " + build.getHost() + "\n\n" +
                "FINGERPRINT: " + build.getFingerPrint() + "\n\n" +
                "Device: " + build.getDevice() + "\n\n" +
                "BootLoader: " + build.getBootLoader() + "\n\n" +
                "Display: " + build.getDisplay() + "\n\n" +
                "Hardware: " + build.getHardware() + "\n\n" +
                "product: " + build.getProduct());
    }
}

