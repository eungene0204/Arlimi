package siva.arlimi.shop.connection;

import siva.arlimi.networktask.NetworkConnection;
import siva.arlimi.shop.util.ShopUtils;
import android.content.Context;
import android.util.Log;

public class ShopRegistrationConnection extends NetworkConnection
{
	public final String TAG = "ShopRegistrationConnection";
	
	private final Context mContext;
	private OnShopRegistrationListener mListener;
	
	public ShopRegistrationConnection(Context context)
	{
		mContext = context; 
		
		try
		{
			mListener = (OnShopRegistrationListener)mContext;
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
		
		Log.i(TAG, "result: " + result);
		
		mContext.stopService(ShopUtils.getShopRegistrationIntent(mContext));
		mListener.onShopRegistrationResult(result);
		
	}
	
	public interface OnShopRegistrationListener
	{
		void onShopRegistrationResult(final String result);
	}

}
