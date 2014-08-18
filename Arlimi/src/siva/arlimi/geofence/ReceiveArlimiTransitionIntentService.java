package siva.arlimi.geofence;

import java.util.ArrayList;
import java.util.List;

import siva.arlimi.activity.HomeActivity;
import siva.arlimi.activity.R;
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationClient;

public class ReceiveArlimiTransitionIntentService extends IntentService
{
	private static final String TAG = "ReceiveArlimiTransitionIntentService";
	public static final CharSequence GEOFENCE_ID_DELIMITER = ",";
	
	static final int MSG_REGISTER_CLIENT = 1;
	static final int MSG_UNREGISTER_CLIENT = 2;
	static final int MSG_SET_VALUE = 3;
	
	
	private GeofenceServiceListener mGeofenceServiceListener;

	public class LocalBinder extends Binder
	{
		ReceiveArlimiTransitionIntentService getService()
		{
			return ReceiveArlimiTransitionIntentService.this;
		}
	}

	public ReceiveArlimiTransitionIntentService()
	{
		super("ReceiveArlimiTransitionIntentService");
	}

	public ReceiveArlimiTransitionIntentService(String name)
	{
		super(name);
	}
	
	public void registerServiceListener(GeofenceServiceListener listener)
	{
		Log.i(TAG, listener.toString());
		this.mGeofenceServiceListener = listener;
	}

	@Override
	public IBinder onBind(Intent intent)
	{
		Log.i(TAG, "OnBind");
		return mBinder;
	}

	private final IBinder mBinder = new LocalBinder();

	private String[] triggerIds;
	
	@Override
	protected void onHandleIntent(Intent intent)
	{
		Log.i(TAG, "onHandleIntent");
		parseGeofence(intent);
	}
	
	private void parseGeofence(Intent intent)
	{
		if (LocationClient.hasError(intent))
		{
			int errorCode = LocationClient.getErrorCode(intent);

			Log.e(TAG, "Location Service error: " + Integer.toString(errorCode));
		} 
		else // has Geofence Info
		{
			int transitionType = LocationClient.getGeofenceTransition(intent);

			if ((transitionType == Geofence.GEOFENCE_TRANSITION_ENTER)
					|| (transitionType == Geofence.GEOFENCE_TRANSITION_EXIT)
					|| (transitionType == Geofence.GEOFENCE_TRANSITION_DWELL))
			{
				List<Geofence> triggerList = LocationClient
						.getTriggeringGeofences(intent);
			
				//Check if there is Geofence
				if(!triggerList.isEmpty())
				
				//Set Geofence Ids
				triggerIds = new String[triggerList.size()];

				for (int i = 0; i < triggerIds.length; i++)
				{
					triggerIds[i] = triggerList.get(i).getRequestId();
				}

				//Send Event Id 
				sendEventId(triggerIds);
				
				String ids = TextUtils.join(GEOFENCE_ID_DELIMITER, triggerIds);
				String transitionStringType = getTransitionString(transitionType);

				sendNotification(transitionStringType, ids);

			}
			else
			{
				Log.e(TAG, "Geofence transition Error: "
								+ Integer.toString(transitionType));
			}
		}
	}

	private void sendEventId(String[] triggerIds)
	{
		String[] eventId = (triggerIds == null) ? new String[0] :
			triggerIds;
	
		if(mGeofenceServiceListener == null)
			throw new NullPointerException("GeofenceListener is null");
		else
			mGeofenceServiceListener.setEventId(eventId);
	}

	public String[] getEventIds()
	{
		if(triggerIds == null)
			Log.i(TAG, "id is null");
		
		return (triggerIds == null) ? triggerIds = new String[0] :
					triggerIds;
	}

	private void sendNotification(String transitionStringType, String ids)
	{
		Intent notificationIntent = new Intent(getApplicationContext(),
				HomeActivity.class);

		TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

		stackBuilder.addParentStack(HomeActivity.class);

		stackBuilder.addNextIntent(notificationIntent);

		PendingIntent notificationPendingIntent = stackBuilder
				.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

		NotificationCompat.Builder builder = new NotificationCompat.Builder(
				this);

		
		  builder.setSmallIcon(R.drawable.ic_notification)
		  .setContentTitle("SIVA Geofece" + transitionStringType + ids)
		  .setContentText("Geofence Success!!")
		  .setContentIntent(notificationPendingIntent);
		 

		NotificationManager notificationManager = (NotificationManager) 
				getSystemService(Context.NOTIFICATION_SERVICE);

		notificationManager.notify(0, builder.build());
	}

	private String getTransitionString(int transitionType)
	{
		switch (transitionType)
		{
		case Geofence.GEOFENCE_TRANSITION_DWELL:
			return "User dewells";

		case Geofence.GEOFENCE_TRANSITION_ENTER:
			return "User Enters";

		case Geofence.GEOFENCE_TRANSITION_EXIT:
			return "User Exits";

		default:
			return "User Unknown action";

		}
	}
	
	public interface GeofenceServiceListener
	{
		void setEventId(String[] eventIds);
	}

}
