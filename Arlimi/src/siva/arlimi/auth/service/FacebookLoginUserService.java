package siva.arlimi.auth.service;

import org.json.JSONObject;

import siva.arlimi.auth.activity.LoginActivity;
import siva.arlimi.auth.connection.LoginFacebookUserConnection;
import siva.arlimi.auth.connection.LoginFacebookUserConnection.OnFacebookLoginResultListener;
import siva.arlimi.auth.util.AuthUtil;
import siva.arlimi.networktask.NetworkURL;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class FacebookLoginUserService extends Service implements OnFacebookLoginResultListener
{
	public static final String TAG = "FacebookLoginUserService"; 
	
	private static final String VALID_USER = "VALID";
	private static final String IN_VALID_USER = "INVALID_USER";
	

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
		
		Log.i(TAG, "facebook login service onStart");
	
		JSONObject json = AuthUtil.getJSONObject(intent);
		
		LoginFacebookUserConnection conn = new LoginFacebookUserConnection(this);
		conn.setData(json);
		conn.setURL(NetworkURL.FACEBOOK_USER_LOGIN);
		conn.execute();
		
		return START_REDELIVER_INTENT;
	}

	@Override
	public void facebookLoginResult(String result)
	{
		sendResultToActivity(result);
	}
	
	void sendResultToActivity(String result)
	{
		final Intent intent = new Intent(AuthUtil.ACTION_FACEBOOK_LOGIN_RESULT);
		
		if(result.equals(VALID_USER))
		{
			intent.putExtra(AuthUtil.KEY_FACEBOOK_LOGIN_RESULT,
					AuthUtil.RESULT_VALID_USER);
		}
		else if(result.equals(IN_VALID_USER));
		{
			intent.putExtra(AuthUtil.KEY_FACEBOOK_LOGIN_RESULT, 
					AuthUtil.RESULT_INVALID_UER);
		}
		
		sendBroadcast(intent);
	}

}