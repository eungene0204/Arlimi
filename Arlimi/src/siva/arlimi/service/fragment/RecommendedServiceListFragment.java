package siva.arlimi.service.fragment;

import siva.arlimi.event.util.EventUtils;
import siva.arlimi.main.R;
import siva.arlimi.service.util.ServiceUtil;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class RecommendedServiceListFragment extends Fragment
{
	static public final String TAG = "RecommendedServiceListFargment";
	
		
	private BroadcastReceiver mServiceListReceiver = new BroadcastReceiver()
	{
		@Override
		public void onReceive(Context context, Intent intent)
		{
			onServiceListReceiver(intent);
		}

	};
	
	private void onServiceListReceiver(Intent intent)
	{
		String result = intent.getStringExtra(ServiceUtil.KEY_ALL_SERVICE_LIST);
		
		Log.i(TAG, "re fragment" + result);
		
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		
		//register Broadcast
		IntentFilter serviceListfilter = new IntentFilter(ServiceUtil.ACTION_ALL_SERVICE_LIST);
		getActivity().registerReceiver(mServiceListReceiver, serviceListfilter);
	}
	
	@Override
	public void onPause()
	{
		super.onPause();
		getActivity().unregisterReceiver(mServiceListReceiver);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View root = inflater.inflate(R.layout.fragment_recommended_service_listview, container,
				false);
		
		//Get Service info to init
		init();
		
		/*
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
		mListView.setOnItemClickListener(new ItemClickListener()); */
	
		
		return root; 
	}

	private void init()
	{
		//For the test just get all available service
		Log.i(TAG, "init of fragment");
		Intent intent = ServiceUtil.getAllServiceListIntent(getActivity());
		getActivity().startService(intent);
		
	}

}
