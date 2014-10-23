package siva.arlimi.auth.service;

import org.json.JSONException;

import org.json.JSONObject;

import siva.arlimi.auth.connection.EmailUserLoginConnection;
import siva.arlimi.auth.interfaces.OnLoginResultListener;
import siva.arlimi.auth.util.AuthUtil;
import siva.arlimi.networktask.NetworkURL;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class EmailUserLoginService extends Service implements OnLoginResultListener 
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
		
		JSONObject json = AuthUtil.getJSONObject(intent);
		
		final EmailUserLoginConnection conn = new EmailUserLoginConnection(this);
		conn.setData(json);
		conn.setURL(NetworkURL.EMAIL_USER_LOGIN);
		conn.execute();
		
		return START_REDELIVER_INTENT;
	}
	
	@Override
	public void OnLoginResult(String result)
	{
		sendBrodCastToActivity(result);
	}
	
	private void sendBrodCastToActivity(final String result)
	{
		JSONObject user = null;
		
		try
		{
			user = new JSONObject(result.toString().trim());
			
		} catch (JSONException e)
		{
			e.printStackTrace();
		}
		
		final Intent intent = AuthUtil.checkResult(user, 
				AuthUtil.ResultType.EMAIL_LOGIN);
		sendBroadcast(intent);
	}

}
