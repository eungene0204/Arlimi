package siva.arlimi.location.service;

import siva.arlimi.location.connection.ReverseGeocodingConnection;
import siva.arlimi.location.connection.ReverseGeocodingConnection.OnReversGeocodingListener;
import siva.arlimi.location.util.LocationUtil;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class ReverseGeocodingService extends Service implements OnReversGeocodingListener
{

	@Override
	public IBinder onBind(Intent intent)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		super.onStartCommand(intent, flags, startId);
	
		String url = intent.getStringExtra(LocationUtil.QUERY_REVERSE_GEOCODING);
		
		ReverseGeocodingConnection conn = new ReverseGeocodingConnection(this); 
		conn.setURL(url);
		conn.execute();
		
		return START_REDELIVER_INTENT;
	}

	@Override
	public void onReversGeoCodingAddress(String result)
	{
		sendAddressToActivity(result);
	}

	private void sendAddressToActivity(String result)
	{
		Intent intent = new Intent(LocationUtil.ACTION_REVERSE_GEOCODING);
		intent.putExtra(LocationUtil.RESULT_REVERSE_GEOCODING, result);
		sendBroadcast(intent);
		
	}

}
