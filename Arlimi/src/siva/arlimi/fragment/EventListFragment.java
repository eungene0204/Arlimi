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
import siva.arlimi.geofence.GeofenceManager.EventListListener;
import siva.arlimi.geofence.GeofenceServiceBinder.EventListener;
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

public class EventListFragment extends Fragment implements EventListListener
{
	public static final String TAG = "EventListFragment";
	
	private GeofenceManager mGeofenceManager;
	private EventList mEventList;
	private View mRoot;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		Log.i(TAG, "onCreateView");
		
		View root = inflater.inflate(R.layout.fragment_event_list, container, false);
		mRoot = root;
		
		mGeofenceManager = new GeofenceManager(getActivity());
		mGeofenceManager.registerEventListListener(this);
		mGeofenceManager.readGeofenceFromDB();
		mGeofenceManager.addGeofence();
		mGeofenceManager.getBinder().doBindService();
		
		return  root;
	}
	
	private void addEventList(View view, EventList eventList)
	{
		ArrayList<Event> List = eventList.getList();
	
		//Nothing to add
		if( 0 == List.size())
			return;
		
		ScrollView scrollView = (ScrollView)view.findViewById(R.id.eventList_scrollView);
		EventCardList eventCardList = new EventCardList(getActivity());
		
		for(Event event: List )
		{
			EventCardWidget eventCard = new EventCardWidget(getActivity());
			eventCard.setBusinessName(event.getBusinessName());
			eventCard.setEventContents(event.getContents());
			
			eventCardList.addView(eventCard);
		}

		scrollView.removeAllViews();
		scrollView.addView(eventCardList);
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		Log.i(TAG, "onResume");
	}
	
	@Override
	public void onStop()
	{
		super.onStop();
		Log.i(TAG, "onStop");
	}
	
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		mGeofenceManager.getBinder().doUnbindService();
		Log.i(TAG, "onDestroy");
	}


	@Override
	public void onNewEventList(final EventList eventList)
	{
		getActivity().runOnUiThread(new Runnable()
		{
			
			@Override
			public void run()
			{
				addEventList(mRoot, eventList);
			}
		});
		
	}

}
