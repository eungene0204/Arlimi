package siva.arlimi.auth.activity;

import siva.arlimi.activity.HomeActivity;
import siva.arlimi.auth.fragment.RegistrationDialogFragment;
import siva.arlimi.auth.interfaces.OnUserLoginListener;
import siva.arlimi.auth.session.SessionManager;
import siva.arlimi.auth.util.AuthUtil;
import siva.arlimi.facebook.FaceBookManager;
import siva.arlimi.main.MainActivity;
import siva.arlimi.main.R;
import siva.arlimi.user.EmailUser;
import siva.arlimi.user.FacebookUser;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;


public class LoginActivity extends FragmentActivity
					implements OnClickListener ,OnUserLoginListener 
{
	public static final String TAG = "LoginActivity";
	
	private boolean mIsResume = false;
	
	private Button mRegistrationBtn;
	private Button mLoginBtn;
	
	private RegistrationDialogFragment fragment;
	private FaceBookManager mFacebookMng;
	
	private EmailUser mEmailUser;
	
	private BroadcastReceiver mLoginResult = new BroadcastReceiver()
	{
		public void onReceive(android.content.Context context, Intent intent) 
		{
			onReceiveLoginResult(context, intent);
		}
		
	};
	
	private BroadcastReceiver mRegistrationResult = new BroadcastReceiver()
	{
		
		@Override
		public void onReceive(Context context, Intent intent)
		{
			onReceiveRegistraionResult(context, intent);
			
		}

	
	};
	
	private SessionManager session;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		mFacebookMng = new FaceBookManager(this, savedInstanceState);
		mFacebookMng.setIsNew(false);
		mFacebookMng.setLoginResultListener(this);
		
		session = new SessionManager(getApplicationContext());
		
		mRegistrationBtn = (Button)
				findViewById(R.id.registration_btn);
		mRegistrationBtn.setOnClickListener(this);
		
		mLoginBtn = (Button)
				findViewById(R.id.login_btn);
		mLoginBtn.setOnClickListener(this);

	}

	private void onReceiveRegistraionResult(Context context, Intent intent)
	{
		
	}

	protected void onReceiveLoginResult(Context context, Intent intent)
	{
		String result = intent.getStringExtra(AuthUtil.KEY_LOGIN_RESULT);
		int type = intent.getIntExtra(AuthUtil.KEY_RESULT_TYPE, -1);

		if (result.equals(AuthUtil.VALID_USER))
		{
			if (AuthUtil.ResultType.FACEBOOK_LOGIN.ordinal() == type)
			{
					
				FacebookUser user = mFacebookMng.getFacebookUser();
				Log.d(TAG, user.getEmail());

				// CreateSession
				session.createLoginSession(user.getEmail(), user.getName());
		
			}
			else if(AuthUtil.ResultType.EMAIL_LOGIN.ordinal() == type)
			{
				String name = intent.getStringExtra(AuthUtil.KEY_NAME);
				session.createLoginSession(null, name);
			}
		
			Intent i = new Intent(getApplicationContext(),MainActivity.class );
			
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	
			startActivity(i);
			
			finish();
		}
		else
		{
			//Show invalid User alertDialog
		}
		
	}


	@Override
	protected void onResume()
	{
		Log.i(TAG, "onResume");
		super.onResume();
		 mIsResume = true;
		 mFacebookMng.onResume();
		 
		 IntentFilter loginFilter = new IntentFilter(AuthUtil.ACTION_LOGIN_RESULT);
		 IntentFilter regFilter = new IntentFilter(AuthUtil.ACTION_REGISTRATION_RESULT);
		 
		 registerReceiver(mLoginResult, loginFilter);
		 registerReceiver(mRegistrationResult, regFilter);
		 
	
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
		
		unregisterReceiver(mLoginResult);
		unregisterReceiver(mRegistrationResult);
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
			getUserInfo();
		break;
		
		default:
			break;
		
		}
	}

	private void getUserInfo()
	{
		EditText emailEdit = (EditText)findViewById(R.id.login_email_et);
		EditText passwordEdit = (EditText)findViewById(R.id.login_password_et);
		
		mEmailUser = new EmailUser("", emailEdit.getText().toString(),
				passwordEdit.getText().toString());
		
		emailUserLogin(mEmailUser);
	
	}


	private void openRegistrationDialogFragment()
	{
		fragment = new RegistrationDialogFragment(mFacebookMng);
		fragment.show(getSupportFragmentManager(), "Dialog fragment");
	}


	@Override
	public void facebookUserLogin(FacebookUser user)
	{
		// TODO Auto-generated method stub
		if(user.isValid())
		{
			Log.i(TAG, "login email" + user.getEmail());
			
			Intent intent = AuthUtil.getFacebookLoginIntent(this);
			intent.putExtra(AuthUtil.KEY_USER, user);
			
			startService(intent);
			
		}
	}


	@Override
	public void emailUserLogin(EmailUser user)
	{
		Intent intent = AuthUtil.getEmailLoginIntent(this);
		intent.putExtra(AuthUtil.KEY_USER, user);

		startService(intent);
	}

	@Override
	public String toString()
	{
		return getClass().getName();
	}

	


}
