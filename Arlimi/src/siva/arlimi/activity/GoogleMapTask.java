package siva.arlimi.activity;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class GoogleMapTask extends Activity implements 
GooglePlayServicesClient.ConnectionCallbacks,
GooglePlayServicesClient.OnConnectionFailedListener,LocationListener
{
	//TAG
	private static String TAG = "GoogleMapTask";

	//GoogleMap
	private static final int MILLISECONDS_PER_SECOND = 1000;
	public static final int UPDATE_INTERVAL_IN_SECONDS = 5;
	private static final long UPDATE_INTERVAL =
			MILLISECONDS_PER_SECOND * UPDATE_INTERVAL_IN_SECONDS;
	private static final int FASTEST_INTERVAL_IN_SECONDS = 1;
	private static final long FASTEST_INTERVAL =
			MILLISECONDS_PER_SECOND * FASTEST_INTERVAL_IN_SECONDS;
	private final static int CONNENCTION_FAILURE_RESOLUTION_REQUEST = 9000;

	// Google Map
	private GoogleMap googleMap;

	// Google Location
	private boolean mUpdatedRequested;
	LocationClient mLocationClient;
	Location mCurrentLocation;
	LocationRequest mLocationRequest;
	SharedPreferences.Editor mEditor;
	SharedPreferences mPrefs;

	private ProgressBar mActivityIndicator;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_owner_map);

		//Loading Map
		initilizeMap();

		//Location Client
		mLocationClient = new LocationClient(this, this, this);

		//Google Location
		mLocationRequest = LocationRequest.create();
		mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
		mLocationRequest.setInterval(UPDATE_INTERVAL);
		mLocationRequest.setFastestInterval(FASTEST_INTERVAL);

		mPrefs =getSharedPreferences("SharedPreferences",
				Context.MODE_PRIVATE);

		mEditor = mPrefs.edit();
		mUpdatedRequested = false;

		mActivityIndicator = (ProgressBar)findViewById(R.id.address_progress);
	}

	@Override
	protected void onStart()
	{
		super.onStart();
		mLocationClient.connect();
	}

	@Override
	protected void onStop()
	{
		// If the client is connected
		if (mLocationClient.isConnected()) {
			/*
			 * Remove location updates for a listener.
			 * The current Activity is the listener, so
			 * the argument is "this".
			 */
			//removeLocationUpdates(this);
		}

		mLocationClient.disconnect();
		super.onStop();
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		initilizeMap();

		/*
		 * Get any previous setting for location updates
		 * Gets "false" if an error occurs
		 */
		if (mPrefs.contains("KEY_UPDATES_ON")) {
			mUpdatedRequested =
					mPrefs.getBoolean("KEY_UPDATES_ON", false);

			// Otherwise, turn off location updates
		} else {
			mEditor.putBoolean("KEY_UPDATES_ON", false);
			mEditor.commit();
		}

	}

	@Override
	protected void onPause()
	{
		// Save the current setting for updates
		mEditor.putBoolean("KEY_UPDATES_ON", mUpdatedRequested);
		mEditor.commit();

		super.onPause();
	}

	@Override
	public void onLocationChanged(Location location)
	{
		// TODO Auto-generated method stub
		String msg = "Updated Location:" + 
				Double.toString(location.getLatitude()) + "," +
				Double.toString(location.getLongitude());
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
		
		Log.i(TAG, msg);

	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onConnected(Bundle arg0)
	{
		Log.i(TAG, "Google Play Services is connnected");

		// Display the connection status
		Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
		// If already requested, start periodic updates
		if (mUpdatedRequested) {
			mLocationClient.requestLocationUpdates(mLocationRequest, this);
		}


	}

	@Override
	public void onDisconnected()
	{
		Log.i(TAG, "Google Play Services is Disconnnected");
	}

	private void initilizeMap()
	{
		if (googleMap == null)
		{
			googleMap = ((MapFragment) getFragmentManager().findFragmentById(
					R.id.map)).getMap();

			// check if map is created successfully or not
			if (googleMap == null)
			{
				Toast.makeText(getApplicationContext(),
						"Sorry! unable to create maps", Toast.LENGTH_SHORT)
						.show();
			}
		}

		googleMap.setMyLocationEnabled(true);

	}

	private void moveToPosition(double latitude, double longitude)
	{
		CameraPosition cameraPosition = new CameraPosition.Builder().target(
				new LatLng(latitude, longitude)).zoom(18).build();

		googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

	}


	private void placeMakerAtCurrentPostion(double latitude, double longitude)
	{
		MarkerOptions marker = new MarkerOptions().position(
				new LatLng(latitude, longitude));

		googleMap.addMarker(marker);
	}


	public void getAddressFromLocation(Location mLocation) {
		// Ensure that a Geocoder services is available
		if (Build.VERSION.SDK_INT >=
				Build.VERSION_CODES.GINGERBREAD
				&&
				Geocoder.isPresent()) {
			// Show the activity indicator
			mActivityIndicator.setVisibility(View.VISIBLE);
			/*
			 * Reverse geocoding is long-running and synchronous.
			 * Run it on a background thread.
			 * Pass the current location to the background task.
			 * When the task finishes,
			 * onPostExecute() displays the address.
			 */
			(new GetAddressTask(this)).execute(mLocation);
		}
	}
	
	

	/*
	=============================================================
	
	GetAddresTask Class
	
	=============================================================
	 */
	private class GetAddressTask extends AsyncTask<Location, Void, String>
	{
		Context mContext;

		public GetAddressTask(Context context)
		{
			super();
			mContext = context;

		}

		@Override
		protected String doInBackground(Location... params)
		{
			Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
			Location loc = params[0];

			List<Address> addresses = null;

			try
			{
				addresses = geocoder.getFromLocation(loc.getLatitude(),
						loc.getLongitude(), 1);
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (addresses != null && addresses.size() > 0) {
				// Get the first address
				Address address = addresses.get(0);
				/*
				 * Format the first line of address (if available),
				 * city, and country name.
				 */
				String addressText = String.format(
						"%s, %s, %s",
						// If there's a street address, add it
						address.getMaxAddressLineIndex() > 0 ?
								address.getAddressLine(0) : "",
								// Locality is usually a city
								address.getLocality(),
								// The country of the address
								address.getCountryName());
				// Return the text
				return addressText;
			} else {
				return "No address found";
			}
		}

		@Override
		protected void onPostExecute(String result)
		{
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			mActivityIndicator.setVisibility(View.GONE);

			//mCurrentAddressTextView.setText(result);

			Log.i(TAG, result);
		}
	}



}
