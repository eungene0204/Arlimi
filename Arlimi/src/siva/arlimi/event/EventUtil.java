package siva.arlimi.event;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

abstract public class EventUtil
{
	public static final String USER = "USER";
	public static final String EMAIL = "EMAIL";

	public static final String EVENT_ID = "EVENT_ID";
	public static final String EVENT_CONTENTS = "EVENT_CONTENTS";
	public static final String EVENT_START_DATE = "EVENT_START_DATE";
	public static final String EVENT_END_DATE = "EVENT_END_DATE";
	public static final String EVENT_START_TIME = "EVENT_START_TIME";
	public static final String EVENT_END_TIME = "EVENT_END_TIME";
	public static final String EVENT_RADIUS = "EVENT_RADIUS";
	public static final String EVENT_LATITUDE = "EVENT_LATITUDE";
	public static final String EVENT_LONGITUDE = "EVENT_LONGITUDE";
	
	public static final String SHOP_NAME = "SHOP_NAME";
	public static final String SHOP_ADDRESS = "SHOP_ADDRESS";
	public static final String SHOP_PHONE_NUMBER = "SHOP_PHONE_NUMBER";
	
	public static EventList parseJSONArrayToEventList(final JSONArray array) throws JSONException
	{
		EventList eventList = new EventList();
		
		if(null == array)
			return eventList;
		
		final int length = array.length();
		
		for(int i = 0; i < length; i++)
		{
			JSONObject json = array.getJSONObject(i);
			final String id = json.getString(EVENT_ID);
			final String contents = json.getString(EVENT_CONTENTS);
			final String email = json.getString(EMAIL);
			final String latitude = json.getString(EVENT_LATITUDE);
			final String longitude = json.getString(EVENT_LONGITUDE);
			
			Event event = new Event();
			event.setEmail(email);
			event.setContents(contents);
			event.setId(id);
			event.setLatitude(latitude);
			event.setLongitude(longitude);
			
			eventList.addEvent(event);
		}
		
		return eventList;
	}

	
}
