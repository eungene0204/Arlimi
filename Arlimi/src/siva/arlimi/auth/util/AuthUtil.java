package siva.arlimi.auth.util;

import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

import siva.arlimi.auth.service.FacebookLoginUserService;
import siva.arlimi.auth.service.NewEmailUserService;
import siva.arlimi.auth.service.NewFacebookUserService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class AuthUtil
{
	public static String ACTIVITY = "ACTIVITY";
	public static String EMAIL = "EMAIL";
	public static String NAME = "NAME";
	public static String PASSWORD = "PASSWORD";
	
	public static String ACTION_FACEBOOK_LOGIN_RESULT = "FACEBOOK_LOGIN_RESULT";
	public static String KEY_FACEBOOK_LOGIN_RESULT = "FACEBOOK_LOGIN_RESULT";
	
	public static int RESULT_VALID_USER = 1;
	public static int RESULT_INVALID_UER = 0;
	
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
	
	public static JSONObject getJSONObject(Intent intent)
	{
		JSONObject json = new JSONObject();
		Bundle bundle = intent.getExtras();
		Set<String> keys = bundle.keySet();
		
		for(String key : keys)
		{
			try
			{
				json.put(key, bundle.get(key));
			}
			catch(JSONException e)
			{
				e.printStackTrace();
			}
		}
		
		return json;
		
	}

}
