package siva.arlimi.json;

import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

import siva.arlimi.auth.util.AuthUtil;
import siva.arlimi.user.User;
import android.content.Intent;

public class JSONHelper
{
	public static JSONObject getJSONObjectFromIntent(Intent intent)
	{
		JSONObject json = new JSONObject();

		try
		{
			Set<String> keys = intent.getExtras().keySet();

			for (String key : keys)
			{
				try
				{
					json.put(key, intent.getStringExtra(key));
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
