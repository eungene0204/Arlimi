package siva.arlimi.location;

import siva.arlimi.main.R;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
//import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

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
	
	private final Context mContext;
	private final GoogleMap mGoogleMap;
	//private LocationClient mLocationClient;
	private LocationRequest mLocationRequest;

	boolean mUpdatesRequested = false;
	
	private double mCurrentLat;
	private double mCurrentLng;
	
	
	public ArlimiLocationClient(Context context, GoogleMap map )
	{
		this.mContext = context;
	//	this.mLocationClient = new LocationClient(mContext, this, this);
		this.mLocationRequest = LocationRequest.create();
		
		mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
		mLocationRequest.setInterval(UPDATE_INTERVAL);
		mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
		
		mUpdatesRequested = false;
		
		mGoogleMap = map;
		
	}
	
	public void setCurrentPosition(double latitude, double longitude )
	{
		mCurrentLat = latitude;
		mCurrentLng = longitude;
	}
	
	public LatLng getCurrentPosition()
	{
		return new LatLng(mCurrentLat, mCurrentLng);
		
	}
	

	public void setUpdatesRequested(boolean val)
	{
		this.mUpdatesRequested = val;
	}
	
	public boolean getUpdatesRequested()
	{
		return this.mUpdatesRequested;
	}
	

	public void connect()
	{
//		mLocationClient.connect();
	}
	
	public void disconnect()
	{
//		mLocationClient.disconnect();
	}
	
	public Location getLastLocation()
	{
//		return mLocationClient.getLastLocation();
		return null;
	}
	
	public boolean isConnected()
	{
//		return mLocationClient.isConnected();
		return false;
	}
	
	public void removeLocationUpdates()
	{
//		mLocationClient.removeLocationUpdates(this);
	}
	
	public void moveCamera(LatLng position)
	{
		CameraPosition cameraPosition = CameraPosition.builder().target(
				position).zoom(18).build();
		
		mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
		addMarker(position);
		
	}
	
	public void addMarker(LatLng position)
	{
		MarkerOptions marker = new MarkerOptions().position(position);
	
		marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_action_place));
		
		mGoogleMap.addMarker(marker);
		
	}

	@Override
	public void onConnectionFailed(ConnectionResult connectionResult)
	{
	
	}

	@Override
	public void onConnected(Bundle connectionHint)
	{
		
		if(mUpdatesRequested)
		{
			//mLocationClient.requestLocationUpdates(mLocationRequest, this);
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
