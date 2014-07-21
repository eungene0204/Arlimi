package siva.arlimi.networktask;

import java.io.IOException;
import java.net.HttpURLConnection;

import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

public class ReadEventListByIDConnection extends NetworkConnection
{
	
	private String[] mEventIds;
	
	@Override
	protected String doInBackground(String... params)
	{
		String result = "";
		
		try
		{
			HttpURLConnection conn = getDefaultHttpConnection();
			
			conn.connect();
			
			if( HttpURLConnection.HTTP_OK == conn.getResponseCode())
			{
				Log.i(TAG, "HTTP_OK");
				result = readData(conn);
			}
			
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	public void sendEventID()
	{
		JSONArray jsonArray = new JSONArray();
		
		String id;
		int len = mEventIds.length;
		for(int i = 0; i < len; i++)
		{
			JSONObject json = new JSONObject();
			id = mEventIds[i];
			
			
		}
	}
		
	public void setEventIds(String[] eventIds)
	{
		this.mEventIds = eventIds;
	}
	
	
	public String[] getEventIds()
	{
		return this.mEventIds;
	}


}
