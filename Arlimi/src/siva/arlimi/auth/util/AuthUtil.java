package siva.arlimi.auth.util;

import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

import siva.arlimi.auth.service.EmailUserLoginService;
import siva.arlimi.auth.service.FacebookLoginUserService;
import siva.arlimi.auth.service.NewEmailUserService;
import siva.arlimi.auth.service.NewFacebookUserService;
import siva.arlimi.user.User;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AuthUtil
{
	public static final String TAG = "AuthUtil";
	
	private static final String VALID_USER = "VALID";

	public static String ACTIVITY = "ACTIVITY";
	public static String KEY_EMAIL = "EMAIL"; 
	public static String KEY_NAME = "NAME";
	public static String KEY_PASSWORD = "PASSWORD";
	
	
	public static int RESULT_VALID_USER = 1;
	public static int RESULT_INVALID_USER = 0;

	public static String KEY_USER = "USER";
	public static String KEY_EMAIL_USER = "EMAIL_USER";
	public static String KEY_FACEBOOK_USER = "FACEBOOK_USER";
	
	public static String ACTION_LOGIN_RESULT = "SIVA_ARLIMI_LOGIN_RESULT";
	public static String KEY_LOGIN_RESULT = "RESULT";
	
	public static String ACTION_REGISTRATION_RESULT = "SIVA_ARLIMI_REG_RESULT";
	
	public enum ResultType { LOGIN, REGISTRATION }  
	
	public static Intent getNewEmailUserIntent(Context context)
	{
		return new Intent(context, NewEmailUserService.class);
	}
	
	public static Intent getNewFaceBookUserIntent(Context context)
	{
		return new Intent(context, NewFacebookUserService.class);
	}
	
	public static Intent getFacebookLoginIntent(Context context)
	{
		return new Intent(context, FacebookLoginUserService.class);
	}
	
	public static Intent getEmailLoginIntent(Context context)
	{
		return new Intent(context, EmailUserLoginService.class);
	}
	
	public static Intent checkResult(final String result, final ResultType type)
	{
	
		Intent intent = new Intent();
		
		switch(type)
		{
		
		case LOGIN:
			intent = new Intent(ACTION_LOGIN_RESULT);
			break;
			
		case REGISTRATION:
			intent = new Intent(ACTION_REGISTRATION_RESULT);
			break;
		
			default:
				break;
		
		}
		
		if(result.equals(VALID_USER))
		{
			Log.i(TAG, "checkResult valid " + result);
			intent.putExtra(AuthUtil.KEY_LOGIN_RESULT,
					AuthUtil.RESULT_VALID_USER);
		}
		else 
		{
			Log.i(TAG, "checkResult invalid " + result);
			intent.putExtra(AuthUtil.KEY_LOGIN_RESULT, 
					AuthUtil.RESULT_INVALID_USER);
		}
		
		return intent;
	}
	
	
	public static JSONObject getJSONObject(Intent intent)
	{
		JSONObject json = new JSONObject();
	
		try
		{
			User user = (User) intent.getSerializableExtra(AuthUtil.KEY_USER);
			Set<String> keys = user.getKeys();

			for (String key : keys)
			{
				try
				{
					json.put(key, user.get(key));
				} catch (JSONException e)
				{
					e.printStackTrace();
				}
			}
			
		} catch (NullPointerException e)
		{
			e.printStackTrace();
		}
		
		return json;
		
	}

}
