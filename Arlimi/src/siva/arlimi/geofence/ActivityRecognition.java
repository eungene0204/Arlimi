package siva.arlimi.geofence;

import android.app.Dialog;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.ActivityRecognitionClient;

public class ActivityRecognition implements 
				ConnectionCallbacks, OnConnectionFailedListener
{
	public enum REQUEST_TYPE {START, STOP}
	private REQUEST_TYPE mRequestType;
	
	
	public static final int MILLISECONDS_PER_SECOND = 1000;
	public static final int DETECTION_INTERVAL_SECONDS = 20;
	public static final int DETECTION_INTERVAL_MILISECONDS =
			MILLISECONDS_PER_SECOND * DETECTION_INTERVAL_SECONDS;
	
	private PendingIntent mActivityRecognitionPendingIntent;
	private ActivityRecognitionClient mActivityRecognitionClient;
	
	private FragmentActivity mContext;
	
	private boolean mInProgress;
	
	public ActivityRecognition(FragmentActivity context)
	{
		mContext = context;
		
		mActivityRecognitionClient = new ActivityRecognitionClient(mContext, this, this);
		
		Intent intent = new Intent(mContext,ActivityRecognitionIntentService.class);
		
		mActivityRecognitionPendingIntent = 
				PendingIntent.getService(mContext, 0, intent, 
						PendingIntent.FLAG_UPDATE_CURRENT);
		
		mInProgress = false;
		
	}
	
	public void startUpdates()
	{
		
		mRequestType = REQUEST_TYPE.START;
		
		if(!mInProgress)
		{
			mInProgress = true;
			mActivityRecognitionClient.connect();
		}
		else
		{
			 /*
             * A request is already underway. You can handle
             * this situation by disconnecting the client,
             * re-setting the flag, and then re-trying the
             * request.
             */
		}
	}
	
	public void stopUpdates()
	{
		mRequestType = REQUEST_TYPE.STOP;
		
		if(!mInProgress)
		{
			mInProgress = true;
			mActivityRecognitionClient.connect();
		}
		else
		{
			
		}
		
	}
	

	@Override
	public void onConnectionFailed(ConnectionResult result)
	{
		mInProgress = false;
		
		if(result.hasResolution())
		{
			try
			{
				result.startResolutionForResult(mContext, 
						GeofenceUtils.CONNECTION_FAILURE_RESOLUTION_REQUEST);
			} catch (SendIntentException e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			int errorCode = result.getErrorCode();
			
			Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(errorCode,
					mContext,
					GeofenceUtils.CONNECTION_FAILURE_RESOLUTION_REQUEST);
		
			if( null != errorDialog)
			{
				ErrorDialogFragment errorFragment = 
						new ErrorDialogFragment();
				
				errorFragment.setDialog(errorDialog);
				errorFragment.show(mContext.getSupportFragmentManager(),
						"Activity Recognition");
			}
			
			
		}
	}

	@Override
	public void onConnected(Bundle connectionHint)
	{
		switch(mRequestType)
		{
		case START:
		mActivityRecognitionClient.requestActivityUpdates(DETECTION_INTERVAL_MILISECONDS, 
				mActivityRecognitionPendingIntent);
		break;
		
		case STOP:
			mActivityRecognitionClient.removeActivityUpdates(
					mActivityRecognitionPendingIntent);
			break;
		
		default:
			try
			{
				throw new Exception("Unknown request type in onConntected()");
			} catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		
		}
		
	
		mInProgress = false;
		mActivityRecognitionClient.disconnect();
	}

	@Override
	public void onDisconnected()
	{
		mInProgress = false;
		mActivityRecognitionClient = null;
	}
	
	public static class ErrorDialogFragment extends DialogFragment
	{
		private Dialog mDialog;
		
		public ErrorDialogFragment()
		{
			super();
			mDialog = null;
		}
		
		public void setDialog(Dialog dialog)
		{
			mDialog = dialog;
		}
		
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState)
		{
			// TODO Auto-generated method stub
			return super.onCreateDialog(savedInstanceState);
		}
	}

}
