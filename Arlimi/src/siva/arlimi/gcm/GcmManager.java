package siva.arlimi.gcm;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import siva.arlimi.activity.HomeActivity;
import siva.arlimi.networktask.NetworkConnection;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

public class GcmManager
{
	public static final String TAG = "GcmManager";
	
    public static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";
    private static final String SENDER_ID = "199153191927";
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    
	private Context mContext;
	private FragmentActivity mActivity;
	private GoogleCloudMessaging mGcm;
	private String mRegId;
	
	public GcmManager(Context context, FragmentActivity activity)
	{
		this.mContext = context;
		this.mActivity = activity;
	}
	
	public void checkGCMRegistration()
	{
		if(checkPlayServices())
		{
			mGcm = GoogleCloudMessaging.getInstance(mContext);
			mRegId = getRegistrationId(mContext);
			
			if(mRegId.isEmpty())
			{
				registerInBackground();
			}
		}
		else {
            Log.i(TAG, "No valid Google Play Services APK found.");
		}
		
		sendRegistrationIdToBackend();
	}
	
	private void registerInBackground()
	{
		new AsyncTask()
		{
			@Override
			protected Object doInBackground(Object... params)
			{
				String msg = "";
				
				try
				{
					if(null == mGcm)
					{
						mGcm = GoogleCloudMessaging.getInstance(mContext);
					}
					
					mRegId = mGcm.register(mRegId);
					msg = "Device registered, registration ID=" + mRegId;
					
					sendRegistrationIdToBackend();
					
					storeRegistrationId(mContext, mRegId);
					
				}catch(IOException e)
				{
					msg = "Error : " + e.getMessage();
				}
				
				return msg;
			}
		
			@Override
			protected void onPostExecute(Object result)
			{
				super.onPostExecute(result);
				Log.i(TAG,  (String)result.toString());
			}
			
		}.execute(null,null,null);
		
	}
	
	
	private void storeRegistrationId(Context context, String regId)
	{
		final SharedPreferences prefs = getGCMPreferences(context);
		int appVersion = getAppVersion(context);
		Log.i(TAG, "Saving regId on app version" + appVersion);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(PROPERTY_REG_ID, regId);
		editor.putInt(PROPERTY_APP_VERSION, appVersion);
		editor.commit();
	}
	
	private int getAppVersion(Context context)
	{
		try
		{
			PackageInfo packageInfo = 
					context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			
			return packageInfo.versionCode;
		} catch(NameNotFoundException e)
		{
			throw new RuntimeException("Could not get package name:" + e);
		}
	}

	private SharedPreferences getGCMPreferences(Context context)
	{
		return mActivity.getSharedPreferences(HomeActivity.class.getSimpleName(), 
				Context.MODE_PRIVATE);
	}

	
	private void sendRegistrationIdToBackend()
	{
		NetworkConnection conn = new NetworkConnection();
	
		try
		{
			String data = URLEncoder.encode("reg_id", "UTF-8") + "=" 
			+ URLEncoder.encode(mRegId,"UTF-8");
			
			conn.setData(data);
			conn.setURL("http://192.168.0.21:8088/SIVA_Arlimi_Test_Server/gcmRegistration");
			conn.execute(null,null,null);
			
		} catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
	}
	
	private String[] getParam()
	{
		String[] params = new String[2];
//		params[0] = GCMRegistrationId.REGISTRATION_ID;
		params[1] = mRegId;
		
		return params; 
	}
	private String getRegistrationId(Context context)
	{
		final SharedPreferences prefs = getGCMPreferences(context);
		String registrationId = prefs.getString(PROPERTY_REG_ID, "");
		if(registrationId.isEmpty())
		{
			Log.i(TAG, "Registration not found.");
			return "";
		}
		
		int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
		int currentVersion = getAppVersion(context);
		if(registeredVersion != currentVersion)
		{
			Log.i(TAG, "App version changed");
			return "";
		}
		
		return registrationId;
		
	}
	private boolean checkPlayServices()
	{
		int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(mActivity);
		if(resultCode != ConnectionResult.SUCCESS)
		{
			if(GooglePlayServicesUtil.isUserRecoverableError(resultCode))
			{
				GooglePlayServicesUtil.getErrorDialog(resultCode, mActivity, 
						PLAY_SERVICES_RESOLUTION_REQUEST).show();
			}
			else
			{
				Log.i(TAG, "This device is not supported.");
				mActivity.finish();
			}
			
			return false;
		}
		return true;
	}


}
