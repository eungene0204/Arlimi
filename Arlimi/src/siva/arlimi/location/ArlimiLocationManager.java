package siva.arlimi.location;

import java.util.concurrent.ExecutionException;

import siva.arlimi.networktask.NetworkConnection;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

public class ArlimiLocationManager extends Service implements LocationListener
{
	public static final String TAG = "ArlimiLocationManager";
	
	private Context mContext;
	private LocationManager mManager; 
	
	private boolean isGPSEnabled = false;
	private boolean isNetWorkEnabled = false;
	private boolean canGetLocation = false;
	
	private Location location;
	private PendingIntent mPending;
	private double mLatitude;
	private double mLongitude;
	
	
	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 0;
	private static final long MIN_TIME_BW_UPDATES = 0;//1000 * 60 * 1;
	private static final long POINT_RADIUS = 500;
	private static final long PROX_ALERT_EXPIRATION = -1;
	
	public ArlimiLocationManager(Context context)
	{
		this.mContext = context;
		mManager = (LocationManager) 
			mContext.getSystemService(Context.LOCATION_SERVICE);
		
		getCurrentLocation();
	}
	
	public String getAddress() throws InterruptedException, ExecutionException
	{
		StringBuilder url = new StringBuilder
				("https://maps.googleapis.com/maps/api/geocode/json?");
		url.append("address=서울특별시+송파구+문정2동+408-3");
		url.append("&sensor=true");
		
		NetworkConnection conn = new NetworkConnection();
		conn.setURL(url.toString());
		String result = conn.execute().get();
		
		return result;
	}
	
	public void addProximity(double latitude, double longitude)
	{
		Intent intent = new Intent(mContext, EventReceiver.class);
		mPending = PendingIntent.getBroadcast(mContext, 0, intent, 0);
		
		mManager.addProximityAlert(latitude, longitude, POINT_RADIUS, PROX_ALERT_EXPIRATION, mPending);
	}
	
	public void removeProximityAlert()
	{
		mManager.removeProximityAlert(mPending);
	}

	private Location getCurrentLocation()
	{
		isGPSEnabled = 
				mManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		
		isNetWorkEnabled = 
				mManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		
		if(!isGPSEnabled && !isNetWorkEnabled)
		{
			//No connection available
		}
		else
		{
				
			canGetLocation = true;
			if(isGPSEnabled)
			{
				
				if(location == null)
				{
					mManager.requestLocationUpdates(
							LocationManager.GPS_PROVIDER, 
							MIN_TIME_BW_UPDATES,
							MIN_DISTANCE_CHANGE_FOR_UPDATES, 
							this);
					
					Log.i(TAG, "GPS enabled");
					
					if(mManager != null)
					{
						location =
								mManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
						
						if(location != null)
						{
							mLatitude = location.getLatitude();
							mLongitude = location.getLongitude();
						}
						
					}
				}
			}
			
			if(isNetWorkEnabled)
			{
				canGetLocation = true;
				
				mManager.requestLocationUpdates(
						LocationManager.NETWORK_PROVIDER,
						MIN_TIME_BW_UPDATES,
						MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
				Log.i(TAG, "Network is available");
				
				if(mManager != null)
				{
					location = mManager
							.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
					
					if(location != null)
					{
						mLatitude = location.getLatitude();
						mLongitude = location.getLongitude();
					}
				}
			}
			
		}
		
		return location;
		
	}
	
	public void stopUsingGPS()
	{
		if(mManager != null)
		{
			mManager.removeUpdates(ArlimiLocationManager.this);
		}
	}
	
	public double getLatitude()
	{
		if(location != null)
		{
			mLatitude = location.getLatitude();
		}
		
		return mLatitude;
	}
	
	public double getLongitude()
	{
		if(location != null)
		{
			mLongitude = location.getLongitude();
		}
		
		return mLongitude;
	}
	
	public boolean canGetLocation()
	{
		return this.canGetLocation;
	}
	
	public void showSettingAlert()
	{
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
		
		alertDialog.setTitle("GPS is settings");
		
		alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
            	Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            	mContext.startActivity(intent);
            }
        });
 
        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
            }
        });
 
        // Showing Alert Message
        alertDialog.show();
	}

	@Override
	public void onLocationChanged(Location location)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String provider)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public IBinder onBind(Intent arg0)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	


}
