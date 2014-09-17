package siva.arlimi.auth.connection;

import siva.arlimi.auth.util.AuthUtil;
import siva.arlimi.networktask.NetworkConnection;
import android.content.Context;

public class LoginFacebookUserConnection extends NetworkConnection
{
	private final Context mContext; 
	
	private OnFacebookLoginResultListener mCallback;
	
	public LoginFacebookUserConnection(Context context)
	{
		this.mContext = context;
		
		try
		{
			mCallback = (OnFacebookLoginResultListener) mContext;
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
		mCallback.facebookLoginResult(result);
		
	}
	
	public interface OnFacebookLoginResultListener
	{
		void facebookLoginResult(String result);
	}

}
