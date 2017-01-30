package me.shalvah.lionetwatcher;


import android.net.wifi.WifiInfo;

public class WifiData
	{

		public int lastKnownSpeed;
		public long timestamp;

		public WifiData()
		{

		}

		public WifiData(WifiInfo wifiInfo)
		{
			this.lastKnownSpeed = wifiInfo.getLinkSpeed();
			this.timestamp = System.currentTimeMillis()/1000;
		}

	}
