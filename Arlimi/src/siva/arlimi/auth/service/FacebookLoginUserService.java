package siva.arlimi.auth.service;

import org.json.JSONObject;


import siva.arlimi.auth.activity.LoginActivity;
import siva.arlimi.auth.connection.FacebookUserLoginConnection;
import siva.arlimi.auth.interfaces.OnLoginResultListener;
import siva.arlimi.auth.util.AuthUtil;
import siva.arlimi.networktask.NetworkURL;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class FacebookLoginUserService extends Service implements OnLoginResultListener 
{
	public static final String TAG = "FacebookLoginUserService"; 
	

	@Override
	public IBinder onBind(Intent intent)
	{
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		super.onStartCommand(intent, flags, startId);
		
		Log.i(TAG, "facebook login service onStart");
	
		JSONObject json = AuthUtil.getJSONObject(intent);
		
		FacebookUserLoginConnection conn = new FacebookUserLoginConnection(this);
		conn.setData(json);
		conn.setURL(NetworkURL.FACEBOOK_USER_LOGIN);
		conn.execute();
		
		return START_REDELIVER_INTENT;
	}

	@Override
	public void OnLoginResult(String result)
	{
		sendResultToActivity(result);
	}

	
	void sendResultToActivity(String result)
	{
		final Intent intent = AuthUtil.checkResult(result, AuthUtil.ResultType.LOGIN); 
		
		sendBroadcast(intent);
	}

	
}