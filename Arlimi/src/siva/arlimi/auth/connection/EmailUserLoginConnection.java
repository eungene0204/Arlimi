package siva.arlimi.auth.connection;

import siva.arlimi.auth.interfaces.OnLoginResultListener;
import siva.arlimi.auth.util.AuthUtil;
import siva.arlimi.networktask.NetworkConnection;
import android.content.Context;

public class EmailUserLoginConnection extends NetworkConnection
{
	private final Context mContext;
	private OnLoginResultListener mCallback;
	
	public EmailUserLoginConnection(Context context)
	{
		this.mContext = context;
		
		try
		{
			mCallback = (OnLoginResultListener) mContext;
		}
		catch(ClassCastException e)
		{
			throw new ClassCastException(mContext.toString() +
					"must implement OnLoginResultListener");
		}
	}
	
	@Override
	protected void onPostExecute(String result)
	{
		super.onPostExecute(result);
		
		mContext.stopService(AuthUtil.getEmailLoginIntent(mContext));
		mCallback.OnLoginResult(result);
		
	}

}
