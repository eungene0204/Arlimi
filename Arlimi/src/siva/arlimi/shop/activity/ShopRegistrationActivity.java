package siva.arlimi.shop.activity;

import java.util.concurrent.ExecutionException;

import org.json.JSONException;
import org.json.JSONObject;

import siva.arlimi.location.ArlimiLocationClient;
import siva.arlimi.location.util.LocationUtil;
import siva.arlimi.main.R;
import siva.arlimi.shop.connection.SearchAddressConnection;
import siva.arlimi.shop.service.ShopRegistrationService;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;

public class ShopRegistrationActivity extends Activity implements OnClickListener
{
	static public final String TAG = "ShopRegistrationActivity";
	
	private ArlimiLocationClient mLocationClient;
	
	private Button mFindButton;
	private Button mFindAddressBtn;
	
	private Button mRegistrationBtn;
	
	private EditText mShopAddressEditText;
	private EditText mShopNameEditText;
	private EditText mShopPhoneNumberEditText;
	
	private BroadcastReceiver mReciever  = new BroadcastReceiver()
	{
		
		@Override
		public void onReceive(Context context, Intent intent)
		{
			onReceiveResult(context, intent);
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shop_registration);
	
		mFindAddressBtn = (Button)findViewById(R.id.shop_search_address_btn);
		mFindAddressBtn.setOnClickListener(this);
		
		mRegistrationBtn = (Button)findViewById(R.id.shop_registration_btn);
		mRegistrationBtn.setOnClickListener(this);
		
		
		mShopNameEditText = (EditText) findViewById(R.id.shop_name);
		mShopAddressEditText = (EditText) findViewById(R.id.shop_address);
		mShopPhoneNumberEditText = (EditText) findViewById(R.id.shop_phone_number);
		
	}
	
	protected void onReceiveResult(Context context, Intent intent)
	{
		Log.i(TAG, "onReceive");
		
		String result = intent.getStringExtra(LocationUtil.RESULT_REVERSE_GEOCODING);
	
		if(null == result)
		{
			Log.e(TAG, "Wrong Result");
			return;
		}
		
		try
		{
			JSONObject json = new JSONObject(result);
			if(! json.getString("status").equals("OK"))
			{
				Log.e(TAG, "Could not receive proper result");
			}
			
			JSONObject res = json.getJSONArray("results").getJSONObject(0);
			String address = res.getString(LocationUtil.JSON_FORMATTED_ADDRESS);
			Log.d(TAG, res.getString("formatted_address"));
			
			onShowDialog(address);
			
			
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
		
		
	}
	
	private void onShowDialog(String address)
	{
		String title = getResources().getString(R.string.search_address_title);
		
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
		alertDialog.setTitle(title);
		alertDialog.setMessage(address + "\n" +"\n" + "위의 주소로 등록 하시겠습니까?");
		alertDialog.setPositiveButton(getResources().getString(R.string.address_confirm),
				new DialogInterface.OnClickListener()
				{
					
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						mLocationClient.moveCamera(mLocationClient.getCurrentPosition());
					}
				});
		alertDialog.setNegativeButton(getResources().getString(R.string.manual_address),
				new DialogInterface.OnClickListener()
				{
					
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						// TODO Auto-generated method stub
					}
				});
		
		
		alertDialog.show();
		
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		
		IntentFilter filter = new IntentFilter(LocationUtil.ACTION_REVERSE_GEOCODING);
		registerReceiver(mReciever, filter);
	}
	
	@Override
	protected void onPause()
	{
		super.onPause();
		unregisterReceiver(mReciever);
	}

	@Override
	protected void onStart()
	{
		super.onStart();
		
	}
	
	@Override
	protected void onStop()
	{
		super.onStop();
		
	}

	@Override
	public void onClick(View v)
	{
		switch(v.getId())
		{
		
		case R.id.shop_search_address_btn:
			searchAddress();
			break;
		
		case R.id.shop_registration_btn:
			registerShop();
			break;
			
			default:
				break;
		}
		
	}

	private void searchAddress() 
	{
		
		/*
		String key =
				"fz+D92GHcx2tw1sxIdqMifIhApHVJoNcrzbD6NYOqGyf5aVdMRJ7jw+uDPup3jjK8ntUjWptZarC9Hpt353G6Q==";
		try
		{
			key = URLEncoder.encode(key, "UTF-8");
		} catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		
		String url =
				"http://openapi.epost.go.kr/postal/retrieveLotNumberAdressService/retrieveLotNumberAdressService/getEupMyunDongList"
		+ "?ServiceKey=";
		
		String params = "&emdCd=암사동";
		
		String addr = 
				"http://openapi.epost.go.kr/postal/retrieveLotNumberAdressService/retrieveLotNumberAdressService/getDetailList?ServiceKey=fz%2BD92GHcx2tw1sxIdqMifIhApHVJoNcrzbD6NYOqGyf5aVdMRJ7jw%2BuDPup3jjK8ntUjWptZarC9Hpt353G6Q%3D%3D&searchSe=dong&srchwrd=%EC%95%94%EC%82%AC%EB%8F%99";
	
		ShopSearchAddressConnection conn = new ShopSearchAddressConnection();
		conn.setURL(addr);
		conn.setMethod(NetworkConnection.Method.GET);
		
		Log.d(TAG, addr);
		
		
	
		String result = "null";
		try
		{
			result = conn.execute().get();
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} */
		
	
	}

	private void registerShop()
	{
		Intent intent = new Intent(this, ShopRegistrationService.class);
		
		String name = mShopNameEditText.getText().toString(); 
		String address = mShopAddressEditText.getText().toString();
		String phone = mShopPhoneNumberEditText.getText().toString();
	}

	private void findAddress()
	{
		Log.d(TAG, "findAddress");
		
		Location location = mLocationClient.getLastLocation();
		
		double latitude = location.getLatitude();
		double longitude = location.getLongitude();
		mLocationClient.setCurrentPosition(latitude, longitude);
		
		try
		{
			String query = LocationUtil.getQuery(this,latitude, longitude);
				
			Intent service = LocationUtil.getReverseGeocodingIntent(this);
			service.putExtra(LocationUtil.QUERY_REVERSE_GEOCODING, query.toString());
			startService(service);
			
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		} catch (ExecutionException e)
		{
			e.printStackTrace();
		}
	
	}

	
	

}
