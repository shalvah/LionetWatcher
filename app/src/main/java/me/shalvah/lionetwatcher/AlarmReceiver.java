package me.shalvah.lionetwatcher;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.SystemClock;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AlarmReceiver extends BroadcastReceiver
	{
		@Override
		public void onReceive(Context context, Intent intent)
		{
			WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
			WifiInfo wi = wm.getConnectionInfo();
			if (wi.getSSID().contains("LIONET@") || wi.getSSID().contains("Lionet@"))
			{
				Log.d("WIFI_SPEED", "" + wi.getLinkSpeed());
				storeWifiData(wi);
			} else
			{
				Log.d("WIFI_SPEED ", "STOPPING_ALARM");

				int alarmId = intent.getExtras().getInt("alarmId");
				PendingIntent alarmPendingIntent;
				alarmPendingIntent = PendingIntent.getBroadcast(context , alarmId,
						new Intent(context, AlarmReceiver.class),
						0);

				AlarmManager alarmManager= (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
				alarmManager.cancel(alarmPendingIntent);
			}

		}

		private void storeWifiData(WifiInfo wifiInfo)
		{
			WifiData wifiData = new WifiData(wifiInfo);
			DatabaseReference dbr = FirebaseDatabase.getInstance().getReference();

			Log.d("WIFI_WRITE_RESULT", "" + dbr.child("networks").child(wifiInfo.getBSSID()).setValue
					(wifiData));
		}
	}
