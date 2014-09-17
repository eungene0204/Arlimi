package siva.arlimi.activity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import siva.arlimi.auth.activity.LoginActivity;
import siva.arlimi.main.R;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.util.Base64;
import android.util.Log;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;

public class OpenActivity extends FragmentActivity
{
	private static final String TAG = "OpenActivity";

	private UiLifecycleHelper uiHelper;
	
	private Session.StatusCallback callback = new Session.StatusCallback()
	{
		@Override
		public void call(Session session, SessionState state, Exception exception)
		{
			onSessionStateChange(session, state, exception);
		}
	};
	
	private boolean mIsResume = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		Log.i(TAG, this.toString() + " onCreate");
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_open);
		
		uiHelper = new UiLifecycleHelper(this, callback);
		uiHelper.onCreate(savedInstanceState);
		
		printHashKey();
	}
	
	public void showHomeActivity()
	{
		Intent intent = new Intent(this,HomeActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
				Intent.FLAG_ACTIVITY_CLEAR_TOP
				);
		startActivity(intent);
	}
	
	public void showLoginActivity()
	{
		Intent intent = new Intent(this,LoginActivity.class);
		startActivity(intent);
	}
	
	@Override
	public void onResume()
	{
		Log.i(TAG, "onReuse");
		super.onResume();
		
		mIsResume = true;
		uiHelper.onResume();

		Session session = Session.getActiveSession();
		if (session != null)
		{
			if (session.isOpened())
			{
				Log.i(TAG, "sesseion is open");
				showHomeActivity();
				
			}
			else
			{
				new Handler().postDelayed(new Runnable()
				{

					@Override
					public void run()
					{
						showLoginActivity();
					}
				}, 3000);

			}
		}
		else
		{
			Log.i(TAG, "session is null");
		}

	}
	
	@Override
	public void onPause()
	{
		super.onPause();
		
		mIsResume = false;
		uiHelper.onPause();
		
	}
	
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, requestCode, data);
		
		uiHelper.onActivityResult(requestCode, resultCode, data);
	}
	
	@Override
	protected void onStop()
	{
		super.onStop();
	}
	
	@Override
	protected void onDestroy()
	{
		super.onDestroy();
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);
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
	
	private void onSessionStateChange(Session session, SessionState state, Exception exception)
	{
		if(mIsResume)
		{
                if(state.isOpened())
                {
                        
                }
                else if(state.isClosed())
                {
                	Log.i(TAG, "state is closed");
                	showLoginActivity();
                }
		}
		
	}
}
