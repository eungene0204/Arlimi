package siva.arlimi.fragment;

import java.util.ArrayList;

import siva.arlimi.activity.HomeActivity;
import siva.arlimi.activity.HomeActivity.ActionTabListener;
import siva.arlimi.activity.R;
import siva.arlimi.event.Event;
import siva.arlimi.event.EventList;
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
import android.widget.ScrollView;

public class EventListFragment extends Fragment implements EventListListener,
										ActionTabListener
{
	public static final String TAG = "EventListFragment";
	
	private GeofenceManager mGeofenceManager;
	private EventList mEventList;
	private View mRoot;
	
	private HomeActivity mHomeActivity;

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
		
		mHomeActivity = (HomeActivity) getActivity();
		mHomeActivity.registerActionTabListener(this);
		
		return  root;
	}
	
	private void addEventList(View view, EventList eventList)
	{
		ArrayList<Event> List = eventList.getList();
	
		//Nothing to add
		if( 0 == List.size())
			return;
		
		ScrollView scrollView =
				(ScrollView)view.findViewById(R.id.eventList_scrollView);
		
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
		Log.i(TAG, "New EvetList");
		
		mEventList = eventList;
		
		/*
		getActivity().runOnUiThread(new Runnable()
		{
			
			@Override
			public void run()
			{
				addEventList(mRoot, eventList);
			}
		}); */
	}

	@Override
	public void onRefreshClicked()
	{
		if(null == mEventList)
			mEventList = new EventList();
		
		addEventList(mRoot, mEventList);
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
