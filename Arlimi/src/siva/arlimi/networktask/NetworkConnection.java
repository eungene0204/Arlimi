package siva.arlimi.networktask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public class NetworkConnection extends AsyncTask<String, String, String>
{
	public 	final static String TAG = "NetworkConnection";
	
	protected final int TIME_LIMIT = 5000;
	
	protected Object mData = null;
	protected String mUrl;
	
	@Override
	protected String doInBackground(String... params)
	{
		String result = "";
		HttpURLConnection conn = null;
		
		try
		{
			conn = getDefaultHttpConnection();
			
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
	
	protected HttpURLConnection getDefaultHttpConnection() throws IOException
	{
		
		URL url = getUrl();
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		conn.setReadTimeout(TIME_LIMIT);
		conn.setConnectTimeout(TIME_LIMIT);
		conn.setRequestMethod("POST");
		conn.setDoOutput(true);
		conn.setDoInput(true);
		
		return conn;
	}
	
	
	public void setData(Object data)
	{
		this.mData = data;
	}
	
	public void setURL(String url)
	{
		this.mUrl = (url.isEmpty() || null == url) ? "" : url;
	}
	
	public URL getUrl() throws MalformedURLException
	{
		return new URL(mUrl); 
	}

	//abstract public void makeData(Object obj);
	
	protected void sendData(HttpURLConnection conn, Object data)
	{
		JSONObject json = (JSONObject) data;
		
		Log.i(TAG, json.toString());
		
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
	
	public String readData(HttpURLConnection conn)
	{
		
		int length = conn.getContentLength();
		StringBuilder stringBuilder = new StringBuilder(length);
		
		try
		{
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			
			char[] buffer = new char[length];
			int charRead;
			
			while(  -1 != (charRead = bufferedReader.read(buffer)) )
			{
				stringBuilder.append(buffer, 0, length); 
			}
			
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return stringBuilder.toString();
		
		
		/*
		try
		{
			BufferedReader in;
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			
		String line = "";
		while( null != (line = in.readLine()))
		{
			result.append(line + '\n');
		}
		in.close();
		
		
		}
	    catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return result.toString();  
		
	}
	*/
	}

}
