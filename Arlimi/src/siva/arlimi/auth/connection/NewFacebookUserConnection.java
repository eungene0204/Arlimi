package siva.arlimi.auth.connection;

import siva.arlimi.auth.interfaces.OnRegistrationResultListener;
import siva.arlimi.auth.util.AuthUtil;
import siva.arlimi.networktask.NetworkConnection;
import android.content.Context;
import android.content.Intent;

public class NewFacebookUserConnection extends NetworkConnection 
{
	private final Context mContext;
	private OnRegistrationResultListener mResultCallback;
	
	public NewFacebookUserConnection(Context context)
	{
		this.mContext = context;
		
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
		super.onPostExecute(result);
		
		mContext.stopService(AuthUtil.getNewFaceBookUserIntent(mContext));
		
		mResultCallback.onRegistrationResult(result);
	}

}
