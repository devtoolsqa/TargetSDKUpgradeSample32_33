package com.example.targetsdkupgradesample32_33.wifi;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

/**
 * WifiHotstopManager class makes use of the Android's WifiManager and WifiConfiguration class
 * to implement the wifi hotspot feature.
 * Created by Adeel Zafar on 28/5/2019.
 */

public class WifiHotspotManager {
  private static final String TAG = "WifiHotspotManager";

  private WifiManager wifiManager;
  private Context mContext;
  private WifiManager.LocalOnlyHotspotReservation hotspotReservation;

  public WifiHotspotManager(Context context) {
    wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    mContext = context;

  }

  //Workaround to turn on hotspot for Oreo versions
  @RequiresApi(api = Build.VERSION_CODES.O)
  public void turnOnHotspot() {
    if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
      // TODO: Consider calling
      //    ActivityCompat#requestPermissions
      // here to request the missing permissions, and then overriding
      //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
      //                                          int[] grantResults)
      // to handle the case where the user grants the permission. See the documentation
      // for ActivityCompat#requestPermissions for more details.
      return;
    }
    wifiManager.startLocalOnlyHotspot(new WifiManager.LocalOnlyHotspotCallback() {
      @Override
      public void onStarted(WifiManager.LocalOnlyHotspotReservation reservation) {
        super.onStarted(reservation);
        hotspotReservation = reservation;
        WifiConfiguration currentConfig = hotspotReservation.getWifiConfiguration();

        printCurrentConfig(currentConfig);
        Log.v(TAG, "Local Hotspot Started");
      }

      @Override
      public void onStopped() {
        super.onStopped();
        Log.v(TAG, "Local Hotspot Stopped");
      }

      @Override
      public void onFailed(int reason) {
        super.onFailed(reason);
        Log.v(TAG, "Local Hotspot failed to start");
      }
    }, new Handler());

  }

  //Workaround to turn off hotspot for Oreo versions
  @RequiresApi(api = Build.VERSION_CODES.O)
  public void turnOffHotspot() {
    if (hotspotReservation != null) {
      hotspotReservation.close();
      hotspotReservation = null;
      Log.v(TAG, "Turned off hotspot");
    }
  }

  //This method checks the state of the hostpot for devices>=Oreo
  @RequiresApi(api = Build.VERSION_CODES.O)
  public boolean isHotspotStarted() {
    return hotspotReservation != null;
  }

  private void printCurrentConfig(WifiConfiguration wifiConfiguration) {
    Log.v(TAG, "THE PASSWORD IS: "
        + wifiConfiguration.preSharedKey
        + " \n SSID is : "
        + wifiConfiguration.SSID);
  }
}
