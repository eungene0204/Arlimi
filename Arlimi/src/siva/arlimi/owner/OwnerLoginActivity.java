package siva.arlimi.owner;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;

import org.json.JSONObject;

import siva.arlimi.activity.R;
import siva.arlimi.activity.R.id;
import siva.arlimi.activity.R.layout;
import siva.arlimi.networktask.NetworkTask;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class OwnerLoginActivity extends Activity implements View.OnClickListener
{
	private static final String LOG = "OwnerLoginActivity";


	ProgressDialog mProgress;
	String addr;
	String dbAddr;
	String jsonAddr;
	
	JSONObject mJsonObject;

	private Button mOwnerRegisterButton;
	private Button mLogin;
	
	private EditText mIdEditText;
	private EditText mPasswordEditText;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_owner_menu);
		
		//Buttons
		mOwnerRegisterButton = (Button)findViewById(R.id.btn_register);
		mLogin = (Button)findViewById(R.id.btn_login);
		mOwnerRegisterButton.setOnClickListener(this);
		mLogin.setOnClickListener(this);
		
		//EditText
		mIdEditText = (EditText)findViewById(R.id.edittext_id);
		mPasswordEditText = (EditText)findViewById(R.id.edittext_pass);
		
	}

	@Override
	public void onClick(View v)
	{
		// TODO Auto-generated method stub
		switch (v.getId())
		{
			case R.id.btn_register:
				Intent intent = new Intent(this, OwnerRegistrationActivity.class);
				startActivity(intent);
				break;
			case R.id.btn_login:
				LoginOwnerTask login = new LoginOwnerTask(this);
				login.execute(getParams());
			default:
				break;
		}
		
	}

	private String[] getParams()

	{
		String[] params = new String[3];
		
		String id = mIdEditText.getText().toString();
		String pass = mPasswordEditText.getText().toString();
		
		params[0] = LoginOwnerTask.LOGIN_OWNER;
		params[1] = id;
		params[2] = pass;
		
		Log.i(LOG, "params[1] id: "+ params[1].toString());
		Log.i(LOG, "params[2] pass: "+ params[2].toString());
		return params;
		
	}
		

	private class LoginOwnerTask extends NetworkTask
	{
		private static final String LOG = "LoginOwner";
		private static final String VALID_OWNER = "valid";

		public final static String LOGIN_OWNER =
				"http://192.168.0.21:8088/SIVA_Arlimi_Test_Server/owner/login/ValidOwner.jsp"; 
		
		private Activity ownerLoginActivity;

		public LoginOwnerTask()
		{
		}
		
		public LoginOwnerTask(OwnerLoginActivity ownerLoginActivity)
		{
			this.ownerLoginActivity = ownerLoginActivity;
			
		}

		@Override
		public void sendData(HttpURLConnection conn, String[] params) throws UnsupportedEncodingException, IOException
		{
			//Get Info from parameters
			String id = params[1];
			String pass = params[2];
			
			String data = URLEncoder.encode("ID", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");
			data += "&" + URLEncoder.encode("PASS", "UTF-8") + "=" + URLEncoder.encode(pass, "UTF-8");
			data += "&" + URLEncoder.encode("OWNERS", "UTF-8") + "=" + URLEncoder.encode("OWNERS", "UTF-8");

			writeData(conn, data);			
		}
		
		@Override
		protected void onPostExecute(String result)
		{
			super.onPostExecute(result);
		
			if(true)//isValidOwner(result))
			{	
				ownerLoginActivity.startActivity(
						new Intent(ownerLoginActivity, 
								OwnerManagementActivity.class));
			} 
		}
	
		public boolean isValidOwner(String result)
		{
			try
			{
				if(result.equals(VALID_OWNER + "\n"))
				{
					return true;
				}
			}
			catch(NullPointerException e)
			{
				Log.e(LOG, e.toString());
			}

			return false;
		}

	}

}
