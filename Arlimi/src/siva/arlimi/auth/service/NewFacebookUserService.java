package siva.arlimi.auth.service;

import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

import siva.arlimi.auth.connection.NewFacebookUserConnection;
import siva.arlimi.auth.interfaces.OnRegistrationResultListener;
import siva.arlimi.auth.util.AuthUtil;
import siva.arlimi.networktask.NetworkURL;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class NewFacebookUserService extends Service implements OnRegistrationResultListener
{
	public static final String TAG = "NewFacebookUserService";
	
	public IBinder onBind(Intent intent)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		super.onStartCommand(intent, flags, startId);
		
		JSONObject json = AuthUtil.getJSONObject(intent); 
	
		NewFacebookUserConnection conn = new NewFacebookUserConnection(this);
		conn.setData(json);
		conn.setURL(NetworkURL.FACEBOOK_USER_REGISTRATION);
		conn.execute();
		
		return START_REDELIVER_INTENT;
	}

	@Override
	public void onRegistrationResult(String result)
	{
		sendResult(result);
		
	}

	private void sendResult(String result)
	{
		Log.i(TAG, "result " + result);
		
		final Intent intent = 
				AuthUtil.checkResult(result, AuthUtil.ResultType.REGISTRATION);
		
		sendBroadcast(intent);
	}

}
