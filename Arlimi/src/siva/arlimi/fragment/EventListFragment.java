package siva.arlimi.fragment;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import siva.arlimi.activity.R;
import siva.arlimi.event.Event;
import siva.arlimi.event.EventList;
import siva.arlimi.event.EventUtil;
import siva.arlimi.geofence.ArlimiGeofence;
import siva.arlimi.geofence.GeofenceManager;
import siva.arlimi.geofence.ReceiveArlimiTransitionIntentService;
import siva.arlimi.geofence.ReceiveArlimiTransitionIntentService.LocalBinder;
import siva.arlimi.networktask.NetworkURL;
import siva.arlimi.networktask.ReadEventListByIDConnection;
import siva.arlimi.networktask.ReadEventListConnection;
import siva.arlimi.widget.EventCardList;
import siva.arlimi.widget.EventCardWidget;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.google.android.gms.location.Geofence;

public class EventListFragment extends Fragment
{
	public static final String TAG = "EventListFragment";
	
	private GeofenceManager mGeofenceManager;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		Log.i(TAG, "onCreateView");
		
		View root = inflater.inflate(R.layout.fragment_event_list, container, false);
	
		mGeofenceManager = new GeofenceManager(getActivity());
		mGeofenceManager.doBindService();
	
		/*
		if(mGeofenceManager.getIsServiceConnected())
		{
			Log.i(TAG, "service is true");
			
			//EventList eventList = 
					//readEventsFromDB(mGeofenceManager.getEventIds()); 
				
			mGeofenceManager.readGeofenceFromDB();
			mGeofenceManager.addGeofence();
			
			if(mGeofenceManager.getEventIds().length > 0)
			{
				Log.i(TAG, "has event id");
				
				ReadEventListByIDConnection conn = new ReadEventListByIDConnection();
				conn.setEventIds(mGeofenceManager.getEventIds());
				conn.setURL(NetworkURL.READ_EVENT_LIST_FROM_DB);
				conn.execute();
			}
			else
			{
				Log.i(TAG, "Id List is emty");
			}
			
		}*/ 
	
	    //addEventList(root, eventList);
		return  root;
	}

	private EventList readEventsFromDB(String[] eventIds)  
	{
		Log.i(TAG, "readEVentsFromDB");
		
		EventList eventList = new EventList();
		
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
                        Event event = new Event();
                       
                        String id = obj.getString(EventUtil.EVENT_ID);
                        String email = obj.getString(EventUtil.EMAIL);
                        String contents = obj.getString(EventUtil.EVENT_CONTENTS);
                        String latitude = obj.getString(EventUtil.EVENT_LATITUDE);
                        String longitude = obj.getString(EventUtil.EVENT_LONGITUDE);
             
                        event.setId(id);
                        event.setContents(contents);
                        event.setBusinessName(email);
                        event.setEmail(email);
                        event.setLatitude(latitude);
                        event.setLongitude(longitude);
                        
                        eventList.addEvent(event);
                        
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
		
		return eventList;
	}

	private void addEventList(View view, EventList eventList)
	{
		ScrollView scrollView = (ScrollView)view.findViewById(R.id.eventList_scrollView);
		EventCardList eventCardList = new EventCardList(getActivity());
		ArrayList<Event> List = eventList.getList();
		
		for(Event event: List )
		{
			EventCardWidget eventCard = new EventCardWidget(getActivity());
			eventCard.setBusinessName(event.getBusinessName());
			eventCard.setEventContents(event.getContents());
			
			eventCardList.addView(eventCard);
		}
		
		scrollView.addView(eventCardList);
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		Log.i(TAG, "onResume");
	}

}
