package siva.arlimi.auth.fragment;

import siva.arlimi.auth.interfaces.OnRegisterNewUserListener;
import siva.arlimi.auth.util.AuthUtil;
import siva.arlimi.facebook.FaceBookManager;
import siva.arlimi.main.R;
import siva.arlimi.user.EmailUser;
import siva.arlimi.user.FacebookUser;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.facebook.widget.LoginButton;

public class RegistrationDialogFragment extends DialogFragment implements OnClickListener,
							OnRegisterNewUserListener
{
	public static final String TAG = "RegistrationDialogFragment";
	
	private FaceBookManager mFaceBook;
	private Button mRegistrationBtn;
	private EditText mEmailEditText;
	private EditText mPasswrodEditText;
	private EditText mUserName;
	
	private BroadcastReceiver mBroadCastReceiver = new BroadcastReceiver()
	{
		@Override
		public void onReceive(Context context, Intent intent)
		{
			onReceiveRegistrationResult(intent);
			
		}
	};
	
	public RegistrationDialogFragment(FaceBookManager facebook)
	{
		this.mFaceBook = facebook;
		mFaceBook.setIsNew(true);
		mFaceBook.setRegistrationListener(this);
	}
	
	
	protected void onReceiveRegistrationResult(Intent intent)
	{
		final int result = intent.getIntExtra
				(AuthUtil.KEY_LOGIN_RESULT, AuthUtil.RESULT_INVALID_USER);
		
		Log.i(TAG,"Facebook Registration result " + result);
		
		if(AuthUtil.RESULT_VALID_USER == result)
			dismiss();
		
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View root = inflater.inflate(R.layout.fragment_user_registration
				,container, false);
		
		getDialog().setTitle(
				getResources().getString(R.string.new_registration));
				
		//Remove Divider of title
		int dividerID = getDialog().getContext().getResources()
						.getIdentifier("android:id/titleDivider",
								null, null);
		View divider = getDialog().findViewById(dividerID);
		divider.setBackgroundColor(
				getResources().getColor(R.color.white));
		
		LoginButton authButton = (LoginButton) 
				root.findViewById(R.id.dialog_fragment_facebook_reg_btn); 
		authButton.setFragment(this);
	
		mRegistrationBtn = (Button)root.findViewById(R.id.new_user_btn);
		mRegistrationBtn.setOnClickListener(this);
		mEmailEditText = (EditText)root.findViewById(R.id.new_user_email_et);
		mPasswrodEditText = (EditText)root.findViewById(R.id.new_user_password_et);
		mUserName = (EditText)root.findViewById(R.id.new_user_name_et);
		
		//mFaceBook = new FaceBookManager(getActivity(), savedInstanceState);
		
		
		return root;
	}
	
	@Override
	public void onResume()
	{
		Log.i(TAG, "onResume");
		super.onResume();
		mFaceBook.onResume();
		
		IntentFilter filter = new IntentFilter(AuthUtil.ACTION_REGISTRATION_RESULT);
		getActivity().registerReceiver(mBroadCastReceiver, filter);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		Log.i(TAG, "onActivityResult");
		super.onActivityResult(requestCode, resultCode, data);
		mFaceBook.onActivityResult(requestCode, resultCode, data);
	}
	
	@Override
	public void onPause()
	{
		Log.i(TAG, "onPause");
		super.onPause();
		mFaceBook.onPause();
		
		getActivity().unregisterReceiver(mBroadCastReceiver);
	}
	
	@Override
	public void onDestroy()
	{
		Log.i(TAG, "onDestroy");
		super.onDestroy();
		mFaceBook.onDestroy();
	}
	
	@Override
	public void onSaveInstanceState(Bundle arg0)
	{
		Log.i(TAG, "onSaveInstanceState");
		super.onSaveInstanceState(arg0);
		mFaceBook.onSaveInstanceState(arg0);
	}
	
	@Override
	public void onAttach(Activity activity)
	{
		Log.i(TAG, "onAttach");
		super.onAttach(activity);
	
	}

	@Override
	public void onClick(View v)
	{
		switch(v.getId())
		{
		case R.id.new_user_btn:
			getUserInfo();
			break;
			
			default:
				break;
		
		}
		
	}

	private void getUserInfo()
	{
		String email = mEmailEditText.getText().toString();
		String password = mPasswrodEditText.getText().toString();
		String name = mUserName.getText().toString();
	
		EmailUser user = new EmailUser(name, email, password);
		
		registerNewEmailUser(user);
	
	}
	
	@Override
	public String toString()
	{
		return getClass().getName();
	}


	@Override
	public void registerNewFacebookUser(FacebookUser user)
	{
		Log.i(TAG, "registerNewfacebookUSer");
		
		if(user.isValid())
		{
			Intent intent = AuthUtil.getNewFaceBookUserIntent(getActivity());
			intent.putExtra(AuthUtil.KEY_USER, user);
			
			getActivity().startService(intent);
		}
		
	}

	@Override
	public void registerNewEmailUser(EmailUser user)
	{
		Intent intent = AuthUtil.getNewEmailUserIntent(getActivity()); 
		intent.putExtra(AuthUtil.KEY_USER, user);
		
		getActivity().startService(intent);
		
	}
	
}
