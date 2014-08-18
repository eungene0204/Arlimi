package siva.arlimi.event;

import java.util.ArrayList;

public class EventList
{
	private final ArrayList<Event> mEventList;
	
	public EventList()
	{
		mEventList = new ArrayList<Event>();
	}
	
	public void addEvent(Event event)
	{
		if(event.isNull())
			mEventList.add(Event.newNull());
		else
			mEventList.add(event);
	}
	
	public ArrayList<Event> getList()
	{
		return this.mEventList;
	}
	
	public boolean isEmpty()
	{
		return mEventList.isEmpty();
	}
	
	public boolean isNull()
	{
		return false;
	}
	
	public static EventList newNull()
	{
		return new NullEventList();
	}

}
