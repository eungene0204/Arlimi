package siva.arlimi.service.connection;

import siva.arlimi.networktask.NetworkConnection;
import siva.arlimi.service.util.ServiceUtil;
import android.content.Context;

public class AllServiceListConn extends NetworkConnection
{
	
	private final Context mContext;
	private OnAllServiceListListener mListener;
	
	public AllServiceListConn(final Context context)
	{
		this.mContext = context;
	}
	
	public void setListener(OnAllServiceListListener listener)
	{
		this.mListener = listener;
	}
	
	@Override
	protected void onPostExecute(String result)
	{
		super.onPostExecute(result);
		
		mListener.onAllServiceList(result);
	}
	
	public interface OnAllServiceListListener
	{
		void onAllServiceList(String result);
	}
	
	

}
