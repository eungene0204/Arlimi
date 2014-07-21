package siva.arlimi.activity;

import siva.arlimi.adapter.TabPagerAdapter;
import siva.arlimi.fragment.ErrorDialogFragment;
import siva.arlimi.gcm.GcmManager;
import siva.arlimi.geofence.GeofenceManager;
import siva.arlimi.location.AddressManager;
import siva.arlimi.location.ArlimiLocationClient;
import siva.arlimi.owner.Owner;
import siva.arlimi.quickaction.SettingPopUp;
import siva.arlimi.ui.UIManager;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.Session.StatusCallback;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.ProfilePictureView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

public class HomeActivity extends FragmentActivity implements OnClickListener,
		ActionBar.TabListener
{
	private static final String TAG = "HomeActivity";
	public static final String EXTRA_MESSAGE = "message";

	private UIManager mUIManager;
	private GcmManager mGcmManager;
	private GeofenceManager mGeofenceManager;
	
	//private Event mEvent;
	private Owner mPersonInfo;
	
	// private FaceBookTask mFaceBook;
	
	private ProfilePictureView mFacebookUserPic;

	private ArlimiLocationClient mLocationClient;
	
	private UiLifecycleHelper mUihelper;
	private Session.StatusCallback callback = new StatusCallback()
	{
		@Override
		public void call(Session session, SessionState state, Exception exception)
		{
			onSessionStateChage(session, state, exception);
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Log.i(TAG, "onCreate");

		if (googleServiceConnected())
			Log.i(TAG, "google play is available");
		else
			Log.i(TAG, "google play is not available");

		// UI
		mUIManager = new UIManager(this, getActionBar());
		mUIManager.setActionBar();
		mUIManager.addTab(TabPagerAdapter.TAB_COUNT);
		mUIManager.viewPagerPageChangerListener();
		
		//Facebook
		mUihelper = new UiLifecycleHelper(this, callback);
		mUihelper.onCreate(savedInstanceState);
		mFacebookUserPic = new ProfilePictureView(this);
		
		//Owner
		mPersonInfo = new Owner();
		
		//Geofence
		mGeofenceManager = new GeofenceManager(this);
		mGeofenceManager.doBindService();
	
		// Check GCM
		// mGcmManager = new GcmManager(context,this);
		// mGcmManager.checkGCMRegistration(); 
		/*
		 * mLocationManager = new ArlimiLocationManager(this);
		 * if(mLocationManager.canGetLocation()) { double latitude =
		 * mLocationManager.getLatitude(); double longitude =
		 * mLocationManager.getLongitude(); Geocoder geoCoder = new
		 * Geocoder(this,Locale.KOREA); List<Address> addressList = null; String
		 * result = ""; try { addressList = geoCoder.getFromLocation(latitude,
		 * longitude, 5); //result = mLocationManager.getAddress();
		 * 
		 * } catch (IOException e) { e.printStackTrace(); }
		 * catch(InterruptedException e) { } catch(ExecutionException e) { }
		 * 
		 * Log.i(TAG, "latitude: " + String.valueOf(latitude)); Log.i(TAG,
		 * "Longitude: " + String.valueOf(longitude));
		 * 
		 * mLocationManager.addProximity(latitude, longitude);
		 * 
		 * TextView view = (TextView)findViewById(R.id.location_text_edittext);
		 * String str = String.format(
		 * "latitude: %f\n longitude: %f\n myaddress: %s\n result: %s\n",
		 * latitude,longitude,addressList.get(0).toString(),result);
		 * 
		 * view.setText(str);
		 * 
		 * } else { mLocationManager.showSettingAlert();
		 * 
		 * }
		 */
	}

	private boolean googleServiceConnected()
	{
		int resultCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(this);

		if (ConnectionResult.SUCCESS == resultCode)
		{
			/*
			Log.i(GeofenceUtils.APPTAG,
					getString(R.string.play_services_available)); */

			return true;
		} else
		{
			Dialog dialog = GooglePlayServicesUtil.getErrorDialog(resultCode,
					this, 0);
			if (dialog != null)
			{
				ErrorDialogFragment errorFragment = new ErrorDialogFragment();
				errorFragment.setDialog(dialog);
				/*
				errorFragment.show(getSupportFragmentManager(),
						GeofenceUtils.APPTAG); */
			}

			return false;

		}
	}

	private void checkNetwork()
	{
		/*
		 * //Check availableNetwork ConnectivityManager connMgr =
		 * (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		 * NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		 * if(networkInfo != null && true == networkInfo.isConnected()) { //Do
		 * something with message box }
		 */
	}

	private void onSessionStateChage(Session session, SessionState state,
			Exception exception)
	{
		if(session != null)
		{
			if(session.isOpened())
			{
				makeMeRequest(session);
			}
			
		}

	}

	private void makeMeRequest(final Session session)
	{
		Log.i(TAG, "makeMeRequest");
		Request request = Request.newMeRequest(session,
				new Request.GraphUserCallback()
				{
					@Override
					public void onCompleted(GraphUser user, Response response)
					{
						Log.i(TAG, "onCompleted");
						// If the response is successful
						if (session == Session.getActiveSession())
						{
							if (user != null)
							{
								mFacebookUserPic.setProfileId(user.getId());
								String name = user.getName();
								String email = user.asMap().get("email").toString();
								
								mPersonInfo.setName(name);
								mPersonInfo.setEmail(email);
								mPersonInfo.setFacebookProfileImg(mFacebookUserPic);
								mPersonInfo.setmId(user.getId());
								Log.i(TAG, "User name " + mPersonInfo.getName());
							}
						}
						if (response.getError() != null)
						{
							// Handle error
						}
					}
				});

		request.executeAsync();
	}
	
	public Owner getPersonInfo()
	{
		return mPersonInfo;
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.home_activity_actions, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
		case R.id.action_search:
			break;
		case R.id.main_action_setting:
			SettingPopUp popup = new SettingPopUp(this, "hello");
			View view = this.findViewById(R.id.main_action_setting);
			popup.show(view);
			break;
		default:
			return super.onOptionsItemSelected(item);
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onStart()
	{
		super.onStart();
		Log.i(TAG, "onStart");
		// mLocationClient.connect();
	}

	@Override
	protected void onResume()
	{
		Log.i(TAG, "onResume");
		super.onResume();
		mUihelper.onResume();
	
		Session session = Session.getActiveSession();
		if(session != null)
		{
			makeMeRequest(session);
		} 
		
		/*
		 * if(mPrfs.contains(getString(R.string.key_updates_on))) {
		 * mLocationClient.
		 * setUpdatesRequested(mPrfs.getBoolean(getString(R.string
		 * .key_updates_on), false)); } else {
		 * mEditor.putBoolean(getString(R.string.key_updates_on), false);
		 * mEditor.commit(); }
		 */

		// mLocationManager.addProximity(mLocationManager.getLatitude(),
		// mLocationManager.getLongitude());
		// mFaceBook.onResume();
		// checkPlayServices();
	}

	@Override
	protected void onPause()
	{
		Log.i(TAG, "onPause");

		super.onPause();
		mUihelper.onPause();

		// mGeofenceManager.onPause();
		// mFaceBook.onPause();
		// mLocationManager.removeProximityAlert();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent)
	{
		super.onActivityResult(requestCode, requestCode, intent);
		mUihelper.onActivityResult(requestCode, resultCode, intent);
		//mGeofenceManager.onResult(requestCode, resultCode, intent);
	}

	@Override
	protected void onStop()
	{
		/*
		 * if(mLocationClient.isConnected()) {
		 * mLocationClient.removeLocationUpdates(); }
		 * 
		 * mLocationClient.disconnect();
		 */

		super.onStop();
		mUihelper.onStop();
		mGeofenceManager.doUnbindService();
		// mFaceBook.onStop();
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		mUihelper.onStop();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);
		mUihelper.onSaveInstanceState(outState);
	}

	@Override
	public void onClick(View v)
	{
		if (v.getId() == R.id.show_location_btn)
		{
			Location location = mLocationClient.getLastLocation();
			mLocationClient.setUpdatesRequested(true);
			Log.i(TAG, "latitude: " + location.getLatitude());
			Log.i(TAG, "longitude: " + location.getLongitude());

			AddressManager addMng = new AddressManager(this);
			addMng.setProgressBarVisibility(View.VISIBLE);
			addMng.execute(location);

		} else if (v.getId() == R.id.geofence_add_btn)
		{
			//mGeofenceManager.registerGeofence();
		}

	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft)
	{
		// TODO Auto-generated method stub

	}

}
