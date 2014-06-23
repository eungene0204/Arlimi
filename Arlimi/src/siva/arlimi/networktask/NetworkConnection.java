package siva.arlimi.networktask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.os.AsyncTask;
import android.util.Log;

public class NetworkConnection extends AsyncTask<String, String, String>
{
	public 	final static String TAG = "NetworkConnection";
	
	private Object mData = null;
	private String mUrl;
	
	@Override
	protected String doInBackground(String... params)
	{
		String result = null;
		HttpURLConnection conn = null;
		
		try
		{
			URL url = getUrl();
			conn = (HttpURLConnection)url.openConnection();
			conn.setReadTimeout(5000);
			conn.setConnectTimeout(5000);
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.connect();
	
			if(null != mData)
				sendData(conn, (String)mData);
			
			result = readData(conn);
		
		} 
		catch (MalformedURLException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			Log.e(TAG,e.toString());
			e.printStackTrace();
		} catch(Exception e)
		{
			Log.e(TAG, e.toString());;
		}
		finally
		{
			if( null != conn) conn.disconnect();
		}
		
		return result;
	}
	
	public void setData(Object data)
	{
		this.mData = data;
	}
	
	public void setURL(String url)
	{
		this.mUrl = url;
	}
	
	public URL getUrl() throws MalformedURLException
	{
		return new URL(mUrl); 
	}

	//abstract public void makeData(Object obj);
	
	protected void sendData(HttpURLConnection conn, String data)
	{
		Log.i(TAG, "Writting data: " + data);
		
		OutputStreamWriter out;
		try
		{
			out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
			out.write(data);
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
		StringBuilder result = new StringBuilder();
		
		BufferedReader in;
		try
		{
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			
		String line = "";
		while( null != (line = in.readLine()))
		{
			result.append(line + '\n');
		}
		in.close();
		
		
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		return result.toString(); 
		
	}

}
