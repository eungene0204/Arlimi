package siva.arlimi.auth.activity;

import siva.arlimi.auth.session.SessionManager;
import siva.arlimi.facebook.FaceBookManager;
import siva.arlimi.main.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class LoggedInActivity extends Activity implements OnClickListener
{
	private Button mButton;
	
	private SessionManager mSession;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_logged_in);
		
		mButton = (Button) findViewById(R.id.log_out_btn);
		mButton.setOnClickListener(this);
		
		mSession = new SessionManager(getApplicationContext());
		
	}

	@Override
	public void onClick(View v)
	{
		switch(v.getId())
		{
		
		case R.id.log_out_btn:
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
