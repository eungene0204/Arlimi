package siva.arlimi.shop.service;

import siva.arlimi.shop.connection.SearchAddressConnection;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class SearchAddressService extends Service
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
		
		SearchAddressConnection conn = new SearchAddressConnection();
		conn.execute();
		
		return START_REDELIVER_INTENT;
	}

}
