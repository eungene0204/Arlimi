package siva.arlimi.shop.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class ShopRegistrationService extends Service
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
		
		
		
		return START_REDELIVER_INTENT;
	}

}
