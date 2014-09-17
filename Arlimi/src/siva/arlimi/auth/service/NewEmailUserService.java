package siva.arlimi.auth.service;

import java.util.Set;


import org.json.JSONException;
import org.json.JSONObject;

import siva.arlimi.auth.connection.NewEmailUserConnection;
import siva.arlimi.auth.util.AuthUtil;
import siva.arlimi.networktask.NetworkURL;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

public class NewEmailUserService extends Service
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
	
		JSONObject json = AuthUtil.getJSONObject(intent);
		
		NewEmailUserConnection conn = new NewEmailUserConnection(getApplicationContext());
		conn.setData(json);
		conn.setURL(NetworkURL.HOME_EMAIL_USER_REGISTRATION);
		conn.execute();
		
		return START_REDELIVER_INTENT;
	}

}
