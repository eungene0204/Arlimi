package siva.arlimi.geofence;

import siva.arlimi.geofence.ReceiveArlimiTransitionIntentService.GeofenceServiceListener;
import siva.arlimi.geofence.ReceiveArlimiTransitionIntentService.LocalBinder;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

public class GeofenceServiceBinder implements GeofenceServiceListener
{
	public static final String TAG = "GeofenceServiceBinder";

	private ReceiveArlimiTransitionIntentService mBoundService;
	private FragmentActivity mContext;
	
	private String[] mEventIds;

	private boolean mIsBound = false;
	private boolean mIsCalled = false;
	

	private ServiceConnection mConnection = new ServiceConnection()
	{
		@Override
		public void onServiceDisconnected(ComponentName name)
		{
			mBoundService = null;
			Log.i(TAG, "onServiceDisconnected");
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service)
		{

			LocalBinder binder = (LocalBinder)service;
			mBoundService = binder.getService();
			
			mBoundService.registerServiceListener(getThis());
			Log.i(TAG, "onServiceConnected");
		}
	};

	public GeofenceServiceBinder(Context context)
	{
		this.mContext = (FragmentActivity)context;
	}

	private GeofenceServiceBinder getThis()
	{
		return this;
	}
	
	public void doBindService()
	{
		Intent intent = 
				new Intent(mContext, ReceiveArlimiTransitionIntentService.class);

		if(mContext.bindService(intent, mConnection, Context.BIND_AUTO_CREATE))
		{
			Log.i(TAG, "binding successed");
		}
		else
		{
			Log.i(TAG, "fail to connect to the service");
		}

		mIsBound = true;
	}

	public void doUnbindService()
	{
		if(mIsBound)
		{
			mContext.unbindService(mConnection);
			mIsBound = false;
		}
	}

	public String[] getEventIds()
	{
		return mEventIds;
	}
	
	private void setEventIds(String[] eventIds)
	{
		this.mEventIds = eventIds;
	}
	
	public boolean isCalled()
	{
		return this.mIsCalled;
	}
	
	@Override
	public void sendEventId(String[] eventIds)
	{
		Log.i(TAG, eventIds[0]);
		
		this.mIsBound = true;
		
		setEventIds(eventIds);
	}

	


}