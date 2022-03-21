package util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;

import androidx.annotation.NonNull;

import interfaces.INetworkUpdate;

public class NetworkConnectivity {
    private Context context;
    INetworkUpdate iNetworkUpdate;

    public NetworkConnectivity() {
    }

    public NetworkConnectivity(Context ctx,DeviceInfoUtility deviceInfoUtility) {
        this.context = ctx;
        iNetworkUpdate = (INetworkUpdate) deviceInfoUtility;
        NetworkRequest networkRequest = new NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                .build();
        ConnectivityManager connectivityManager =
                null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            connectivityManager = (ConnectivityManager) this.context.getSystemService(ConnectivityManager.class);
        }
        connectivityManager.requestNetwork(networkRequest, networkCallback);
    }

    private ConnectivityManager.NetworkCallback networkCallback = new ConnectivityManager.NetworkCallback() {
        @Override
        public void onAvailable(@NonNull Network network) {
            super.onAvailable(network);
        }

        @Override
        public void onLost(@NonNull Network network) {
            super.onLost(network);
        }

        @SuppressLint("NewApi")
        @Override
        public void onCapabilitiesChanged(@NonNull Network network, @NonNull NetworkCapabilities networkCapabilities) {
            super.onCapabilitiesChanged(network, networkCapabilities);
            int networkStrenght= networkCapabilities.getSignalStrength();
            iNetworkUpdate.onNetworkStatusChanged(networkStrenght);
            final boolean unmetered = networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_NOT_METERED);
        }
    };

}
