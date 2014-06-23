package siva.arlimi.event;

import java.util.ArrayList;

public class EventList
{
	private ArrayList<Event> mEventList;
	
	public EventList()
	{
		mEventList = new ArrayList<Event>();
	}
	
	public void addEvent(Event event)
	{
		mEventList.add(event);
	}
	
	public ArrayList<Event> getList()
	{
		return this.mEventList;
	}

}
