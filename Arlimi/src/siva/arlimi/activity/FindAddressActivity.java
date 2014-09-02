package siva.arlimi.activity;

import java.io.IOException;

import siva.arlimi.main.R;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
//import siva.arlimi.networktask.SearchAddress;


public class FindAddressActivity extends Activity implements View.OnClickListener
{
	private final String TAG = "FindAddressActivity";
	

	private EditText mAddressEditText;
	//SearchAddress searchAddress;
	
	private TextView mCurrentAddressTextView;

	Button mSearchAddrButton;

	double latitude = 0.0;
	double longitude = 0.0;
	

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_owner_find_address);
		
		Log.i(TAG, "onCreate");

		mSearchAddrButton = (Button) findViewById(R.id.btn_search_address);
		mSearchAddrButton.setOnClickListener(this);
		mAddressEditText = (EditText) findViewById(R.id.edittext_search_address);
		
		mCurrentAddressTextView = (TextView)findViewById(R.id.textview_current_address);

	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{

		case R.id.btn_search_address:
			//searchAddress = new SearchAddress(this);
			//searchAddress.execute(getAddress());
			
			break;

		default:
			break;
		}
	}

	public String[] getAddress() throws IOException
	{
		String[] address = new String[1];
		String addr = mAddressEditText.getText().toString();

//		address[0] = SearchAddress.getGoogleURL(addr);
		return address;

	}

}
