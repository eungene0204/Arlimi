package siva.arlimi.networktask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import siva.arlimi.event.Event;
import siva.arlimi.event.EventList;
import android.util.Log;

public class ReadEventListConnection extends NetworkConnection
{
	public static final String TAG = "ReadEventListConnection";
	
	private EventList mEventList;
	DataDownloadListener mDataDownloadListener;
	
	public ReadEventListConnection()
	{
		mEventList = new EventList();
	}

	@Override
	protected void onPostExecute(String result)
	{
		super.onPostExecute(result);
		
	}
	
	public void setEventList(EventList list)
	{
		this.mEventList = list;
	}
	
	public EventList getEventList()
	{
		return this.mEventList;
	}
	
	public void setDataDownloadListener(DataDownloadListener listener)
	{
		this.mDataDownloadListener = listener;
	}
	
	public static interface DataDownloadListener
	{
		void dataDownloadSuccess(Object data);
		void dataDownloadFail();
		
	}

}
