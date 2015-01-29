package siva.arlimi.event.connection;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import siva.arlimi.networktask.NetworkConnection;

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
		
			sendEventID(conn);
			
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
	
	public void sendEventID(HttpURLConnection conn)
	{
		//JSONArray jsonArray = new JSONArray();
		
		String id;
		int len = mEventIds.length;
		JSONObject json = new JSONObject();
		try
		{
			json.put("ID_SIZE", String.valueOf(len));
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
		
		for(int i = 0; i < len; i++)
		{
			id = mEventIds[i];
			try
			{
				json.put(String.valueOf(i), id);
				
			} catch (JSONException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		//Write JsonArray
		OutputStreamWriter out;
		
		try
		{
			out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
			out.write(json.toString());
			out.flush();
			out.close();
			
		} catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
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
