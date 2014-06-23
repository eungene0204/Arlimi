package siva.arlimi.geofence;

import siva.arlimi.activity.R;

import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;

import android.app.IntentService;
import android.content.Intent;

public class ActivityRecognitionIntentService extends IntentService
{
	public ActivityRecognitionIntentService(String name)
	{
		super(name);
	}

	@Override
	protected void onHandleIntent(Intent intent)
	{
		if(ActivityRecognitionResult.hasResult(intent))
		{
			ActivityRecognitionResult result = 
					ActivityRecognitionResult.extractResult(intent);
			
			DetectedActivity mostProbableActivity = 
					result.getMostProbableActivity();
			
			int confidence = mostProbableActivity.getConfidence();
			
			int activityType = mostProbableActivity.getType();
			String activityName = getNameFromType(activityType);
			
		}
		
	}

	private String getNameFromType(int activityType)
	{
		switch(activityType)
		{
		case DetectedActivity.IN_VEHICLE:
			return "in_vehicle";
			
		case DetectedActivity.ON_BICYCLE:
			return "on_bicyle";
			
		case DetectedActivity.ON_FOOT:
			return "on_foot";
			
		case DetectedActivity.STILL:
			return "still";
		}
		return "unknown";
	}

}
