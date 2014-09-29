package siva.arlimi.fragment;

import java.io.Serializable;


import siva.arlimi.activity.HomeActivity;
import siva.arlimi.activity.OpenActivity;
import siva.arlimi.activity.RegisterEventActivity;
import siva.arlimi.main.R;
import siva.arlimi.owner.Owner;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.ProfilePictureView;

public class MyPageFragment extends Fragment implements OnClickListener
{
	private static final String TAG = "MyPageFragment";

	private Button mEventRegisterBtn;
	private Button mUserLogOutBtn;
	private Button mBusinessRegistration;
	

	private Owner mPerson;
	//private FaceBookTask mFaceBookTask;
	
		
	private ProfilePictureView mProfilePicture;
	private TextView mUserNameView; 
	
	private Session.StatusCallback callback = new Session.StatusCallback()
	{
		@Override
		public void call(Session session, SessionState state, Exception exception)
		{
			onSessionStateChange(session,state,exception);
		}

	};
	
	private UiLifecycleHelper uiHelper;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		Log.i(TAG, "MypageFragment is onCreate");
		
			
		HomeActivity myActivity = (HomeActivity)getActivity();
		mPerson = myActivity.getPersonInfo();
	
	/*	
		mUserInfo = new FaceBookUserInfo();
		
		uiHelper = new UiLifecycleHelper(getActivity(), callback);
		uiHelper.onCreate(savedInstanceState); */
		
		//makeMeRequest(Session.getActiveSession());
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		
		Log.i(TAG, "onCreateView");
		View view = inflater.inflate(R.layout.fragment_mypage, container, false);
	
		mUserLogOutBtn = (Button) view.findViewById(R.id.user_logout_btn);
		mUserLogOutBtn.setOnClickListener(this);
		
		mEventRegisterBtn = (Button) 
				view.findViewById(R.id.event_register_btn);
		mEventRegisterBtn.setOnClickListener(this);
		
		mBusinessRegistration = (Button)
				view.findViewById(R.id.business_registration_btn);
		mBusinessRegistration.setOnClickListener(this);
		
		
		
		mProfilePicture = (ProfilePictureView)view.findViewById(R.id.facebook_profile_pic);
		mProfilePicture.setCropped(true);
		mProfilePicture.setProfileId(mPerson.getmId()); 
		
		mUserNameView = (TextView) view.findViewById(R.id.facebook_user_name);
		mUserNameView.setText(mPerson.getName());
		
		return view;
	}

	@Override
	public void onClick(View v)
	{
		switch(v.getId())
		{
		
		case R.id.business_registration_btn:
			showBusinessRegistrationActivity();
			break;
			
		case R.id.event_register_btn:
			showEventRegistratoinActivity();
			break;
			
		case R.id.user_logout_btn:
			//mFaceBookTask.logOutFacebook();
			logOut(); 
			
			default:
				break;
		}
	}
	

	private void showBusinessRegistrationActivity()
	{
		/*
		Intent intent = new Intent(getActivity(), BusinessRegistration.class);
		intent.putExtra(Owner.TAG, (Serializable)mPerson);
		
		startActivity(intent); */
	}

	private void showEventRegistratoinActivity()
	{
		Intent intent = new Intent(getActivity(), RegisterEventActivity.class);
		intent.putExtra(Owner.TAG,(Serializable)mPerson);
		startActivity(intent);
	}

	private void logOut()
	{
		Log.i(TAG, "log out");
		Session session = Session.getActiveSession();
	
		if(session != null)
		{
			if(!session.isClosed())
			{
				session.close();
				session.closeAndClearTokenInformation();
			}
		}
		else
		{
			session = new Session(getActivity());
			Session.setActiveSession(session);
			
			session.closeAndClearTokenInformation();
		}
		
		showLoginActivity();
		
		
	}
	
     private void onSessionStateChange(Session session, SessionState state,
                        Exception exception)
     {
    	 if(state.isOpened())
    	 {
    		 
    	 }
    	 else if(state.isClosed())
    	 {
    		 
    	 }
     }

	
	private void showLoginActivity()
	{
		
		Intent intent = new Intent(getActivity(), OpenActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
				Intent.FLAG_ACTIVITY_NEW_TASK);
		
		startActivity(intent);
	}
	
	@Override
	public void onAttach(Activity activity)
	{
		Log.i(TAG, "onAttach");
		super.onAttach(activity);
	}
	
	
	@Override
	public void onResume()
	{
		super.onResume();
		//mFaceBookTask.onResume();
		//uiHelper.onResume();
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);
		//mFaceBookTask.onSaveInstanceState(outState);
	}
	
	@Override
	public void onPause()
	{
		super.onPause();
		//mFaceBookTask.onPause();
	}
	
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		//mFaceBookTask.onDestroy();
	} 
	
}
