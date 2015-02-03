package siva.arlimi.service.fragment;

import java.util.ArrayList;

import siva.arlimi.event.Event;
import siva.arlimi.event.adapter.EventAdapter;
import siva.arlimi.main.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class RecommendedServiceListFargment extends Fragment
{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View root = inflater.inflate(R.layout.fragment_recommended_service_listview, container,
				false);
		
			
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
	
		
		return root; 
	}

}
