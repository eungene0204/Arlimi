package siva.arlimi.auth.service;

import org.json.JSONObject;

import siva.arlimi.auth.connection.NewEmailUserConnection;
import siva.arlimi.auth.interfaces.OnRegistrationResultListener;
import siva.arlimi.auth.util.AuthUtil;
import siva.arlimi.networktask.NetworkURL;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class NewEmailUserService extends Service 
							implements OnRegistrationResultListener
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
		
		NewEmailUserConnection conn = new NewEmailUserConnection(this);
		conn.setData(json);
		conn.setURL(NetworkURL.HOME_EMAIL_USER_REGISTRATION);
		conn.execute();
		
		return START_REDELIVER_INTENT;
	}

	@Override
	public void onRegistrationResult(String result)
	{
		final Intent intent = 
				AuthUtil.checkResult(result, AuthUtil.ResultType.REGISTRATION);
		
		sendBroadcast(intent);
		
	}

}
