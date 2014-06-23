package siva.arlimi.geofence;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import siva.arlimi.activity.R;
import siva.arlimi.geofence.GeofenceUtils.REMOVE_TYPE;
import siva.arlimi.geofence.GeofenceUtils.REQUEST_TYPE;
import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.Geofence;

public class GeofenceManager
{
	private static final long GEOFENCE_EXPIRATION_IN_HOURS = 12;
	private static final long GEOFENCE_EXPIRATION_IN_MILLISECONDS =
				GEOFENCE_EXPIRATION_IN_HOURS * DateUtils.HOUR_IN_MILLIS;
	
	private Context mContext;
	
	private REQUEST_TYPE mRequestType;
	private REMOVE_TYPE mRemoveType;
	private GeofenceStore mPrefs;
	
	private List<Geofence> mCurrentGeofences;

	private GeofenceRequester mGeofenceRequester;
	private GeofenceRemover mGeofenceRemover;
	
	private ArlimiGeofence mGeofence;
	
	private DecimalFormat mLatLngFormat;
	private DecimalFormat mRadiusFormat;
	
	private GeofenceReceiver mBroadcastReceiver;
	private IntentFilter mIntentFilter;
	
	private List<String> mGeofenceIdsToRemove;
	
	private double mLatitude;
	private double mLongitude;
	
	public GeofenceManager(Context context)
	{
		this.mContext = context;
		
		String latLngPattern =mContext.getString(R.string.lat_lng_pattern);
		
		mLatLngFormat = new DecimalFormat(latLngPattern);
		mLatLngFormat.applyLocalizedPattern(mLatLngFormat.toLocalizedPattern());
		
		mBroadcastReceiver = new GeofenceReceiver();
		mIntentFilter = new IntentFilter();
		
		mIntentFilter.addAction(GeofenceUtils.ACTION_GEOFENCE_ADDED);
		mIntentFilter.addAction(GeofenceUtils.ACTION_GEOFENCE_REMOVED);
		mIntentFilter.addAction(GeofenceUtils.ACTION_GEOFENCE_ERROR);
		
		mIntentFilter.addCategory(GeofenceUtils.CATEGORY_LOCATION_SERVICES);
		
		mPrefs = new GeofenceStore(mContext);
		
		mCurrentGeofences = new ArrayList<Geofence>();
		mGeofenceRequester = new GeofenceRequester((Activity)mContext);
		mGeofenceRemover = new GeofenceRemover((Activity)mContext);
		
	}
	
	public void onResult(int requestCode, int resultCode, Intent intent)
	{
		switch(requestCode)
		{
		case GeofenceUtils.CONNECTION_FAILURE_RESOLUTION_REQUEST:
			
			switch(resultCode)
			{
			case Activity.RESULT_OK:
				
				if(GeofenceUtils.REQUEST_TYPE.ADD == mRequestType)
				{
					mGeofenceRequester.setInProgressFlag(false);
					mGeofenceRequester.addGeofences(mCurrentGeofences);
					
				}
				else if(GeofenceUtils.REQUEST_TYPE.REMOVE == mRequestType)
				{
					mGeofenceRemover.setInProgressFlag(false);
					
					if(GeofenceUtils.REMOVE_TYPE.INTENT == mRemoveType)
					{
						mGeofenceRemover.removeGeofencesByIntent(
								mGeofenceRequester.getRequestPendingIntent()
								);
					}
					else
					{
						mGeofenceRemover.removeGeofencesById(mGeofenceIdsToRemove);
						
					}
				}
				break;
				
				default:
					Log.i(GeofenceUtils.APPTAG, mContext.getString(R.string.no_resolution));
					break;
			}
			
			default:
				Log.i(GeofenceUtils.APPTAG, 
						mContext.getString(R.string.unknown_activity_request_code,requestCode));
				break;
		
		}
	}
	
	public void onResume()
	{
		//Register the broadcast receiver
		LocalBroadcastManager.getInstance(mContext).registerReceiver(mBroadcastReceiver, mIntentFilter);
		
		mGeofence = mPrefs.getGeofence("1");
		
		if(mGeofence != null)
		{
			mLatitude = mGeofence.getmLatitude();
			mLongitude = mGeofence.getmLongitude();
		}
		
	}
	
	public void onPause()
	{
		mPrefs.setGeofence("1", mGeofence);
	}
	
	public void registerGeofence()
	{
		
		mRequestType = GeofenceUtils.REQUEST_TYPE.ADD;
		
		mLatitude = 37.4784472;
		mLongitude = 127.1198874;
		
		
		mGeofence = new ArlimiGeofence(
				"1",
				mLatitude,
				mLongitude,
				10,
				GEOFENCE_EXPIRATION_IN_MILLISECONDS,
				Geofence.GEOFENCE_TRANSITION_ENTER |
				Geofence.GEOFENCE_TRANSITION_EXIT
				);
		
		mPrefs.setGeofence("1", mGeofence);
		
		mCurrentGeofences.add(mGeofence.toGeofence());
		
		try
		{
		mGeofenceRequester.addGeofences(mCurrentGeofences);
		}
		catch (UnsupportedOperationException e)
		{
			Toast.makeText(mContext, R.string.add_geofences_already_requested_error, Toast.LENGTH_LONG).show();
			
		}
	}
	
	public class GeofenceReceiver extends BroadcastReceiver
	{
		@Override
		public void onReceive(Context context, Intent intent)
		{
			String action = intent.getAction();
			
			if(TextUtils.equals(action, GeofenceUtils.ACTION_CONNECTION_ERROR))
			{
				handleGeofenceError(context, intent);
			}
			else if(TextUtils.equals(action, GeofenceUtils.ACTION_GEOFENCE_ADDED)
					||
					TextUtils.equals(action, GeofenceUtils.ACTION_GEOFENCE_REMOVED)
					)
			{
				handleGeofenceStatus(context, intent);
			}
			else if(TextUtils.equals(action, GeofenceUtils.ACTION_GEOFENCE_TRANSITION))
			{
				handleGeofenceTransition(context, intent);
				
			}
			else
			{
				Log.e(GeofenceUtils.APPTAG, 
						mContext.getString(R.string.invalid_action_detail,action));
				Toast.makeText(context, R.string.invalid_action, Toast.LENGTH_LONG).show();
			}
		}

		private void handleGeofenceTransition(Context context, Intent intent)
		{
			// TODO Auto-generated method stub
			
		}

		private void handleGeofenceStatus(Context context, Intent intent)
		{
			// TODO Auto-generated method stub
			
		}

		private void handleGeofenceError(Context context, Intent intent)
		{
			String msg = intent.getStringExtra(GeofenceUtils.EXTRA_GEOFENCE_STATUS);
			Log.e(GeofenceUtils.APPTAG, msg);
		}
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
		
	
	}
	
	
	

}
