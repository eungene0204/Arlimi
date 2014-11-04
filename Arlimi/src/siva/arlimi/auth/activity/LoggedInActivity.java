package siva.arlimi.auth.activity;

import siva.arlimi.auth.session.SessionManager;
import siva.arlimi.facebook.FaceBookManager;
import siva.arlimi.main.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class LoggedInActivity extends Activity implements OnClickListener
{
	private Button mLogOutButton;
	private Button mShopRegButton;
	private TextView mUserNameTv;
	
	private SessionManager mSession;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_logged_in);
		
		mShopRegButton = (Button)findViewById(R.id.loggedin_shop_reg_btn);
		mShopRegButton.setOnClickListener(this);
		
		mLogOutButton = (Button) findViewById(R.id.loggedin_log_out_btn);
		mLogOutButton.setOnClickListener(this);
		
		mUserNameTv = (TextView) findViewById(R.id.logggedin_name_tv);
		
		mSession = new SessionManager(getApplicationContext());
		
		if(mSession.isLoggedIn())
			mUserNameTv.setText(mSession.getUserDetails().getName());
			
	}

	@Override
	public void onClick(View v)
	{
		switch(v.getId())
		{
		
		case R.id.loggedin_log_out_btn:
			logout();
			break;
			
			default:
				break;
		}
		
		
	}

	private void logout()
	{
		FaceBookManager fm = new FaceBookManager(this,null);
		fm.closeAndClearTokenInformation();
		mSession.logoutUser();
	}

}
