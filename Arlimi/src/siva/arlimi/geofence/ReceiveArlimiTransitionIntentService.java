package siva.arlimi.geofence;

import java.util.List;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
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
	
	public ReceiveArlimiTransitionIntentService()
	{
		super("ReceiveArlimiTransitionIntentService");
	}

	public ReceiveArlimiTransitionIntentService(String name)
	{
		super(name);
	}

	@Override
	protected void onHandleIntent(Intent intent)
	{
		if(LocationClient.hasError(intent))
		{
			int errorCode = LocationClient.getErrorCode(intent);
			
			Log.e(TAG, "Location Service error: " +
			Integer.toString(errorCode)
			);
		}
		else
		{
			int transitionType =
					LocationClient.getGeofenceTransition(intent);
			
			if(
					(transitionType == Geofence.GEOFENCE_TRANSITION_ENTER)
					||
					(transitionType == Geofence.GEOFENCE_TRANSITION_EXIT)
					||
					(transitionType == Geofence.GEOFENCE_TRANSITION_DWELL)
					)
			{
				List<Geofence> triggerList = 
						LocationClient.getTriggeringGeofences(intent);
				
				String[] triggerIds = new String[triggerList.size()];
				
				for(int i = 0; i < triggerIds.length; i++)
				{
					triggerIds[i] = triggerList.get(i).getRequestId();
				}
				
				String ids = TextUtils.join(GEOFENCE_ID_DELIMITER, triggerIds);
				String transitionStringType = getTransitionString(transitionType);
				
				sendNotification(transitionStringType, ids);
				
				
			}
			else
			{
				Log.e(TAG, "Geofence transition Error: " +
			Integer.toString(transitionType));
			}
		}
	}
	
	private void sendNotification(String transitionStringType, String ids)
	{
		Intent notificationIntent =
				new Intent(getApplicationContext(), MainActivity.class);
		
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
		
		stackBuilder.addParentStack(MainActivity.class);
		
		stackBuilder.addNextIntent(notificationIntent);
		
		PendingIntent notificationPendingIntent =
				stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
		
		NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
		
		builder.setSmallIcon(R.drawable.ic_notification)
				.setContentTitle("Geofece Title" + transitionStringType + ids)
				.setContentText("Geofence test")
				.setContentIntent(notificationPendingIntent);
		
		NotificationManager notificationManager =
				(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		
		notificationManager.notify(0,builder.build());
	}

	private String getTransitionString(int transitionType)
	{
		switch(transitionType)
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

}
