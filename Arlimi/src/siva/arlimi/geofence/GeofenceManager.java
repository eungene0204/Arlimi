package siva.arlimi.geofence;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import siva.arlimi.event.EventList;
import siva.arlimi.event.EventUtil;
import siva.arlimi.networktask.NetworkURL;
import siva.arlimi.networktask.ReadEventListByIDConnection;
import siva.arlimi.networktask.ReadEventListConnection;
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
	
	private LocationClient mLocationClient;
	private PendingIntent mGeofenceRequestIntent;
	public enum REQUEST_TYPE {ADD};
	private REQUEST_TYPE mRequestType;

	private FragmentActivity mContext;
	private boolean mInProgress;
		
	private List<Geofence> mGeofenceList;
	private GeofenceServiceBinder mBinder;
	
	private String[] mEventIds;


	public GeofenceManager(FragmentActivity context)
	{
		this.mContext = context;
		mInProgress = false;
		mGeofenceList = new ArrayList<Geofence>();
		mBinder = new GeofenceServiceBinder(context);
	}
	
	public void addGeofenceList(Geofence geofence)
	{
		mGeofenceList.add(geofence);
	}
	
	public GeofenceServiceBinder getBinder()
	{
		return mBinder;
		
	}
	
	public EventList readEventListByID()
	{
		String[] eventIds = mBinder.getEventIds();
		EventList eventList = new EventList();
		if(eventIds.length != 0)
		{
		ReadEventListByIDConnection conn = new ReadEventListByIDConnection();
		conn.setURL(NetworkURL.READ_EVENT_BY_ID);
		conn.setEventIds(mBinder.getEventIds());
		conn.execute();
		
		return eventList;
		}
		else
		{
			return eventList;
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
                        
                        /*
                        Log.i(TAG, id);
                        Log.i(TAG, email);
                        Log.i(TAG, contents); 
                        Log.i(TAG, "Latitude: " + latitude);
                        Log.i(TAG, "Longitude: " + longitude); */
                 
                        
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

	public EventList readEventListById()
	{
		EventList eventList = new EventList();
	
		ReadEventListByIDConnection conn = new ReadEventListByIDConnection();
		conn.setURL(NetworkURL.READ_EVENT_BY_ID);
		conn.setEventIds(mBinder.getEventIds());
		conn.execute();
		
		return eventList;
	}

}
