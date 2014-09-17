package siva.arlimi.event.fragment;

import java.util.ArrayList;

import siva.arlimi.activity.HomeActivity.ActionTabListener;
import siva.arlimi.event.Event;
import siva.arlimi.event.EventList;
import siva.arlimi.event.activity.EventDetail;
import siva.arlimi.event.activity.EventDetailActivity;
import siva.arlimi.event.adapter.EventAdapter;
import siva.arlimi.geofence.GeofenceManager;
import siva.arlimi.geofence.GeofenceManager.EventListListener;
import siva.arlimi.main.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

public class EventListFragment extends Fragment implements EventListListener,
										ActionTabListener
{
	public static final String TAG = "EventListFragment";
	
	private GeofenceManager mGeofenceManager;
	private EventList mEventList;
	private View mRoot;
	
	private EventAdapter mEventAdapter;
	private ListView mListView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		Log.i(TAG, "onCreateView");
		
		View root = inflater.inflate(R.layout.fragment_eventlist_listview,
				container, false);
		
		/*
		mRoot = root;

		mGeofenceManager = new GeofenceManager(getActivity());
		mGeofenceManager.registerEventListListener(this);
		mGeofenceManager.readGeofenceFromDB();
		mGeofenceManager.addGeofence();
		mGeofenceManager.getBinder().doBindService(); 
		
		mHomeActivity = (HomeActivity) getActivity();
		mHomeActivity.registerActionTabListener(this);
		*/
		
		ArrayList<Event> list = new ArrayList<Event>();
	
		for(int i = 0; i < 10; i++)
		{
			Event event = new Event();
			event.setBusinessName("Shop Number: " + String.valueOf(i));
			event.setContents("Very Good Event");
			
			list.add(event);
		}
				
		EventAdapter eventAdapter = new EventAdapter(getActivity(),
				R.layout.listview_item, list);
		
		mListView = (ListView)root.findViewById(R.id.eventlist_listview); 
		mListView.setAdapter(eventAdapter);
		mListView.setOnItemClickListener(new ItemClickListener());
	
		return  root;
	}
	
	private void addEventList(final View view, final ArrayList<Event> eventList)
	{
		//Nothing to add
		if( 0 == eventList.size())
			return;
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
		Log.i(TAG, "onDestroy");
		super.onDestroy();
		
		if(null != mGeofenceManager)
			mGeofenceManager.getBinder().doUnbindService();
	}

	@Override
	public void onNewEventList(final EventList eventList)
	{
		Log.i(TAG, "New EvetList");
		
		mEventList = eventList;
	
	}

	@Override
	public void onRefreshClicked()
	{
		
		mEventList = mGeofenceManager.readEventListById();
				
		mEventAdapter = new EventAdapter(getActivity(),
				R.layout.listview_item, mEventList.getList());
		
		mListView.setAdapter(mEventAdapter);
	
	}
	

	@Override
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
		
		/*
	
		try
		{
			mCallback = (OnFavoriteButtonSelectedListener) activity;
		} 
		catch(ClassCastException e)
		{
			throw new ClassCastException(activity.toString()+
					"must implement OnFavorieteButtonSelectedListener");
		} */
		
	}
	

	private class ItemClickListener implements AdapterView.OnItemClickListener
	{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id)
		{
			Intent intent = new Intent(getActivity(), EventDetailActivity.class);
		
			startActivity(intent);
			
		}
		
	}
	
}
