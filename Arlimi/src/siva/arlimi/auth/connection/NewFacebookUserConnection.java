package siva.arlimi.auth.connection;

import siva.arlimi.auth.util.AuthUtil;
import siva.arlimi.networktask.NetworkConnection;
import android.content.Context;

public class NewFacebookUserConnection extends NetworkConnection
{
	private final Context mContext;
	
	public NewFacebookUserConnection(Context context)
	{
		this.mContext = context;
	}
	
	@Override
	protected void onPostExecute(String result)
	{
		super.onPostExecute(result);
		
		mContext.stopService(AuthUtil.getNewFaceBookUserIntent(mContext));
	}

}
