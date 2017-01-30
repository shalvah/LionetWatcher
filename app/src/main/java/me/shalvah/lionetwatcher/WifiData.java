package me.shalvah.lionetwatcher;


import android.net.wifi.WifiInfo;

public class WifiData
	{

		public String bssid;
		public int speed;
		public long timestamp;
		public double latitude;
		public double longitude;

		public WifiData()
		{

		}

		public WifiData(WifiInfo wifiInfo, double longitude, double latitude)
		{
			this.bssid = wifiInfo.getBSSID();
			this.speed = wifiInfo.getLinkSpeed();
			this.timestamp = System.currentTimeMillis()/1000;
			this.latitude = latitude;
			this.longitude = longitude;
		}

	}
