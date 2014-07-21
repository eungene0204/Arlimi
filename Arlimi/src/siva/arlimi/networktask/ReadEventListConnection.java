package siva.arlimi.networktask;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

import siva.arlimi.event.EventList;
import android.util.Log;

public class ReadEventListConnection extends NetworkConnection
{
	public static final String TAG = "ReadEventListConnection";
	
	private EventList mEventList;
	private String mResult;

	@Override
	protected String doInBackground(String... params)
	{
		String result ="";
		HttpURLConnection conn = null;
		
		try
		{
			URL url = getUrl();
			conn = (HttpURLConnection)url.openConnection();
			conn.setReadTimeout(TIME_LIMIT);
			conn.setConnectTimeout(TIME_LIMIT);
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
				
			if (null != mData)
				sendData(conn, mData);

			conn.connect();
			
			if( HttpURLConnection.HTTP_OK == conn.getResponseCode())
			{
				Log.i(TAG, "HTTP_OK");
				result = readData(conn);
			}

		} 
		catch (MalformedURLException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			Log.e(TAG,e.toString());
			e.printStackTrace();
		} 
		catch(Exception e)
		{
			Log.e(TAG, e.toString());;
		}
		finally
		{
			if( null != conn) conn.disconnect();
		}
		
		return result;
	}
	
	public ReadEventListConnection()
	{
		mEventList = new EventList();
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
	}
	
	public static interface DataDownloadListener
	{
		void dataDownloadSuccess(Object data);
		void dataDownloadFail();
		
	}

}
