package siva.arlimi.fragment;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import siva.arlimi.activity.R;
import siva.arlimi.event.Event;
import siva.arlimi.event.EventList;
import siva.arlimi.networktask.ReadEventListConnection;
import siva.arlimi.widget.EventCardList;
import siva.arlimi.widget.EventCardWidget;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

public class EventListFragment extends Fragment
{
	public static final String TAG = "EventListFragment";
	
	private EventList mEventList;
	
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View root = inflater.inflate(R.layout.test_card, container, false);
	
       // EventList eventList = readEvents();
	//addList(root, eventList);
		
		return  root;
	}

	private EventList readEvents()  
	{
		EventList eventList = new EventList();
		String url = "http://192.168.0.21:8088/SIVA_Arlimi_Test_Server/readEventList";
	
		ReadEventListConnection conn = new ReadEventListConnection();
		conn.setURL(url);
		
		try
		{
			String result = conn.execute().get();
			
			JSONArray jsonList= new JSONArray(result.toString());
                 
                 for(int i = 0; i < jsonList.length(); i++)
                 {
                        JSONObject obj = jsonList.getJSONObject(i);
                        Event event = new Event();
                        
                        String BusinessName = obj.getString("business_name");
                        String contents = obj.getString("contents");
                
                        event.setBusinessName(BusinessName);
                        event.setContents(contents);
                        
                        eventList.addEvent(event);
                        
                        Log.i(TAG, BusinessName);
                        Log.i(TAG, contents); 
                 }
			
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
		
		return eventList;
	}

	private void addList(View view, EventList eventList)
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


}
