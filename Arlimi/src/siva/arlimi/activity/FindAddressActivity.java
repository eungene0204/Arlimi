package siva.arlimi.activity;

import java.io.IOException;

import java.util.List;
import java.util.Locale;

//import siva.arlimi.networktask.SearchAddress;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


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
