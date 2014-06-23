package siva.arlimi.owner;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;

import siva.arlimi.activity.R;
import siva.arlimi.networktask.NetworkTask;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class InputMenuActivty extends Activity implements OnClickListener
{
	private Button mRegisterMenuButton;
	private EditText mMenuContents;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_owner_inputmenu);

		mRegisterMenuButton = (Button) findViewById(R.id.btn_input_menu);
		mRegisterMenuButton.setOnClickListener(this);
		
		mMenuContents = (EditText)findViewById(R.id.edittext_menu);

	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.btn_input_menu:
			RegisterMenuTask registerMenuTask = new RegisterMenuTask();
			registerMenuTask.execute(getMenuContents());
			break;

		default:
			break;

		}

	}
	
	public String[] getMenuContents()
	{
		String[] params = new String[2];
		
		params[0] = RegisterMenuTask.REGISTERMENUTASK_URL;
		params[1] = mMenuContents.getText().toString();
	
		return params;
	}
	
	class RegisterMenuTask extends NetworkTask
	{
		private final static String LOG = "RegisterMenuTask";
		public final static String REGISTERMENUTASK_URL = 
				"http://192.168.0.21:8088/SIVA_Arlimi_Test_Server/owner/registermenu"; 

		@Override
		public void sendData(HttpURLConnection conn, String[] params)
				throws UnsupportedEncodingException, IOException
		{
			String contents = params[1];
			
			String data = URLEncoder.encode("Contents", "UTF_8") + "=" +
							URLEncoder.encode(contents, "UTF-8");
		
			writeData(conn, data);			
		}
		
		@Override
		protected void onPostExecute(String result)
		{
			super.onPostExecute(result);
			Log.i(LOG, result);
		}
		
	}

}
