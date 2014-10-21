package siva.arlimi.shop.connection;

import siva.arlimi.networktask.NetworkConnection;
import siva.arlimi.shop.util.ShopUtils;
import android.content.Context;
import android.util.Log;

public class GeoCodingConnection extends NetworkConnection
{
	public final String TAG = "GeoCodingConnection";
	
	private final Context mContext;
	private OnGeoCodingListener mListener;
	
	public GeoCodingConnection(Context context)
	{
		mContext = context;
		
		try
		{
			mListener = (OnGeoCodingListener) mContext;
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
		
		
		mContext.stopService(ShopUtils.getGeoCodingServiceIntent(mContext));
		mListener.onGeoCoding(result);
		
	}
	
	public interface OnGeoCodingListener
	{
		void onGeoCoding(String result);
		
	}

}
