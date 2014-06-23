package siva.arlimi.location;

import siva.arlimi.activity.R;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;

public class ArlimiLocationClient implements 
		GooglePlayServicesClient.ConnectionCallbacks,
		GooglePlayServicesClient.OnConnectionFailedListener,
		LocationListener

{

	private static final int MILLISECONDS_PER_SECOND = 1000;
	public  static final int UPDATE_INTERVAL_IN_SECONDS = 5;
	private static final long UPDATE_INTERVAL =
			MILLISECONDS_PER_SECOND * UPDATE_INTERVAL_IN_SECONDS;
	private static final int FASTEST_INTERVAL_IN_SECONDS = 1;
	private static final long FASTEST_INTERVAL =
			MILLISECONDS_PER_SECOND * FASTEST_INTERVAL_IN_SECONDS;
	
	private Context mContext;
	private LocationClient mLocationClient;
	private LocationRequest mLocationRequest;

	boolean mUpdatesRequested = false;
	
	public ArlimiLocationClient(Context context)
	{
		this.mContext = context;
		this.mLocationClient = new LocationClient(mContext, this, this);
		this.mLocationRequest = LocationRequest.create();
		
		mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
		mLocationRequest.setInterval(UPDATE_INTERVAL);
		mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
		
		mUpdatesRequested = false;
		

	}
	

	public void setUpdatesRequested(boolean val)
	{
		this.mUpdatesRequested = val;
	}
	
	public boolean getUpdatesRequested()
	{
		return this.mUpdatesRequested;
	}

	public LocationClient getLocationClient()
	{
		return this.mLocationClient;
	}
	

	public void connect()
	{
		mLocationClient.connect();
	}
	
	public void disconnect()
	{
		mLocationClient.disconnect();
	}
	
	public Location getLastLocation()
	{
		return mLocationClient.getLastLocation();
	}
	
	public boolean isConnected()
	{
		return mLocationClient.isConnected();
	}
	
	public void removeLocationUpdates()
	{
		mLocationClient.removeLocationUpdates(this);
	}

	@Override
	public void onConnectionFailed(ConnectionResult result)
	{
		
		Toast.makeText(mContext, "Service fail", Toast.LENGTH_LONG).show();	
	}

	@Override
	public void onConnected(Bundle connectionHint)
	{
		Toast.makeText(mContext, "Service connected", Toast.LENGTH_SHORT).show();
		
		if(mUpdatesRequested)
		{
			mLocationClient.requestLocationUpdates(mLocationRequest, this);
		}
	}

	@Override
	public void onDisconnected()
	{
		Toast.makeText(mContext, "Service disconnected", Toast.LENGTH_LONG).show();
	}

	@Override
	public void onLocationChanged(Location location)
	{
		String msg = "Update Location" +
				Double.toString(location.getLatitude()) + "," +
				Double.toString(location.getLongitude());
		
	//	Toast.makeText(mContext, msg, Toast.LENGTH_LONG).show();
		
	}

}
