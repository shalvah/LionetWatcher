package me.shalvah.lionetwatcher;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AlarmReceiver extends BroadcastReceiver
	{
		private Location location;


		public AlarmReceiver()
		{

		}

		@Override
		public void onReceive(Context context, Intent intent)
		{

			WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
			WifiInfo wi = wm.getConnectionInfo();
			if (containsAny(wi.getSSID(), "LIONET@", "Lionet@"))
			{
				LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
				Criteria criteria = new Criteria();
				criteria.setAccuracy(Criteria.ACCURACY_FINE);
				if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission
						.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat
						.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
				{
					// TODO: Consider calling
					//    ActivityCompat#requestPermissions
					// here to request the missing permissions, and then overriding
					//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
					//                                          int[] grantResults)
					// to handle the case where the user grants the permission. See the documentation
					// for ActivityCompat#requestPermissions for more details.
					return;
				}
				location = lm
						.getLastKnownLocation(lm.getBestProvider
								(criteria, true));

				Log.d("WIFI_SPEED", "" + wi.getLinkSpeed());
				storeWifiData(wi);
			} else
			{
				Log.d("WIFI_STOP ", "STOPPING_ALARM");

				int alarmId = intent.getExtras().getInt("alarmId");
				PendingIntent alarmPendingIntent;
				alarmPendingIntent = PendingIntent.getBroadcast(context, alarmId,
						new Intent(context, AlarmReceiver.class),
						0);

				AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
				alarmManager.cancel(alarmPendingIntent);
			}

		}

		private boolean containsAny(String haystack, String... needles)
		{
			for (String needle:
			     needles)
			{
				if(haystack.contains(needle))
				{
					return true;
				}
			}
			return false;
		}

		private void storeWifiData(final WifiInfo wifiInfo)
		{
			double currentLongitude = location.getLongitude();
			double currentLatitude = location.getLatitude();
			Log.d("WIFI_LOC", "Lat: " + currentLatitude + " Long: " + currentLongitude);

			final WifiData wifiData = new WifiData(wifiInfo);

			DatabaseReference dbr = FirebaseDatabase.getInstance().getReference();

			String locationKey = ""+ currentLatitude + "LAT|LONG" + currentLongitude;
			locationKey=locationKey.replaceAll("\\.", "-");
			Log.d("WIFI_WRITE_RESULT", "" + dbr.child("networks").child(wifiInfo.getSSID()).child
					(locationKey).setValue
					(wifiData));
		}

	}
