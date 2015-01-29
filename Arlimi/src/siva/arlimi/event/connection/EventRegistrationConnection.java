package siva.arlimi.event.connection;

import siva.arlimi.event.util.EventUtils;
import siva.arlimi.networktask.NetworkConnection;
import android.content.Context;

public class EventRegistrationConnection extends NetworkConnection
{
	private final Context mContext;
	private OnEventRegistrationListener mCallback;
	
	public EventRegistrationConnection(Context context)
	{
		mContext = context;
	}
	
	public void setListener(OnEventRegistrationListener callback)
	{
		this.mCallback = callback;
	}
	
	@Override
	protected void onPostExecute(String result)
	{
		super.onPostExecute(result);
		
		mContext.stopService(EventUtils.getEventRegistrationIntent(mContext));
		mCallback.onEventRegistration(result);
		
	}
	
	public interface OnEventRegistrationListener
	{
		void onEventRegistration(final String result);
	}

}
