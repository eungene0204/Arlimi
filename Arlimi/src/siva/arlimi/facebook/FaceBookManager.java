package siva.arlimi.facebook;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import siva.arlimi.auth.interfaces.OnLoginResultListener;
import siva.arlimi.auth.interfaces.OnRegisterNewUserListener;
import siva.arlimi.auth.interfaces.OnUserLoginListener;
import siva.arlimi.auth.session.SessionManager;
import siva.arlimi.main.MainActivity;
import siva.arlimi.user.FacebookUser;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.Session.StatusCallback;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.ProfilePictureView;

public class FaceBookManager
{
	public static final String TAG = "FaceBookManager";
	
	private final Activity mContext;
	private final UiLifecycleHelper uiHelper;
	private final FacebookUser mFacebookUser;
	
	private OnRegisterNewUserListener mNewUserCallback;
	private OnUserLoginListener mLoginUserCallback;
	
	private boolean isNew = false;
	
	private final SessionManager mSession;
	
	private Session.StatusCallback callback = new StatusCallback()
	{
		@Override
		public void call(Session session, SessionState state, Exception exception)
		{
			onSessionStateChange(session,state,exception);
		}
	};
	
	
	public FaceBookManager(Activity context, Bundle savedInstanceState)
	{
		//LoginActivity
		mContext = context;
		
		mSession = new SessionManager(mContext.getApplicationContext());
		
		uiHelper = new UiLifecycleHelper((Activity) context, callback);
		uiHelper.onCreate(savedInstanceState);
		
		mFacebookUser = new FacebookUser(mContext); 
	
	}
	
	public boolean isNew()
	{
		return isNew;
	}
	
	public void setRegistrationListener(OnRegisterNewUserListener listener)
	{
		this.mNewUserCallback = listener;
	}
	
	public void setLoginResultListener(OnUserLoginListener listener)
	{
		mLoginUserCallback = listener;
	}
	
	
	public void setIsNew(boolean isUser)
	{
		this.isNew = isUser;
	}
	
	public FacebookUser getFacebookUser()
	{
		return (null == mFacebookUser) ? new FacebookUser(mContext)
		: mFacebookUser;
	}
	
	private void onSessionStateChange(Session session, SessionState state,
			Exception exception)
	{
		// TODO Auto-generated method stub
		if(session != null)
		{
			Log.d(TAG, session.toString());
			if(session.isOpened())
			{
				makeMeRequest(session);
			}
			else if(session.isClosed())
			{
				logout();
			}
		}
	}

	private void logout()
	{
		Log.d(TAG, "LogOut");
		mSession.logoutUser();
		
		closeAndClearTokenInformation();
		
		Intent i = new Intent(mContext.getApplicationContext(), MainActivity.class);

		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		mContext.startActivity(i);
	}
	
	public void closeAndClearTokenInformation()
	{
		Session session = Session.getActiveSession();
		
		if(null != null)
		{
			if(!session.isClosed())
			{
				session.closeAndClearTokenInformation();
			}
			
		}
		else
		{
			session = new Session(mContext);
			Session.setActiveSession(session);
			
			session.closeAndClearTokenInformation();
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
						Log.i(TAG, "facebook onCompleted");
						// If the response is successful
						if (session == Session.getActiveSession())
						{
							if (user != null)
							{
								String name = user.getName();
								String email = user.asMap().get("email").toString();
								ProfilePictureView profilePic = new ProfilePictureView(mContext);
								profilePic.setProfileId(user.getId());
								
								mFacebookUser.setName(name);
								mFacebookUser.setEmail(email);
								mFacebookUser.setProfilePicture(profilePic);
								mFacebookUser.setValid(true);
						
								//Check if user is new 
								callListener(isNew);
							
							
								Log.i(TAG, "User name " + name);
								Log.i(TAG, "email" + email);
								
							}
						}
						if (response.getError() != null)
						{
							// Handle error
						}
					}

					private void callListener(boolean isNew)
					{
						if (isNew)
						{
							Log.i(TAG, "call reg callback");
							mNewUserCallback.registerNewFacebookUser(mFacebookUser);
						} else
						{
							Log.i(TAG, "call login callback");
							mLoginUserCallback.facebookUserLogin(mFacebookUser);
						}

					}
				});

		request.executeAsync();
		
	}
	
	public void onResume()
	{
		 uiHelper.onResume();
		 Session session = Session.getActiveSession();
		 
		 if(session != null)
		 {
			 if(session.isOpened())
				 Log.i(TAG, "onResume session is opened");
			 else
				 Log.i(TAG, session.toString());
		 }
		 else
		 {
			 Log.i(TAG, "session is null");
		 }
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		uiHelper.onActivityResult(requestCode, resultCode, data);
	}
	
	public void onPause()
	{
		uiHelper.onPause();
	}
	
	public void onDestroy()
	{
		uiHelper.onDestroy();
	}
	
	public void onSaveInstanceState(Bundle outState)
	{
		uiHelper.onSaveInstanceState(outState);
	}
	
	public FacebookUser getUserInfo()
	{
		return mFacebookUser;
	}
		
	public void printHashKey() 
	{
        try
        {
        PackageInfo info = mContext.getPackageManager().getPackageInfo("siva.arlimi.main",
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
