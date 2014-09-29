package siva.arlimi.auth.connection;

import siva.arlimi.auth.interfaces.OnLoginResultListener;
import siva.arlimi.auth.util.AuthUtil;
import siva.arlimi.networktask.NetworkConnection;
import android.content.Context;

public class FacebookUserLoginConnection extends NetworkConnection
{
	private final Context mContext; 
	
	private OnLoginResultListener mCallback;
	
	public FacebookUserLoginConnection(Context context)
	{
		this.mContext = context;
		
		try
		{
			mCallback = (OnLoginResultListener) mContext;
		}
		catch(ClassCastException e)
		{
			throw new ClassCastException(mContext.toString() +
					"must implement OnFacebookLoginResultListener");
		}
	}
	
	@Override
	protected void onPostExecute(String result)
	{
		super.onPostExecute(result);

		
		mContext.stopService(AuthUtil.getFacebookLoginIntent(mContext));
		mCallback.OnLoginResult(result);
		
	}

}
