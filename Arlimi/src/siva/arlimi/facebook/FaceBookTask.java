package siva.arlimi.facebook;

import java.util.Arrays;

import siva.arlimi.activity.HomeActivity;
import siva.arlimi.activity.OpenActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.TextView;

import com.facebook.LoginActivity;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.facebook.widget.ProfilePictureView;

public class FaceBookTask
{
	private static final String TAG = "FaceBookTask";
	private static final int REAUTH_ACTIVITY_CODE = 100;
	
	private Fragment mRootFragment;
	private Activity mRootActivity;
	
	private LoginButton mLoginButton;
	private UiLifecycleHelper uiHelper;
	
	
	private ProfilePictureView mProfilePicture;
	private TextView mUserNameView; 
	private String mEmail;
	
	
	private Session.StatusCallback callback = new Session.StatusCallback()
	{
		@Override
		public void call(Session session, SessionState state, Exception exception)
		{
			onSessionStateChange(session, state, exception);
		}

	};
	
	private boolean isResumed = false;
		
	public FaceBookTask()
	{
	}	
	
	
	public FaceBookTask(Activity activity, Bundle savedInstanceState)
	{
		mRootActivity = activity;

		uiHelper = new UiLifecycleHelper(mRootActivity, callback);
		uiHelper.onCreate(savedInstanceState);
	}
	
	
	public void setProfilePic(ProfilePictureView pic)
	{
		mProfilePicture = pic;
	}
	
	public void setUserNameView(TextView userName)
	{
		mUserNameView = userName;
	}
	
	public void setLoginButton(Fragment fragment, LoginButton button)
	{
		mLoginButton = button; 
		setFragment(fragment);
		
		mLoginButton.setFragment(mRootFragment);
		mLoginButton.setReadPermissions(Arrays.asList("basic_info","email"));
		
	}
	
	public void setFragment(Fragment fragment)
	{
		this.mRootFragment = fragment;
	}

	
	public boolean isResume()
	{
		return this.isResumed;
	}
	
	private void onSessionStateChange(Session session, SessionState state, Exception exception)
	{
		Log.i(TAG, mRootActivity.toString() + " onSessionSteteChange");
	
		if(isResumed)
		{
			/*
			
                FragmentManager fm = mRootActivity.getSupportFragmentManager();
                int backStackSize = fm.getBackStackEntryCount();
                for(int i = 0; i < backStackSize; i++)
                {
                        fm.popBackStack();
                }  */
                
                if(state.isOpened())
                {
                        Log.i(TAG, mRootActivity.toString() + " SessionState OPENED ");
                        showHomeActivity();
                }
                else if(state.isClosed())
                {
                        Log.i(TAG, mRootActivity.toString() + " state.isClosed ");
                        showOpenActivity();
                }
		}

			/*
		    //if(session != null && session.isOpened())
			if(session == null)
				Log.i(TAG, mRootActivity.toString() + " seesion is null");
			
			if(state.isOpened())
			{
				Log.i(TAG, mRootActivity.toString() +  " on session state session is open");
				
				Intent intent = new Intent(mRootActivity, HomeActivity.class);
				mRootActivity.startActivity(intent);
				//makeMeRequest(session);
			}
			else if(state.isClosed())
			{
				Log.i(TAG, mRootActivity.toString() + " session is closed facbook log out");
				
				//Intent intent = new Intent(mRootActivity, OpenActivity.class);
				//mRootActivity.startActivity(intent);
				
			}
			else
			{
				Log.i(TAG, mRootActivity.toString() + " open nor closed");
				//Session.openActiveSession(mRootActivity, mRootFragment, true, callback);
				session.openForRead(new Session.OpenRequest(mRootActivity).setCallback(callback));
			} */
	
		
}	
	public void OnResumeFragments()
	{
		Log.i(TAG, mRootActivity.toString() + " onResumeFragments");
	/*	
		Session session = Session.getActiveSession();
        if(session != null && session.isOpened())
        {
                Log.i(TAG, mRootActivity.toString() + " resumefragement session is open");
                //showHomeActivity();
        }
        else if(session != null && session.isClosed())
        {
                Log.i(TAG, mRootActivity.toString() + " resumefragement session is closed");
                showOpenActivity();
        }
        else
        {
                        Log.i(TAG, mRootActivity.toString() + " resumefragement session is created");			
                session.openForRead(new Session.OpenRequest(mRootActivity).setCallback(callback));
        }  */
		
	//	uiHelper.onResume();
	}

	public void onResume()
	{
		Log.i(TAG, mRootActivity.toString() + "Resume");
		
		this.isResumed = true;
		uiHelper.onResume(); 
		
		Session session = Session.getActiveSession();
		
		Log.d(TAG, mRootActivity.toString() + " onResume sesseion" + session.toString());
		
		if(session != null && session.isOpened())
		{
			/*
	
			if( !session.isOpened() && !session.isClosed())
			{
				 Log.i(TAG, mRootActivity.toString() + " session is neither opened nor closed");
				 session.openForRead(new Session.OpenRequest(mRootActivity).setCallback(callback));
			}
			else
			{
				Log.i(TAG, mRootActivity.toString() + " openActiveSession");
				Session.openActiveSession(mRootActivity, mRootFragment, true, callback);	
			}*/
			
			showHomeActivity();
		}
		else
		{
			Log.i(TAG, "session is null or closed");
			showLoginActivity();
		} 
		
	}

	public void onPause()
	{
		Log.i(TAG, mRootActivity.toString() + " onPause");
		isResumed = false;
		uiHelper.onPause();
	}
	
	public void onStop()
	{
		Log.i(TAG, mRootActivity.toString() + " onStop");
		uiHelper.onStop();
	}
	
	public void onDestroy()
	{
		Log.i(TAG, mRootActivity.toString() + " onDestroy");
		uiHelper.onDestroy();
		
	}
	
	public void onSaveInstanceState(Bundle outState)
	{
		Log.i(TAG, mRootActivity.toString() + " onSaveInstanecState");
		uiHelper.onSaveInstanceState(outState);
		
	}
		
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		Log.i(TAG, mRootActivity.toString() + "activityResult");
		
		uiHelper.onActivityResult(requestCode, resultCode, data);
		
		if(requestCode == REAUTH_ACTIVITY_CODE)
		{	
			Log.i(TAG, "REAUTH CODE");
		}
	}
	
	
	public void setRootFragment(Fragment fragment)
	{
		mRootFragment = fragment;
	}

	private void makeMeRequest(final Session session)
	{
		Request request = Request.newMeRequest(session, new Request.GraphUserCallback()
		{
			@Override
			public void onCompleted(GraphUser user, Response response)
			{
				//If the response is successful
				if(session == Session.getActiveSession())
				{
					if( user != null)
					{
						mProfilePicture.setProfileId(user.getId());
						mUserNameView.setText(user.getName());
						mEmail = user.asMap().get("email").toString();
						Log.i(TAG, mEmail.toString());
					}
				}
				if(response.getError() != null)
				{
					//Handle error
				}
			}
		});
		
		request.executeAsync();
	}
	
	public void checkSession(Session session)
	{
		Log.i(TAG, "checkSession");
		
		if(session == null)
		{
			Log.i(TAG, mRootActivity.toString() + " session is null at checkSession");
			return;
		}
		
		if(session.isOpened())
		{
			Log.i(TAG, "check session is opened at checkSession");
	//		makeMeRequest(session);
		}
		else if(session.isClosed())
		{
			Log.i(TAG, "chekc session is closed");
		}
	}
	
	public void showFragment(boolean addToBackStack)
	{
		/*
		Log.i(TAG, mRootActivity.toString() + " showFragment");
        FragmentManager fm = mRootActivity.getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction(); 
                
        transaction.show(mRootFragment);
                
        transaction.commit(); */
		
        //transaction.commitAllowingStateLoss();
		
	}
	
	public void showOpenActivity()
	{
		Log.i(TAG, mRootActivity.toString() + " showOpenActivity");
		
		Intent intent = new Intent(mRootActivity, OpenActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		mRootActivity.startActivity(intent);
	}
	
	public void showLoginActivity()
	{
		Intent intent = new Intent(mRootActivity, LoginActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		mRootActivity.startActivity(intent);
	}
	
	public void showHomeActivity()
	{
		Log.i(TAG, mRootActivity.toString() + " showHomeActivity");
		
		Intent intent = new Intent(mRootActivity, HomeActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		mRootActivity.startActivity(intent);
	}
	
		
	public void logOutFacebook()
	{
		Session session = Session.getActiveSession();
		
		Log.d(TAG, mRootActivity.toString() + " logout session: " + session.toString());
		
		if(session != null  && !session.isClosed())
		{
			Log.i(TAG, this.toString() + "facebook log out");
			
			session.close();
			session.closeAndClearTokenInformation();
		}
		
		//Session.setActiveSession(null);
		
	}


}
