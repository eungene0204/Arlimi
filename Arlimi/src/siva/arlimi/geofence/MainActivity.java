package siva.arlimi.geofence;

import java.util.ArrayList;
import java.util.List;

import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationClient.OnAddGeofencesResultListener;
import com.google.android.gms.location.LocationStatusCodes;

public class MainActivity extends FragmentActivity implements 
							ConnectionCallbacks,
							OnConnectionFailedListener,
							OnAddGeofencesResultListener,
							OnClickListener
{
	
	private final String TAG = "Geofence Test";
	
	private final static int 
			CONNECTIONA_FAILURE_RESOLUTION_REQUEST = 9000;
	
    private static final long SECONDS_PER_HOUR = 3600;
    private static final long MILLISECONDS_PER_SECOND = 1000;
    private static final long GEOFENCE_EXPIRATION_IN_HOURS = 12;
    private static final long GEOFENCE_EXPIRATION_TIME =
                     GEOFENCE_EXPIRATION_IN_HOURS * SECONDS_PER_HOUR;
    
    private GeofenceStore mGeofenceStore;
    
    private EditText mLatEditText;
    private EditText mLngEditText;
    private EditText mRadiusEditText;
    private Button regBtn;
   
    private LocationClient mLocationClient;
    private PendingIntent mGeofenceRequestIntent;
    
    public enum REQUEST_TYPE {ADD}
    private REQUEST_TYPE mRequestType;
    
    private List<Geofence> mGeofenceList;
    
    private boolean mInProgress;
    
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_main);
		
		mGeofenceStore = new GeofenceStore(this);
		
		mInProgress = false;
		
		mGeofenceList = new ArrayList<Geofence>();
		
		regBtn = (Button)findViewById(R.id.new_geofence_btn);
		regBtn.setOnClickListener(this);
		
	}
	
	public void addGeofence()
	{
		mRequestType = REQUEST_TYPE.ADD;
		
		if(!serviceConnected())
		{
			Log.e(TAG, "Google service is not available");
			
			return;
		}
		
		mLocationClient = new LocationClient(this,this,this);
		
		if(!mInProgress)
		{
			mInProgress = true;
			mLocationClient.connect();
		}
		else
		{
			//Do something
		}
	}
	
	public void createGeofence()
	{
		int id = 0;
	
		/*
		ArlimiGeofence geofence = new ArlimiGeofence(String.valueOf(id), 
				Double.valueOf(mLatEditText.toString()),
				Double.valueOf(mLngEditText.toString()),
				Float.valueOf(mRadiusEditText.toString()),
				GEOFENCE_EXPIRATION_TIME,
				Geofence.GEOFENCE_TRANSITION_ENTER |
				Geofence.GEOFENCE_TRANSITION_EXIT |
				Geofence.GEOFENCE_TRANSITION_DWELL
				); */
		
			ArlimiGeofence jangji =  new ArlimiGeofence(String.valueOf(id), 
				37.478922,
				127.126143,
				100,
				Geofence.NEVER_EXPIRE,
				Geofence.GEOFENCE_TRANSITION_ENTER |
				Geofence.GEOFENCE_TRANSITION_EXIT  
				); 
			
			
			mGeofenceStore.setGeofence(String.valueOf(id), jangji);
		mGeofenceList.add(jangji.toGeofence());
		id++;
		
			ArlimiGeofence amsa =  new ArlimiGeofence(String.valueOf(id), 
				37.550424,
				127.127530,
				100,
				Geofence.NEVER_EXPIRE,
				Geofence.GEOFENCE_TRANSITION_ENTER |
				Geofence.GEOFENCE_TRANSITION_EXIT  
				); 
		
				
			mGeofenceStore.setGeofence(String.valueOf(id), amsa);
		mGeofenceList.add(amsa.toGeofence());
		id++;
		
				ArlimiGeofence kangdong =  new ArlimiGeofence(String.valueOf(id), 
				37.530752,
				127.120514,
				100,
				Geofence.NEVER_EXPIRE,
				Geofence.GEOFENCE_TRANSITION_ENTER |
				Geofence.GEOFENCE_TRANSITION_EXIT  
				); 
		
				
			mGeofenceStore.setGeofence(String.valueOf(id), kangdong);
		mGeofenceList.add(kangdong.toGeofence());
		id++;
		
		
		
	}
	
	private PendingIntent getTransitionPendingIntent()
	{
		Intent intent = new Intent(this,
				ReceiveArlimiTransitionIntentService.class);
		
		return PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		
	}
	
	private boolean serviceConnected()
	{
		int resultCode = 
				GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
		
		if(ConnectionResult.SUCCESS == resultCode)
		{
			Log.i(TAG, "Goolge service is available");
			
			return true;
		}
		else
		{
			Dialog dialog = GooglePlayServicesUtil.getErrorDialog(resultCode, this, 0);
			if(dialog != null)
			{
				ErrorDialogFragment errorFragment = new ErrorDialogFragment();
				errorFragment.setDialog(dialog);
				errorFragment.show(getSupportFragmentManager(),TAG );
			}
			
			return false;
			
		}	
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onAddGeofencesResult(int statusCode, String[] geofenceRequestIds)
	{
		String ids = TextUtils.join(",", geofenceRequestIds);
		
		if(LocationStatusCodes.SUCCESS == statusCode)
		{
			Log.i(TAG, ids + " Geofence added sccuessfully");
		}
		else
		{
			Log.i(TAG, "Fail to add Geofence");
		}
		
		mInProgress = false;
		mLocationClient.disconnect();
		
	}

	@Override
	public void onConnectionFailed(ConnectionResult result)
	{
		mInProgress = false;
		
		if(result.hasResolution())
		{
			try
			{
				result.startResolutionForResult(this, CONNECTIONA_FAILURE_RESOLUTION_REQUEST);
			} catch (SendIntentException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		else
		{
			int errCode = result.getErrorCode();
			
			Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(errCode, this, CONNECTIONA_FAILURE_RESOLUTION_REQUEST);
			
			if(errorDialog != null)
			{
				ErrorDialogFragment errFragment = new ErrorDialogFragment();
				
				errFragment.setDialog(errorDialog);
				
				errFragment.show(getSupportFragmentManager(), "Geofence Test");
				
			}
		}
	}

	@Override
	public void onConnected(Bundle connectionHint)
	{
		switch(mRequestType)
		{
		
		case ADD:
			mGeofenceRequestIntent =
				getTransitionPendingIntent();
			mLocationClient.addGeofences(mGeofenceList, mGeofenceRequestIntent,
					this);
			break;
		
		default:
			break;
		
		}
	}

	@Override
	public void onDisconnected()
	{
		mInProgress = false;
		
		mLocationClient = null;
		
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
			return mDialog;
		}
		
	} // end of errordialog class

	@Override
	public void onClick(View v)
	{
		switch(v.getId())
		{
		case R.id.new_geofence_btn:
			createGeofence();
			addGeofence();
			break;
			
			default:
				break;
		}
		
	}

}














