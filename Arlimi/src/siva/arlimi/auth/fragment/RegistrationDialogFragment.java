package siva.arlimi.auth.fragment;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import siva.arlimi.auth.activity.LoginActivity;
import siva.arlimi.auth.interfaces.OnRegisterNewUserListener;
import siva.arlimi.auth.session.SessionManager;
import siva.arlimi.auth.util.AuthUtil;
import siva.arlimi.facebook.FaceBookManager;
import siva.arlimi.main.MainActivity;
import siva.arlimi.main.R;
import siva.arlimi.user.EmailUser;
import siva.arlimi.user.FacebookUser;
import siva.arlimi.user.User;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
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
import com.google.android.gms.plus.model.people.Person;

public class RegistrationDialogFragment extends DialogFragment implements OnClickListener,
							OnRegisterNewUserListener
{
	public static final String TAG = "RegistrationDialogFragment";
	
	private FaceBookManager mFaceBook;
	private Button mRegistrationBtn;
	private EditText mEmailEditText;
	private EditText mPasswrodEditText;
	private EditText mUserName;
	private SessionManager mSession;
	
	private enum Type {facebook, email};

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
	
	
	protected void onReceiveRegistrationResult(final Intent intent)
	{
		final String result = intent.getStringExtra(AuthUtil.KEY_REGISTRATION_RESULT);
		Type type = null;
		User user;
		
		Log.i(TAG,"Facebook Registration result " + result);
	
		if(result.trim().equals(AuthUtil.VALID_USER))
		{
			if (mFaceBook.isOpended())
			{
				type = Type.facebook;
				user = (User) mFaceBook.getFacebookUser();
			}
			else
			{
				type = Type.email;
				user = (User) getEmailUserInfo();
			}

			makeSession(user);
			showMainActivity();
			
		} else if (result.trim().equals(AuthUtil.DUPLICATE_USER))
		{
			CharSequence message = "이미 등록된 이메일 주소입니다";
			
			if(mFaceBook.isOpended())
				type = Type.facebook;
			else
				type = Type.email;
			
			showRegistrationDialog(message, type);
			
		}
		else  // Invalid User
		{
			CharSequence message = "등록에 실패하였습니다.";
			
			if (mFaceBook.isOpended())
				type = Type.facebook;
			else
				type = Type.email;

			showRegistrationDialog(message, type);

		}
		
	}
	
	private void showRegistrationDialog(final CharSequence message, final Type type)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		builder.setTitle(getResources().getString(R.string.dialog_registration));
		builder.setMessage(message);
		builder.setPositiveButton(
				getResources().getString(R.string.dialog_confirm),
				new DialogInterface.OnClickListener()
				{

					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						switch(type)
						{
						
						case facebook:
							mFaceBook.closeAndClearTokenInformation();
							break;
							
						case email:
							break;
							
							default:
								break;
						}

					}
				});

		AlertDialog alert = builder.create();
		alert.show();

	}

	private void showLoginActivity()
	{
		Intent intent = new Intent(getActivity(), LoginActivity.class);

		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		startActivity(intent);
	}

	public static boolean isEmailValid(String email)
	{
		boolean isValid = false;

		String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
		CharSequence inputStr = email;

		Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
	Matcher matcher = pattern.matcher(inputStr);
		if (matcher.matches())
		{
			isValid = true;
		}
		return isValid;
	}

	private void makeSession(User user)
	{
		mSession = new SessionManager(getActivity().getApplicationContext());
		
		mSession.createLoginSession(user.getEmail(), user.getName());
	}


	private void showMainActivity()
	{
		Intent intent = new Intent(getActivity(), MainActivity.class);
		
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		
		startActivity(intent);
		
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
			EmailUser user = getEmailUserInfo();
			registerNewEmailUser(user);
			break;
			
			default:
				break;
		
		}
		
	}

	private EmailUser getEmailUserInfo()
	{
		String email = mEmailEditText.getText().toString();
		String password = mPasswrodEditText.getText().toString();
		String name = mUserName.getText().toString();
	
		EmailUser user = new EmailUser(name, email, password);
		
		return user;
	
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
		String email = user.getEmail();
		
		if(!isEmailValid(email))
		{
			String message = "잘못된 이메일 입니다.";
			showRegistrationDialog(message, Type.email);
			
			return;
		}
			
			
		Intent intent = AuthUtil.getNewEmailUserIntent(getActivity()); 
		intent.putExtra(AuthUtil.KEY_USER, user);
		
		getActivity().startService(intent);
		
	}
	
}
