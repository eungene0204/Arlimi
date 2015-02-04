package siva.arlimi.service.service;

import siva.arlimi.networktask.NetworkURL;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class RecommendedServiceListService extends Service
{

	@Override
	public IBinder onBind(Intent intent)
	{
		return null;
	}
	
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		super.onStartCommand(intent, flags, startId);
		
		String url = NetworkURL.SERVICE_RECOMMENDED_LIST; 
		
		return START_REDELIVER_INTENT;
	}

}
