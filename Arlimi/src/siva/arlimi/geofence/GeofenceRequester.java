package siva.arlimi.geofence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import siva.arlimi.activity.R;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationClient.OnAddGeofencesResultListener;
import com.google.android.gms.location.LocationStatusCodes;

public class GeofenceRequester implements OnAddGeofencesResultListener,
								ConnectionCallbacks,
								OnConnectionFailedListener
{
	
	private Activity mActivity;
	
	private PendingIntent mGeofencePendingIntent;
	private ArrayList<Geofence> mCurrentGeofences;
	private LocationClient mLocationClient;
	
	private boolean mInProgress;
	
	public GeofenceRequester(Activity activity)
	{
		mActivity = activity;
		
		mGeofencePendingIntent = null;
		mLocationClient = null;
		mInProgress = false;
	}
	
	public void setInProgressFlag(boolean flag)
	{
		this.mInProgress = flag;
	}
	
	public PendingIntent getRequestPendingIntent()
	{
		return createRequestPendinngIntent();
	}
	
	public void addGeofences(List<Geofence> geofences)
	{
		mCurrentGeofences = (ArrayList<Geofence>) geofences;
		
		if(!mInProgress)
		{
			mInProgress = true;
			
			requestConnection();
		}
		else
		{
			throw new UnsupportedOperationException();
		}
		
	}
	
	private void requestConnection()
	{
		getLocationClient().connect();
	}
	
	private GooglePlayServicesClient getLocationClient()
	{
		if(null == mLocationClient)
			mLocationClient = new LocationClient(mActivity, this, this);
		
		return mLocationClient;
	}
	
	private void continueAddGeofences()
	{
		mGeofencePendingIntent = createRequestPendinngIntent();
		
		mLocationClient.addGeofences(mCurrentGeofences, mGeofencePendingIntent, this);
	}

	private PendingIntent createRequestPendinngIntent()
	{
		if( null != mGeofencePendingIntent  )
		{
			return mGeofencePendingIntent;
		}
		else
		{
			Intent intent = new Intent(mActivity, 
					ReceiveTransitionIntentService.class);
			/*
             * Return a PendingIntent to start the IntentService.
             * Always create a PendingIntent sent to Location Services
             * with FLAG_UPDATE_CURRENT, so that sending the PendingIntent
             * again updates the original. Otherwise, Location Services
             * can't match the PendingIntent to requests made with it.
             */
			return PendingIntent.getService(mActivity, 0, intent, 
					PendingIntent.FLAG_UPDATE_CURRENT);
			
		}
		
		
	}
	
	private void requestDisconnention()
	{
		mInProgress = false;
		
		getLocationClient().disconnect();
	}
	

	@Override
	public void onConnectionFailed(ConnectionResult result)
	{
		mInProgress = false;
		
		if(result.hasResolution())
		{
			try
			{
				result.startResolutionForResult(mActivity, 
						GeofenceUtils.CONNECTION_FAILURE_RESOLUTION_REQUEST);
			}
			catch(SendIntentException e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			Intent errorBroadcastIntent = new Intent(GeofenceUtils.ACTION_CONNECTION_ERROR);
			errorBroadcastIntent.addCategory(GeofenceUtils.CATEGORY_LOCATION_SERVICES)
								.putExtra(GeofenceUtils.EXTRA_CONNECTION_ERROR_CODE,
										result.getErrorCode());
			LocalBroadcastManager.getInstance(mActivity).sendBroadcast(errorBroadcastIntent);
		}
		
	}

	@Override
	public void onConnected(Bundle connectionHint)
	{
		Log.i(GeofenceUtils.APPTAG, mActivity.getString(R.string.connected));
		
		continueAddGeofences();
		
	}

	@Override
	public void onDisconnected()
	{
		mInProgress = false;
		
		Log.i(GeofenceUtils.APPTAG, mActivity.getString(R.string.disconnected));
		
		mLocationClient = null;
		
	}

	@Override
	public void onAddGeofencesResult(int statusCode, String[] geofenceRequestIds)
	{
		Intent broadcastIntent = new Intent();
		
		String msg;
		
		//If adding the geocodes was succesful
		if(LocationStatusCodes.SUCCESS == statusCode)
		{
			msg = mActivity.getString(R.string.add_geofences_result_success,
					Arrays.toString(geofenceRequestIds)
					);
			
			Log.i(GeofenceUtils.APPTAG, msg);
			
			//create an Intent to broadcast to the app
			broadcastIntent.setAction(GeofenceUtils.ACTION_GEOFENCE_ADDED)
							.addCategory(GeofenceUtils.CATEGORY_LOCATION_SERVICES)
							.putExtra(GeofenceUtils.EXTRA_GEOFENCE_STATUS, msg);
		}
		else
		{
			msg = mActivity.getString(
					R.string.add_geofences_result_failure,
					statusCode,
					Arrays.toString(geofenceRequestIds)
					);
			
			Log.i(GeofenceUtils.APPTAG, msg);
			
			broadcastIntent.setAction(GeofenceUtils.ACTION_CONNECTION_ERROR)
							.addCategory(GeofenceUtils.CATEGORY_LOCATION_SERVICES)
							.putExtra(GeofenceUtils.EXTRA_GEOFENCE_STATUS, msg);
		}
		
			
		LocalBroadcastManager.getInstance(mActivity).sendBroadcast(broadcastIntent);
		
		requestDisconnention();
		
	}


}
