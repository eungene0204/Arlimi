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
	
	private EventListener mGeofenceManager;
	
	private final GeofenceServiceBinder mThis = this;

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
			
			if(null != mBoundService)
				mBoundService.registerServiceListener(mThis);
			
			Log.i(TAG, "onServiceConnected");
		}
	};

	public GeofenceServiceBinder(Context context)
	{
		this.mContext = (FragmentActivity)context;
	}

	
	public void registerEventListener(EventListener manager)
	{
		this.mGeofenceManager = manager;
	}
	
	public void doBindService()
	{
		Intent intent = 
				new Intent(mContext, ReceiveArlimiTransitionIntentService.class);

		if(mContext.bindService(intent, mConnection, Context.BIND_AUTO_CREATE))

		{
			Log.i(TAG, "binding successed");
			mIsBound = true;
			
		}
		else
		{
			Log.i(TAG, "fail to connect to the service");
		}

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

	public boolean isCalled()
	{
		return this.mIsCalled;
	}

	
	@Override
	public void setEventId(String[] eventIds)
	{
		Log.i(TAG, "Event Id is " + eventIds[0]);
		mGeofenceManager.onNewEventIDs(eventIds);
	}

	public interface EventListener
	{
		void onNewEventIDs(String[] eventIds);
	}
	

	


}