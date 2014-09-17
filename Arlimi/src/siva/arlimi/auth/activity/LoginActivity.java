package siva.arlimi.auth.activity;

import siva.arlimi.activity.HomeActivity;
import siva.arlimi.auth.fragment.RegistrationDialogFragment;
import siva.arlimi.auth.util.AuthUtil;
import siva.arlimi.facebook.FaceBookManager;
import siva.arlimi.facebook.FaceBookManager.OnLoginUserListener;
import siva.arlimi.facebook.FaceBookManager.OnRegisterNewUserListener;
import siva.arlimi.main.R;
import siva.arlimi.person.FacebookUser;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class LoginActivity extends FragmentActivity
					implements OnClickListener , OnRegisterNewUserListener,
								OnLoginUserListener 
{
	public static final String TAG = "LoginActivity";
	
	private boolean mIsResume = false;
	
	private Button mRegistrationBtn;
	private RegistrationDialogFragment fragment;
	private FaceBookManager mFacebookMng;
	
	private BroadcastReceiver mFacbookUserResult = new BroadcastReceiver()
	{
		public void onReceive(android.content.Context context, Intent intent) 
		{
			int result =
					intent.getIntExtra(AuthUtil.KEY_FACEBOOK_LOGIN_RESULT,
							AuthUtil.RESULT_INVALID_UER);
			
			Log.i(TAG, "facebook result" + result);
		}
		
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		mFacebookMng = new FaceBookManager(this, savedInstanceState);
		mFacebookMng.setIsNew(false);
		
		mRegistrationBtn = (Button)
				findViewById(R.id.registration_btn);
		
		mRegistrationBtn.setOnClickListener(this);
	
	}


	@Override
	protected void onResume()
	{
		Log.i(TAG, "onResume");
		super.onResume();
		 mIsResume = true;
		 mFacebookMng.onResume();
		 
		 IntentFilter filter = new IntentFilter(AuthUtil.ACTION_FACEBOOK_LOGIN_RESULT);
		 
		 registerReceiver(mFacbookUserResult, filter);
	
	}
	
	private void showHomeActivity()
	{
		Intent intent = new Intent(this, HomeActivity.class);
		
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
				Intent.FLAG_ACTIVITY_CLEAR_TOP);
		
		startActivity(intent);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		Log.i(TAG, "onActivityResult");
		
		super.onActivityResult(requestCode, resultCode, data);
		mFacebookMng.onActivityResult(requestCode, resultCode, data);
	}
	
	@Override
	protected void onPause()
	{
		Log.i(TAG, "onPause");
		super.onPause();
		mIsResume = false;
		mFacebookMng.onPause();
		
		unregisterReceiver(mFacbookUserResult);
	}
	
	@Override
	protected void onDestroy()
	{
		Log.i(TAG, "onDestroy");
		
		super.onDestroy();
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState)
	{
		Log.i(TAG, "onSaveInstanceState");
		
		super.onSaveInstanceState(outState);
		mFacebookMng.onSaveInstanceState(outState);
	}

	@Override
	public void onClick(View v)
	{
		switch(v.getId())
		{
		case R.id.registration_btn:
			openRegistrationDialogFragment();
		break;
		
		case R.id.login_btn:
			
		break;
		
		default:
			break;
		
		}
	}

	private void openRegistrationDialogFragment()
	{
		fragment = new RegistrationDialogFragment(mFacebookMng);
	
		fragment.show(getSupportFragmentManager(), "Dialog fragment");
	}

	@Override
	public void registerNewUser(FacebookUser user)
	{
		Log.i(TAG, "requestCompleted");
		
		if(user.isValid())
		{
			Bundle bundle = new Bundle();
			bundle.putString(AuthUtil.EMAIL, user.getEmail());
			bundle.putString(AuthUtil.NAME, user.getName());
		
			Intent intent = AuthUtil.getNewFaceBookUserIntent(this);
			intent.putExtras(bundle);
			
			startService(intent);
			
			if(null != fragment)
				fragment.dismiss();
			
			//finish();
			
		}
	}
	
	@Override
	public void loginUser(FacebookUser user)
	{
		if(user.isValid())
		{
			Log.i(TAG, "login email" + user.getEmail());
			
			Bundle bundle = new Bundle();
			bundle.putString(AuthUtil.EMAIL, user.getEmail());
			
			Intent intent = AuthUtil.getFacebookLoginIntent(this);
			intent.putExtras(bundle);
			
			startService(intent);
			
		}
	}
	
	@Override
	public String toString()
	{
		return getClass().getName();
	}
	


}
