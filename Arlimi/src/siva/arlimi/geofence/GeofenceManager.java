package siva.arlimi.geofence;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationClient.OnAddGeofencesResultListener;
import com.google.android.gms.location.LocationStatusCodes;

public class GeofenceManager implements 
							ConnectionCallbacks,
							OnConnectionFailedListener,
							OnAddGeofencesResultListener
							
{
	public static final String TAG = "GeofenceManager";
	
	private final static int 
	CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;	
	
	private FragmentActivity mContext;
	private LocationClient mLocationClient;
	private PendingIntent mGeofenceRequestIntent;
	public enum REQUEST_TYPE {ADD};
	private REQUEST_TYPE mRequestType;
	
	private boolean mInProgress;
	
	private List<Geofence> mGeofenceList;
	
	public GeofenceManager(FragmentActivity context)
	{
		this.mContext = context;
		mInProgress = false;
		mGeofenceList = new ArrayList<Geofence>();
	}
	
	public void addGeofenceList(Geofence geofence)
	{
		mGeofenceList.add(geofence);
		
	}
	
	public void addGeofence()
	{
		mRequestType = REQUEST_TYPE.ADD;
		
		if(!serviceConnected())
		{
			Log.i(TAG, "Google service failed");
			return;
		}
		
		mLocationClient = new LocationClient(mContext, this, this);
		
		//If request is not already underway
		if(!mInProgress)
		{
			mInProgress = true;
			
			mLocationClient.connect();
		}
		else
		{
			//do something
		}
		
		
	}
		
	private boolean serviceConnected()
	{
		int resultCode = 
				GooglePlayServicesUtil.isGooglePlayServicesAvailable(mContext);
		
		if(ConnectionResult.SUCCESS == resultCode)
		{
			Log.i(TAG, "Goolge service is available");
			
			return true;
		}
		else
		{
			Dialog dialog = GooglePlayServicesUtil.getErrorDialog(resultCode, mContext, 0);
			if(dialog != null)
			{
				ErrorDialogFragment errorFragment = new ErrorDialogFragment();
				errorFragment.setDialog(dialog);
				errorFragment.show(mContext.getSupportFragmentManager(),TAG ); 
				
			}
			
			return false;
			
		}	
	}
	
	private PendingIntent getTransitionPendingIntent()
	{
		Intent intent = new Intent(mContext,ReceiveArlimiTransitionIntentService.class );
		
		return PendingIntent.getService(mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
	}

	@Override
	public void onAddGeofencesResult(int statusCode, String[] geofenceRequestIds)
	{
		if(LocationStatusCodes.SUCCESS == statusCode)
		{
			Log.i(TAG, "The Geofence is added successfully");
		}
		else
		{
			Log.i(TAG, "Fail to add the geofence");
		}
		
		mInProgress = false;
		mLocationClient.disconnect();
		
	}

	@Override
	public void onConnectionFailed(ConnectionResult result)
	{
		mInProgress = false;
		
		if(result.hasResolution())
		{
			try
			{
				result.startResolutionForResult(mContext, 
						CONNECTION_FAILURE_RESOLUTION_REQUEST);
			}
			catch(SendIntentException e)
			{
				e.printStackTrace();
			}
		}
		else // No Solution
		{
			int errorCode = result.getErrorCode();
			
			Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(
					errorCode, mContext, CONNECTION_FAILURE_RESOLUTION_REQUEST);
			
			if(null != errorDialog)
			{
				ErrorDialogFragment errorFragment = 
						new ErrorDialogFragment();
				errorFragment.setDialog(errorDialog);
				
				errorFragment.show(mContext.getSupportFragmentManager(), TAG);
				
			}
			
		}
		
	}

	@Override
	public void onConnected(Bundle connectionHint)
	{
		switch(mRequestType)
		{
		case ADD:
			mGeofenceRequestIntent = getTransitionPendingIntent();
			mLocationClient.addGeofences(mGeofenceList, mGeofenceRequestIntent, this);
			break;
		}
		
	}

	@Override
	public void onDisconnected()
	{
		mInProgress = false;
		mLocationClient = null;
	}
	
	public static class ErrorDialogFragment extends DialogFragment
	{
		private Dialog mDialog;
		
		public ErrorDialogFragment()
		{
			super();
			
			mDialog = null;
		}
		
		public void setDialog(Dialog dialog)
		{
			mDialog = dialog;
		}
		
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState)
		{
			return mDialog;
		}
		
	} // end of errordialog class

}
