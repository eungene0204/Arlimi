package siva.arlimi.location.connection;

import siva.arlimi.location.util.LocationUtil;
import siva.arlimi.networktask.NetworkConnection;
import android.content.Context;

public class ReverseGeocodingConnection extends NetworkConnection
{
	private final Context mContext;
	private OnReversGeocodingListener mCallback;
	
	public ReverseGeocodingConnection(Context context)
	{
		mContext = context;
		
		try
		{
			mCallback= (OnReversGeocodingListener)mContext;
		}
		catch(ClassCastException e)
		{
			e.printStackTrace();
		}
		
	}
	
	@Override
	protected void onPostExecute(String result)
	{
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		
		mContext.stopService(LocationUtil.getReverseGeocodingIntent(mContext));
		mCallback.onReversGeoCodingAddress(result);
		
	}
	
	public interface OnReversGeocodingListener
	{
		void onReversGeoCodingAddress(String addr);
	}
}
