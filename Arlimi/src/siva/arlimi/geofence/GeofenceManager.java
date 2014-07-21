package siva.arlimi.geofence;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import siva.arlimi.event.Event;
import siva.arlimi.event.EventUtil;
import siva.arlimi.geofence.ReceiveArlimiTransitionIntentService.LocalBinder;
import siva.arlimi.networktask.NetworkURL;
import siva.arlimi.networktask.ReadEventListConnection;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
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
	
	public String[] getEventIds()
	{
		return mBoundService.getEventIds();
	}
	
	private ReceiveArlimiTransitionIntentService mBoundService;
	private boolean mIsBound = false;
	private boolean mIsConnedtedService = false;
	
	private ServiceConnection mConnection = new ServiceConnection()
	{
		@Override
		public void onServiceDisconnected(ComponentName name)
		{
			mBoundService = null;
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service)
		{
			Log.i(TAG, "onServiceConnected");
			
			LocalBinder binder = (LocalBinder)service;
			mBoundService = binder.getService();
			
			mIsConnedtedService = true;
			
		}
	};
	
	public boolean getIsServiceConnected()
	{
		return this.mIsConnedtedService;
	}
	
	public void doBindService()
	{
		Intent intent = 
				new Intent(mContext, ReceiveArlimiTransitionIntentService.class);
		
		if(mContext.bindService(intent, mConnection, Context.BIND_AUTO_CREATE))
		{
			Log.i(TAG, "binding successed");
		}
		else
		{
			Log.i(TAG, "fail to connect to the service");
		}
		
		mIsBound = true;
	}
	
	public void doUnbindService()
	{
		if(mIsBound)
		{
			mContext.unbindService(mConnection);
			mIsBound = false;
		}
	}
	
	public void readGeofenceFromDB()
	{
		ReadEventListConnection conn = new ReadEventListConnection();
		conn.setURL(NetworkURL.READ_EVENT_LIST_FROM_DB);
		conn.setData(null);
		
		try
		{
			String result = conn.execute().get();
			
			JSONArray jsonList= new JSONArray(result.toString());
                 
                 for(int i = 0; i < jsonList.length(); i++)
                 {
                	 	// need to do Refactoring 
                	 	// Builder pattern
                        JSONObject obj = jsonList.getJSONObject(i);
                       
                        String id = obj.getString(EventUtil.EVENT_ID);
                        String email = obj.getString(EventUtil.EMAIL);
                        String contents = obj.getString(EventUtil.EVENT_CONTENTS);
                        String latitude = obj.getString(EventUtil.EVENT_LATITUDE);
                        String longitude = obj.getString(EventUtil.EVENT_LONGITUDE);
                        
                        ArlimiGeofence geofence = new ArlimiGeofence(id, 
                        		Double.valueOf(latitude), Double.valueOf(longitude),
                        		100, Geofence.NEVER_EXPIRE, 
                        		Geofence.GEOFENCE_TRANSITION_ENTER | 
                        		Geofence.GEOFENCE_TRANSITION_EXIT);
                        
                        addGeofenceList(geofence.toGeofence());
              
                        Log.i(TAG, id);
                        Log.i(TAG, email);
                        Log.i(TAG, contents); 
                        Log.i(TAG, "Latitude: " + latitude);
                        Log.i(TAG, "Longitude: " + longitude);
                        
                 }
		} 
		catch (JSONException e)
		{
			e.printStackTrace();
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
	
	public void addGeofence()
	{
		mRequestType = REQUEST_TYPE.ADD;
		
		if(!googlePlayserviceConnected())
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
		
	private boolean googlePlayserviceConnected()
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
			
			if(!mGeofenceList.isEmpty())
				mLocationClient.addGeofences(mGeofenceList, mGeofenceRequestIntent, this);
			else
				return;
			
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
