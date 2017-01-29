package me.shalvah.lionetwatcher;


import android.net.wifi.WifiInfo;

public class WifiData
	{

		public String ssid;
		public int speed;

		public WifiData()
		{

		}

		public WifiData(WifiInfo wifiInfo)
		{
			this.ssid = wifiInfo.getSSID();
			this.speed = wifiInfo.getLinkSpeed();
		}

	}
