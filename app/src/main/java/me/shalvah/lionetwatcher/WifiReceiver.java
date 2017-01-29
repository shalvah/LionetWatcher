package me.shalvah.lionetwatcher;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

public class WifiReceiver extends BroadcastReceiver
	{
		public WifiReceiver()
		{
		}

		@Override
		public void onReceive(Context context, Intent intent)
		{
			NetworkInfo ni = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
			if (ni.getState() == NetworkInfo.State.CONNECTED)
			{
				Log.d("WIFI_INFO: ", "" + ni.toString());
				WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE) ;
				WifiInfo wi = wm.getConnectionInfo();
				if (wi.getSSID().startsWith("LIONET"))
				{
					Log.d("WIFI_SPEED", "" + wi.getLinkSpeed());
				}
			}
		}
	}
