package siva.arlimi.geofence;

import java.util.ArrayList;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import siva.arlimi.event.Event;
import siva.arlimi.event.EventList;
import siva.arlimi.event.EventUtil;
import siva.arlimi.geofence.GeofenceServiceBinder.EventListener;
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
							OnAddGeofencesResultListener,
							EventListener
							
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
	
	private EventListListener mEventListListener;

	public GeofenceManager(FragmentActivity context)
	{
		this.mContext = context;
		mInProgress = false;
		mGeofenceList = new ArrayList<Geofence>();
		mBinder = new GeofenceServiceBinder(context);
		mBinder.registerEventListener(this);
	}
	
	public void registerEventListListener(EventListListener listener)
	{
		this.mEventListListener = listener;
	}
	
	public void addGeofenceList(Geofence geofence)
	{
		mGeofenceList.add(geofence);
	}
	
	public GeofenceServiceBinder getBinder()
	{
		return mBinder;
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
			e.printStackTrace();
		} catch (ExecutionException e)
		{
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
	

	public EventList readEventListById()
	{
		if(mEventIds == null)
			return new EventList();
		
		EventList eventList = new EventList();
	
		ReadEventListByIDConnection conn = new ReadEventListByIDConnection();
		conn.setURL(NetworkURL.READ_EVENT_BY_ID);
		conn.setEventIds(mEventIds);
		try
		{
			final String result = (String) conn.execute().get();
			Log.i(TAG, "Result is " + result);
			JSONArray jsonResult = parseStringtoJSON(result);
			
			eventList = parseJSONArrayToEventList(jsonResult);
			
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		} catch (ExecutionException e)
		{
			e.printStackTrace();
		} catch (JSONException e)
		{
			e.printStackTrace();
		}

		Log.i(TAG, "readEventListById");

		
		return eventList;
	}
	
	private static EventList parseJSONArrayToEventList(final JSONArray array) throws JSONException
	{
		EventList eventList = new EventList();
		
		if(null == array)
			return eventList;
		
		final int length = array.length();
		
		for(int i = 0; i < length; i++)
		{
			JSONObject json = array.getJSONObject(i);
			final String id = json.getString(EventUtil.EVENT_ID);
			final String contents = json.getString(EventUtil.EVENT_CONTENTS);
			final String email = json.getString(EventUtil.EMAIL);
			final String latitude = json.getString(EventUtil.EVENT_LATITUDE);
			final String longitude = json.getString(EventUtil.EVENT_LONGITUDE);
			
			Event event = new Event();
			event.setEmail(email);
			event.setContents(contents);
			event.setId(id);
			event.setLatitude(latitude);
			event.setLongitude(longitude);
			
			eventList.addEvent(event);
		}
		
		return eventList;
	}

	

	private static JSONArray parseStringtoJSON(String str) throws JSONException
	{
		if(str == null)
			new JSONArray();
		
		JSONArray jsonArray = new JSONArray(str); 
		
		return jsonArray;
	}
	

	@Override
	public void onNewEventIDs(String[] eventIds)
	{
		Log.i(TAG, "New Event!!");
		mEventIds = eventIds;
		EventList eventList = readEventListById();
		mEventListListener.onNewEventList(eventList);
	}
	
	public interface EventListListener
	{
		void onNewEventList(EventList eventList);
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
