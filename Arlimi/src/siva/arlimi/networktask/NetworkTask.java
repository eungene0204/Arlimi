package siva.arlimi.networktask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;



import siva.arlimi.alertness.AlertManager;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

abstract public class NetworkTask extends AsyncTask<String,String,String>
{
	
	private final static String LOG = "NetworkTask";
	public final static String NO_RESULT = "No Result";
	
	protected HttpURLConnection conn;
	protected String response;

	private Context mContext;
	private String mParam;
	protected StringBuilder mResultBuilder = new StringBuilder();
	
	private AlertManager mAlertMng = new AlertManager();

	public NetworkTask()
	{
		super();
		
		this.mParam = "";
		this.response = "";
		
	}
	public NetworkTask(Context context)
	{
		
		this.mContext = context;
		
		this.mParam = "";
		this.response = "";	
		
		mAlertMng.setmContext(mContext);
	}

	@Override
	protected String doInBackground(String... params)
	{
		try
		{
			mParam = params[0];
			mAlertMng.setmParam(mParam);
			URL url = new URL(params[0]);
			conn = (HttpURLConnection)url.openConnection();
			conn.setReadTimeout(5000);
			conn.setConnectTimeout(5000);
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.connect();
			
			sendData(conn, params);
			readData(conn);
			
			if(null == mResultBuilder.toString()) 
				return NO_RESULT;

		} catch (MalformedURLException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			Log.e(LOG,e.toString());
			e.printStackTrace();
		} catch(Exception e)
		{
			Log.e(LOG, e.toString());;
		}
		finally
		{
			if( null != conn) conn.disconnect();
		}
		return mResultBuilder.toString();
	}

	@Override
	protected void onPostExecute(String result)
	{
		super.onPostExecute(result);
	}
	
	public void readData(HttpURLConnection conn) throws IOException
	{
		//Get the response
		BufferedReader in = new BufferedReader(
				new InputStreamReader(conn.getInputStream()));
		String line = "";

		while( null != (line = in.readLine()))
		{
			mResultBuilder.append(line + '\n');
		}
		in.close();

	}

	//For overriding
	abstract public void sendData(HttpURLConnection conn,String[] params)throws UnsupportedEncodingException, IOException;

	
	public void writeData(HttpURLConnection conn, String data) throws UnsupportedEncodingException, IOException
	{
		Log.i(LOG, "Writting data: " + data);
		OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
		out.write(data);
		out.flush();
		out.close();
	}
}