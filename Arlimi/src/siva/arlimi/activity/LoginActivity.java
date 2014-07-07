package siva.arlimi.activity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import com.facebook.Session;
import com.facebook.Session.StatusCallback;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;


public class LoginActivity extends Activity
{
	private static final String TAG = "LoginActivity";
	
	private boolean mIsResume = false;
	
	private UiLifecycleHelper uiHelper;
	
	
	private Session.StatusCallback callback = new StatusCallback()
	{
		
		@Override
		public void call(Session session, SessionState state, Exception exception)
		{
			onSessionStateChane(session,state,exception);
		}
	};
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		uiHelper = new UiLifecycleHelper(this, callback);
		uiHelper.onCreate(savedInstanceState);
		
		printHashKey();

		//LoginButton authButton = (LoginButton)findViewById(R.id.facebook_login_btn);
		//authButton.setReadPermissions(Arrays.asList("basic_info", "email"));
		
	}
	
        private void onSessionStateChane(Session session, SessionState state,
                        Exception exception)
        {
                // TODO Auto-generated method stub
                
        }

	@Override
	protected void onResume()
	{
		Log.i(TAG, "onResume");
		super.onResume();
		 mIsResume = true;
		 uiHelper.onResume();
		 
		 Session session = Session.getActiveSession();
		 
		 if(session != null)
		 {
			 if(session.isOpened())
			 {
				 Log.i(TAG, "HomeActivity session is opened");
				showHomeActivity(); 
			 }
			 else
			 {
				 Log.i(TAG, session.toString());
				 
				 
			 }
		 }
		 else
		 {
			 Log.i(TAG, "HomeAcitivity session is null");
		 }
		
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
		uiHelper.onActivityResult(requestCode, resultCode, data);
		
	}
	
	@Override
	protected void onPause()
	{
		Log.i(TAG, "onPause");
		super.onPause();
		mIsResume = false;
		uiHelper.onPause();
	}
	
	@Override
	protected void onDestroy()
	{
		Log.i(TAG, "onDestroy");
		
		super.onDestroy();
		uiHelper.onDestroy();
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState)
	{
		Log.i(TAG, "onSaveInstanceState");
		
		super.onSaveInstanceState(outState);
		uiHelper.onSaveInstanceState(outState);
	}
	
	public void printHashKey() 
	{
        try
        {
        PackageInfo info = getPackageManager().getPackageInfo("siva.arlimi.activity",
                PackageManager.GET_SIGNATURES);
        for (Signature signature : info.signatures) 
        {
            MessageDigest md = MessageDigest.getInstance("SHA");
            md.update(signature.toByteArray());
            Log.d(TAG, Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        }
        catch (NameNotFoundException e) 
        {
        } 
        catch (NoSuchAlgorithmException e) 
        {
        }

    }	
}
