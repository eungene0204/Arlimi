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
	
	public static final String VALID_USER = "VALID";	
	public static final String INVALID_USER = "INVALID";
	public static final String DUPLICATE_USER = "DUPLICATE"; 

	public static String ACTIVITY = "ACTIVITY";
	public static String KEY_EMAIL = "EMAIL"; 
	public static String KEY_NAME = "NAME";
	public static String KEY_PASSWORD = "PASSWORD";
	

	public static String KEY_USER = "USER";
	public static String KEY_EMAIL_USER = "EMAIL_USER";
	public static String KEY_FACEBOOK_USER = "FACEBOOK_USER";
	
	public static String ACTION_LOGIN_RESULT = "SIVA_ARLIMI_LOGIN_RESULT";
	public static String KEY_LOGIN_RESULT = "RESULT";
	
	public static String ACTION_REGISTRATION_RESULT = "SIVA_ARLIMI_REG_RESULT";
	public static String KEY_REGISTRATION_RESULT = "REGISTRATION_RESULT";
	
	public enum ResultType { FACEBOOK_LOGIN, EMAIL_LOGIN,
		REGISTRATION }  
	
	public static final String KEY_RESULT_TYPE = "RESULT_TYPE";
	public static final int VAL_FACEBOOK_RESULT = 100;
	public static final int VAL_EMAIL_RESULT = 200;
	
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
	
	public static Intent checkResult(final Object result, final ResultType type)
	{
		Intent intent = null; 
		
		switch(type)
		{
		
		case FACEBOOK_LOGIN:
			intent = new Intent(ACTION_LOGIN_RESULT);
			intent.putExtra(KEY_RESULT_TYPE, ResultType.FACEBOOK_LOGIN.ordinal());
			intent.putExtra(AuthUtil.KEY_LOGIN_RESULT, ((String)result).trim());
			break;
			
		case EMAIL_LOGIN:
			
			//Email user get result as a json 
			JSONObject json = (JSONObject) result;
			
			String name = "";
			String loginResult = "";
			try
			{
				name = json.getString(KEY_NAME);
				loginResult = json.getString(KEY_LOGIN_RESULT);
				
			} catch (JSONException e)
			{
				e.printStackTrace();
			}
			
			intent = new Intent(ACTION_LOGIN_RESULT);
			intent.putExtra(KEY_RESULT_TYPE, ResultType.EMAIL_LOGIN.ordinal());
			intent.putExtra(AuthUtil.KEY_LOGIN_RESULT, loginResult.trim());
			intent.putExtra(AuthUtil.KEY_NAME, name.trim());
			
			break;
			
		case REGISTRATION:
			intent = new Intent(ACTION_REGISTRATION_RESULT);
			intent.putExtra(KEY_REGISTRATION_RESULT, ((String)result).trim());
			break;
		
			default:
				intent = new Intent();
				break;
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
