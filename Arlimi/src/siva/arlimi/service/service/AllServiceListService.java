package siva.arlimi.service.service;

import org.json.JSONArray;
import org.json.JSONException;

import siva.arlimi.geofence.GeofenceManager;
import siva.arlimi.networktask.NetworkURL;
import siva.arlimi.service.connection.AllServiceListConn;
import siva.arlimi.service.connection.AllServiceListConn.OnAllServiceListListener;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class AllServiceListService extends Service implements OnAllServiceListListener
{
	static public final String TAG = "AllServiceListService";
	
	private String mResult;
	private OnResultListener mClient;
	
	public class AllServiceListBinder extends Binder
	{
		public AllServiceListService getService()
		{
			return AllServiceListService.this;
		}
	}
	
	private IBinder mBinder = new AllServiceListBinder();
	
	@Override
	public IBinder onBind(Intent intent)
	{
		String url = NetworkURL.SERVICE_READ_ALL_LIST;
		
		AllServiceListConn conn = new AllServiceListConn(this);
		conn.setURL(url);
		conn.setListener(this);
		conn.execute();
	
		return mBinder;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onAllServiceList(final String result)
	{
		Log.i(TAG, "Result" + result);
		setResult(result);
		
	}

	private void setResult(final String result)
	{
		if(result != null)
		{
			this.mClient.getResult(result);
		
			//Just for test
			addGeofence(result);
		}
	}


	private void addGeofence(String result)
	{
		try
		{
			JSONArray jsonArry = new JSONArray(result.trim());
			
		} catch (JSONException e)
		{
			e.printStackTrace();
		}
		
		GeofenceManager geofenceMng = new GeofenceManager(this);
		
	}

	public void setClient(OnResultListener client)
	{
		if( null != client)
			this.mClient = client;
	}
	
	public interface OnResultListener
	{
		void getResult(String result);
	}

}
