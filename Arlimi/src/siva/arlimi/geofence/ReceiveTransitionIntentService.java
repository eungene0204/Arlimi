package siva.arlimi.geofence;

import java.util.List;

import siva.arlimi.activity.HomeActivity;
import siva.arlimi.activity.R;
import siva.arlimi.location.LocationServiceErrorMessges;
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationClient;

public class ReceiveTransitionIntentService extends IntentService
{
	public ReceiveTransitionIntentService()
	{
		super("ReceiveTransitionIntentService");
	}

	@Override
	protected void onHandleIntent(Intent intent)
	{
		Intent broadcastIntent = new Intent();
		
		broadcastIntent.addCategory(GeofenceUtils.CATEGORY_LOCATION_SERVICES);
		
		//Check for errors
		if(LocationClient.hasError(intent))
		{
			int errorCode = LocationClient.getErrorCode(intent);
			
			String errorMessage = LocationServiceErrorMessges.getErrorString(this, errorCode);
			
			Log.e(GeofenceUtils.APPTAG, 
					getString(R.string.geofence_transition_error_detail,errorMessage));
			
			broadcastIntent.setAction(GeofenceUtils.ACTION_GEOFENCE_ERROR)
							.putExtra(GeofenceUtils.EXTRA_GEOFENCE_STATUS, errorMessage);
			
			LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent);
		}
		else
		{
			int transition = LocationClient.getGeofenceTransition(intent);
			
			if(
					(transition == Geofence.GEOFENCE_TRANSITION_ENTER)
					||
					(transition == Geofence.GEOFENCE_TRANSITION_EXIT)
					
			  )
			{
				List<Geofence> geofences = LocationClient.getTriggeringGeofences(intent);
				String[] geofenceIds = new String[geofences.size()];
				
				for(int i = 0; i < geofences.size(); i++)
				{
					geofenceIds[i] = geofences.get(i).getRequestId();
				}
				
				String ids = TextUtils.join(GeofenceUtils.GEOFENCE_ID_DELIMITER,
						geofenceIds);
				String transitionType = getTransitionString(transition);
				
				sendNotification(transitionType, ids);
				
				Log.i(GeofenceUtils.APPTAG, getString(
						R.string.geofence_transition_notification_title,
						transitionType,
						ids));
				
				Log.i(GeofenceUtils.APPTAG, 
						getString(R.string.geofence_transition_notification_text ));
				
			}
			else //Invalid transition 
			{
				Log.e(GeofenceUtils.APPTAG, 
						getString(R.string.geofence_transition_invalid_type,transition));
			}
		}
		
		
		
	}

	private void sendNotification(String transitionType, String ids)
	{
		Intent notificationIntent =
				new Intent(getApplicationContext(), HomeActivity.class);
		
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
		
		stackBuilder.addParentStack(HomeActivity.class);
		stackBuilder.addNextIntent(notificationIntent);
		
		PendingIntent notificationPendingIntent =
				stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
		
		NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
		
		builder.setSmallIcon(R.drawable.ic_notification);
		builder.setContentTitle(
				getString(R.string.geofence_transition_notification_title,
						transitionType,ids ))
			.setContentIntent(notificationPendingIntent);
		
		NotificationManager mNotificationManager =
				(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		
		mNotificationManager.notify(0, builder.build());
	}

	private String getTransitionString(int transitionType)
	{
		switch(transitionType)
		{
		case Geofence.GEOFENCE_TRANSITION_ENTER:
			return getString(R.string.geofence_transition_entered);
			
		case Geofence.GEOFENCE_TRANSITION_EXIT:
			return getString(R.string.geofence_transition_exited);
			
			default:
				return getString(R.string.geofence_transition_unknown);
		}
	}
	
}

