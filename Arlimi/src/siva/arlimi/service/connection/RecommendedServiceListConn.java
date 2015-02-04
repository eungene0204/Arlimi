package siva.arlimi.service.connection;

import siva.arlimi.networktask.NetworkConnection;
import siva.arlimi.service.util.ServiceUtil;
import android.content.Context;

public class RecommendedServiceListConn extends NetworkConnection
{
	private final Context mContext;
	private OnRecommendedServiceListListener mCallback;
	
		
	public RecommendedServiceListConn(final Context context)
	{
		this.mContext = context;
	}
	
	@Override
	protected void onPostExecute(String result)
	{
		super.onPostExecute(result);
		
		mContext.stopService(ServiceUtil.getRecommendedServiceIntent(mContext));
	}

	public void setListener(OnRecommendedServiceListListener listener)
	{
		this.mCallback = listener;
	}
	
	public interface OnRecommendedServiceListListener
	{
		void onRecommendedServiceList(final String result);
	}

}
