package siva.arlimi.auth.connection;

import siva.arlimi.auth.interfaces.OnRegistrationResultListener;
import siva.arlimi.auth.service.NewEmailUserService;
import siva.arlimi.networktask.NetworkConnection;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class NewEmailUserConnection extends NetworkConnection
{
	public static String TAG = "NewEmailUserConnection";
	
	private final Context mContext;
	private OnRegistrationResultListener mResultCallback;
	
	public NewEmailUserConnection(Context context)
	{
		mContext = context;
		
		try
		{
			mResultCallback = (OnRegistrationResultListener) mContext;
			
		}
		catch(ClassCastException e)
		{
			e.printStackTrace();
		}	
	}
	
	@Override
	protected void onPostExecute(String result)
	{
		Log.i(TAG, "onPostExcute");
		super.onPostExecute(result);
		
		mContext.stopService(new Intent(mContext, NewEmailUserService.class));
	
		if(null != result)
			mResultCallback.onRegistrationResult(result);
		else
			Log.i(TAG, "result is null");
	
	}

}
