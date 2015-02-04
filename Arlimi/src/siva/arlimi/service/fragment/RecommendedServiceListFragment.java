package siva.arlimi.service.fragment;

import siva.arlimi.main.MainActivity.OnServiceListListener;
import siva.arlimi.main.R;
import siva.arlimi.service.util.ServiceUtil;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class RecommendedServiceListFragment extends Fragment implements OnServiceListListener
{
	static public final String TAG = "RecommendedServiceListFargment";
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View root = inflater.inflate(R.layout.fragment_recommended_service_listview, container,
				false);
		
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
		Log.i(TAG, "init of fragment");
		
		
		Intent intent = ServiceUtil.getAllServiceListIntent(getActivity());
		getActivity().startService(intent);
	}

	@Override
	public void onListListener(String result)
	{
		Log.i(TAG, result);
	}


}
