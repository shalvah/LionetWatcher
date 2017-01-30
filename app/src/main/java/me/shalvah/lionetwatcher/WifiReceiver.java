package me.shalvah.lionetwatcher;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.SystemClock;
import android.util.Log;


public class WifiReceiver extends BroadcastReceiver
	{

		public static final long POLL_INTERVAL = SystemClock.elapsedRealtime() + 30 * 1000;

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

				AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
				PendingIntent alarmPendingIntent;

				int alarmId = (int) (Math.random() * 100);
				Intent alarmIntent = new Intent(context, AlarmReceiver.class);
				intent.putExtra("alarmId", alarmId);
				alarmPendingIntent = PendingIntent.getBroadcast(context, alarmId, alarmIntent,
						0);

				alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME,
						SystemClock.elapsedRealtime() - 600*1000,
						POLL_INTERVAL,
						alarmPendingIntent);

			}
		}

	}
