package me.shalvah.lionetwatcher;


import android.net.wifi.WifiInfo;
import android.os.SystemClock;

public class WifiData
	{

		public String ssid;
		public int speed;
		public long timestamp;

		public WifiData()
		{

		}

		public WifiData(WifiInfo wifiInfo)
		{
			this.ssid = wifiInfo.getSSID();
			this.speed = wifiInfo.getLinkSpeed();
			this.timestamp = System.currentTimeMillis()/1000;
		}

	}
