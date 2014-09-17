package siva.arlimi.auth.connection;

import siva.arlimi.auth.service.NewEmailUserService;
import siva.arlimi.networktask.NetworkConnection;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class NewEmailUserConnection extends NetworkConnection
{
	public static String TAG = "NewEmailUserConnection";
	
	private final Context mContext;
	public NewEmailUserConnection(Context context)
	{
		mContext = context;
	}
	
	@Override
	protected void onPostExecute(String result)
	{
		Log.i(TAG, "onPostExcute");
		super.onPostExecute(result);
		
		mContext.stopService(new Intent(mContext, NewEmailUserService.class));
	
	}

}
