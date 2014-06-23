package siva.arlimi.geofence;

import java.util.Arrays;
import java.util.List;

import siva.arlimi.activity.R;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationClient.OnRemoveGeofencesResultListener;
import com.google.android.gms.location.LocationStatusCodes;

public class GeofenceRemover implements
					ConnectionCallbacks,
					OnConnectionFailedListener,
					OnRemoveGeofencesResultListener

{
	
	private Context mContext;
	private List<String> mCurrentGeofenceIds;
	private LocationClient mLocationClient;
	private PendingIntent mCurrentIntent;
	
	private GeofenceUtils.REMOVE_TYPE mRequestType;
	
	private boolean mInProgress;
	
	public GeofenceRemover(Context context)
	{
		mContext = context;
		
		mCurrentGeofenceIds = null;
		mLocationClient = null;
		mInProgress = false;
	}
	
	public void setInProgressFlag(boolean flag)
	{
		this.mInProgress = flag;
	}
	
	public boolean getInProgressFlag()
	{
		return this.mInProgress;
	}
	
	public void removeGeofencesById(List<String> geofenceIds) throws
	IllegalArgumentException, UnsupportedOperationException
	{
		if((null == geofenceIds) || (0 == geofenceIds.size()))
		{
			throw new IllegalArgumentException();
		}
		else
		{
			if(!mInProgress)
			{
				mRequestType = GeofenceUtils.REMOVE_TYPE.LIST;
				mCurrentGeofenceIds = geofenceIds;
				requestConnection();
				
			}
			else
			{
				throw new UnsupportedOperationException();
			}
			
		}
	}
	
	public void removeGeofencesByIntent(PendingIntent requestIntent)
	{
		if(!mInProgress)
		{
			mRequestType = GeofenceUtils.REMOVE_TYPE.INTENT;
			mCurrentIntent = requestIntent;
			requestConnection();
		}
		else
		{
			throw new UnsupportedOperationException();
		}
	}
	
	private void continueRemoveGeofences()
	{
		switch(mRequestType)
		{
		case INTENT:
			mLocationClient.removeGeofences(mCurrentIntent, this);
			break;
			
		case LIST:
			mLocationClient.removeGeofences(mCurrentGeofenceIds, this);
			break;
		
		}
	}
	

	private void requestConnection()
	{
		getLocationClient().connect();
	}


	private GooglePlayServicesClient getLocationClient()
	{
		if(null == mLocationClient)
		{
			mLocationClient = new LocationClient(mContext,this,this);
		}
		
		return mLocationClient;
	}

	@Override
	public void onRemoveGeofencesByPendingIntentResult(int statusCode,
			PendingIntent pendingIntent)
	{
		Intent broadcastIntent = new Intent();
		
		if(statusCode == LocationStatusCodes.SUCCESS)
		{
			Log.i(GeofenceUtils.APPTAG, 
					mContext.getString(R.string.remove_geofences_intent_success));
			
			broadcastIntent.setAction(GeofenceUtils.ACTION_GEOFENCE_REMOVED);
			broadcastIntent.putExtra(GeofenceUtils.EXTRA_GEOFENCE_STATUS, 
					mContext.getString(R.string.remove_geofences_intent_success));
		}
		else
		{
			Log.e(GeofenceUtils.APPTAG, 
					mContext.getString(R.string.remove_geofences_id_failure, statusCode));
			
			broadcastIntent.setAction(GeofenceUtils.ACTION_GEOFENCE_ERROR);
			broadcastIntent.putExtra(GeofenceUtils.EXTRA_GEOFENCE_STATUS, 
					mContext.getString(R.string.remove_geofences_id_failure, statusCode));
			
		}
		
		LocalBroadcastManager.getInstance(mContext).sendBroadcast(broadcastIntent);
		
		requestDisconection();
		
	}


	@Override
	public void onRemoveGeofencesByRequestIdsResult(int statusCode,
			String[] geofenceRequestIds)
	{
		Intent broadcastIntent = new Intent();
		
		String msg;
		
		if(LocationStatusCodes.SUCCESS == statusCode)
		{
			msg = mContext.getString(R.string.remove_geofences_id_success,
					Arrays.toString(geofenceRequestIds));
			
			Log.i(GeofenceUtils.APPTAG, msg);
			
			broadcastIntent.setAction(GeofenceUtils.ACTION_GEOFENCE_ERROR)
							.addCategory(GeofenceUtils.CATEGORY_LOCATION_SERVICES)
							.putExtra(GeofenceUtils.EXTRA_GEOFENCE_STATUS, msg);
			
		}
		else
		{
			msg = mContext.getString(R.string.remove_geofences_intent_failure,
					statusCode,
					Arrays.toString(geofenceRequestIds));
			
			Log.e(GeofenceUtils.APPTAG, msg);
			
			broadcastIntent.setAction(GeofenceUtils.ACTION_GEOFENCE_ERROR)
							.addCategory(GeofenceUtils.CATEGORY_LOCATION_SERVICES)
							.putExtra(GeofenceUtils.EXTRA_GEOFENCE_STATUS, msg);
			
		}
		
		LocalBroadcastManager.getInstance(mContext).sendBroadcast(broadcastIntent);
		
		requestDisconection();
		
	}

	private void requestDisconection()
	{
		mInProgress = false;
		
		getLocationClient().disconnect();
		
		if(mRequestType == GeofenceUtils.REMOVE_TYPE.INTENT)
		{
			mCurrentIntent.cancel();
		}
	}

	@Override
	public void onConnectionFailed(ConnectionResult result)
	{
		mInProgress = false;
		
		if(result.hasResolution())
		{
			try
			{
				result.startResolutionForResult((Activity)mContext, 
						GeofenceUtils.CONNECTION_FAILURE_RESOLUTION_REQUEST);
				
			} 
			catch (SendIntentException e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			Intent errorBroadcastIntent = new Intent(GeofenceUtils.ACTION_GEOFENCE_ERROR);
			errorBroadcastIntent.addCategory(GeofenceUtils.CATEGORY_LOCATION_SERVICES)
								.putExtra(GeofenceUtils.EXTRA_CONNECTION_ERROR_CODE, 
										result.getErrorCode());
			LocalBroadcastManager.getInstance(mContext).sendBroadcast(errorBroadcastIntent);
		}
		
	}

	@Override
	public void onConnected(Bundle connectionHint)
	{
		Log.i(GeofenceUtils.APPTAG, mContext.getString(R.string.connected));
		
		continueRemoveGeofences();
		
	}

	@Override
	public void onDisconnected()
	{
		// TODO Auto-generated method stub
		
	}

}
