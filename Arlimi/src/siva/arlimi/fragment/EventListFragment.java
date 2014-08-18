package siva.arlimi.fragment;

import java.util.ArrayList;



import siva.arlimi.activity.HomeActivity;
import siva.arlimi.activity.HomeActivity.ActionTabListener;
import siva.arlimi.activity.R;
import siva.arlimi.event.Event;
import siva.arlimi.event.EventList;
import siva.arlimi.event.adapter.EventAdapter;
import siva.arlimi.geofence.GeofenceManager;
import siva.arlimi.geofence.GeofenceManager.EventListListener;
import siva.arlimi.widget.EventCardList;
import siva.arlimi.widget.EventCardWidget;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class EventListFragment extends Fragment implements EventListListener,
										ActionTabListener
{
	public static final String TAG = "EventListFragment";
	
	private GeofenceManager mGeofenceManager;
	private EventList mEventList;
	private View mRoot;
	
	private HomeActivity mHomeActivity;
	
	private EventAdapter mEventAdapter;
	private ListView mListView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		Log.i(TAG, "onCreateView");
		
		View root = inflater.inflate(R.layout.fragment_event_listview,
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
				R.layout.main_card, list);
		
		mListView = (ListView)root.findViewById(R.id.event_listview); 
		mListView.setAdapter(eventAdapter);
	
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
		super.onDestroy();
		mGeofenceManager.getBinder().doUnbindService();
		Log.i(TAG, "onDestroy");
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
				R.layout.main_card, mEventList.getList());
		
		mListView.setAdapter(mEventAdapter);
	
	}
	
	private OnFavoriteButtonSelectedListener mCallback;
	
	public interface OnFavoriteButtonSelectedListener
	{
		Event onFavoriteButtonSelected(Event event);
		void setEvent(Event event);
	}
	
	@Override
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
	
		try
		{
			mCallback = (OnFavoriteButtonSelectedListener) activity;
		} 
		catch(ClassCastException e)
		{
			throw new ClassCastException(activity.toString()+
					"must implement OnFavorieteButtonSelectedListener");
		}
		
	}
}
