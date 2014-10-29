package siva.arlimi.shop.activity;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.internal.mc;

import siva.arlimi.location.util.LocationUtil;
import siva.arlimi.main.R;
import siva.arlimi.shop.fragment.ShowAddressDialogFragment;
import siva.arlimi.shop.fragment.ShowAddressDialogFragment.onSelectAddressListener;
import siva.arlimi.shop.progress.AddressSearchProgressBar;
import siva.arlimi.shop.util.ShopUtils;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ShopRegistrationActivity extends FragmentActivity 
		implements OnClickListener,onSelectAddressListener 
{
	static public final String TAG = "ShopRegistrationActivity";
	
	
	private Button mFindAddressBtn;
	
	private Button mRegistrationBtn;
	
	private TextView mShopAddressTv;
	private TextView mShopZipTv;
	private EditText mShopDetailAddressEt;
	private EditText mShopSearchAddresEt;
	private EditText mShopNameEt;
	private EditText mShopPhoneNumberEt;
	
	private double mShopLatitude = 0.0;
	private double mShopLongitude = 0.0;
	
	private BroadcastReceiver mReciever  = new BroadcastReceiver()
	{
		@Override
		public void onReceive(Context context, Intent intent)
		{
			onReceiveResult(context, intent);
		}
	};
	
	private BroadcastReceiver mAddrResultReceiver = new BroadcastReceiver()
	{
		@Override
		public void onReceive(Context context, Intent intent)
		{
			onReceiveAddrResult(context,intent);
		}
	};
	
	private BroadcastReceiver mGeoCodingReceiver = new BroadcastReceiver()
	{
		
		@Override
		public void onReceive(Context context, Intent intent)
		{
			onReceiveGeoCoding(context, intent);
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
		
		mShopNameEt = (EditText) findViewById(R.id.shop_name_et);
		mShopAddressTv = (TextView) findViewById(R.id.shop_address_tv);
		mShopZipTv = (TextView) findViewById(R.id.shop_zip_tv);
		mShopDetailAddressEt = (EditText) findViewById(R.id.shop_detail_address_et);
		mShopPhoneNumberEt = (EditText) findViewById(R.id.shop_phone_number_et);
		mShopSearchAddresEt = (EditText) findViewById(R.id.shop_dong_address_et);

	}

	private void onReceiveGeoCoding(Context context, Intent intent)
	{
		String result = intent.getStringExtra(ShopUtils.KEY_GEOCODING);
		parseGeocodingResult(result);
		
	}

	private void parseGeocodingResult(String result)
	{
		try
		{
			JSONObject json = new JSONObject(result);
			
			//Check if result is ok
			if(!json.getString("status").equals("OK"))
			{
				//Show alert dialog
				return;
			}
			
			JSONObject res = json.getJSONArray("results").getJSONObject(0);
			JSONObject location = res.getJSONObject("geometry").getJSONObject("location");
			double lat = location.getDouble("lat");
			double lng = location.getDouble("lng");
			
			Log.d(TAG, "lat" + lat);
			Log.d(TAG, "lng" + lng);
			
			mShopLatitude = lat;
			mShopLongitude = lng;
			
		} catch (JSONException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	protected void onReceiveAddrResult(Context context, Intent intent)
	{
		String result =	intent.getStringExtra(ShopUtils.KEY_ADDRESS_SEARCH_RESULT);
		
		
		showAddrDialog(result);
	}

	private void showAddrDialog(String result)
	{
		//epost server does not respond
		if(result.equals("null"))
		{
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(getResources().getString(R.string.dialog_notification));
			builder.setMessage("주소서버 연결이 원활하지 않습니다.\n " +
					"잠시 후 다시 시도해 주십시요.");
			builder.setPositiveButton(getResources().getString(R.string.dialog_confirm),
					new DialogInterface.OnClickListener()
			{
				
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					// TODO Auto-generated method stub
				}
			});
			AlertDialog dialog = builder.create();
			dialog.show();
			
			return;
		}
		
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		Fragment prev = getSupportFragmentManager()
				.findFragmentByTag(ShopUtils.DIALOG_TAG_ADDRESS_RESULT);
		if( null != prev)
			ft.remove(prev);
		
		ft.addToBackStack(null);
		

		/*
		ShowAddressDialogFragment dialog = new ShowAddressDialogFragment(result);
		dialog.show(ft, ShopUtils.DIALOG_TAG_ADDRESS_RESULT); */
		
		ShowAddressDialogFragment dialog = new ShowAddressDialogFragment(this);
		
		Bundle bundle = new Bundle();
		bundle.putString(ShopUtils.KEY_ADDRESS_SEARCH_RESULT,
				result);
		dialog.setArguments(bundle);
		dialog.show(getSupportFragmentManager(), ShopUtils.DIALOG_TAG_ADDRESS_RESULT); 
		
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
		String title = getResources().getString(R.string.dialog_search_address_title);
		
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
		alertDialog.setTitle(title);
		alertDialog.setMessage(address + "\n" +"\n" + "위의 주소로 등록 하시겠습니까?");
		alertDialog.setPositiveButton(getResources().getString(R.string.dialog_address_confirm),
				new DialogInterface.OnClickListener()
				{
					
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
					}
				});
		alertDialog.setNegativeButton(getResources().getString(R.string.dialog_manual_address),
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
	
		//Need to remove
		IntentFilter reverseGeoCodinFilter = new IntentFilter(LocationUtil.ACTION_REVERSE_GEOCODING);
		registerReceiver(mReciever, reverseGeoCodinFilter);
		
		IntentFilter addrResultFilter = new IntentFilter(ShopUtils.ACTION_SEARCH_ADDRESS_RESULT);
		registerReceiver(mAddrResultReceiver, addrResultFilter);
		
		IntentFilter geocodingFilter = new IntentFilter(ShopUtils.ACTION_GEOCODING);
		registerReceiver(mGeoCodingReceiver, geocodingFilter);
		
	}
	
	@Override
	protected void onPause()
	{
		super.onPause();
		unregisterReceiver(mReciever);
		unregisterReceiver(mAddrResultReceiver);
		unregisterReceiver(mGeoCodingReceiver);
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
		String dong = mShopSearchAddresEt.getText().toString();
		
		if(dong.isEmpty())
		{
			//Show dialog to ask input
			return;
		}

		Intent intent = ShopUtils.getSearchAddressServiceIntent(this);
		intent.putExtra(ShopUtils.KEY_DONG, dong);
		
		View progressView = View.inflate(this, R.layout.progress_bar, null);

		ProgressBar bar = (ProgressBar) progressView.findViewById(R.id.progressBar);
		
		AddressSearchProgressBar progressBar =
				new AddressSearchProgressBar(this, intent, bar);
		progressBar.executeAddressSearchProgressTask();
	}

	private void registerShop()
	{
		boolean isValidInput = false;
		String empty = "";
		
		ArrayList<String> inputs = new ArrayList<String>();
		
		Intent intent = ShopUtils.getShopRegistrationIntent(this); 
		
		String name = mShopNameEt.getText().toString(); 
		String address = mShopAddressTv.getText().toString();
		String detailAddress = mShopDetailAddressEt.getText().toString();
		String phone = mShopPhoneNumberEt.getText().toString();
		String zip = mShopZipTv.getText().toString();
		
		if(name.isEmpty())
			empty = "이름";
		else if(address.isEmpty())
			empty = "주소";
		else if(detailAddress.isEmpty())
			empty = "상세주소";
		else if(phone.isEmpty())
			empty = "전화번호";
		else if(zip.isEmpty())
			empty = "우편번호";
		else
			isValidInput = true;
		
		if(!isValidInput)
		{
			showAlertDialog(empty);
			
			return;
		}
		
		intent.putExtra(ShopUtils.KEY_NAME, name);
		intent.putExtra(ShopUtils.KEY_ADDRESS, address);
		intent.putExtra(ShopUtils.KEY_DETAIL_ADDRESS, detailAddress);
		intent.putExtra(ShopUtils.KEY_PHONE, phone);
		intent.putExtra(ShopUtils.KEY_ZIP, zip);
	
		//Find latitude and longitude
		findLatLng(address, detailAddress);
		
		intent.putExtra(ShopUtils.KEY_LATITUDE, mShopLatitude);
		intent.putExtra(ShopUtils.KEY_LONGITUDE, mShopLongitude);
		
		//Send data to a database
		startService(intent);
		
	}
		

	private void showAlertDialog(String empty)
	{
		String message = empty + "를(을) 확인해주세요.";
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		
		
	}

	private boolean isValidInput(ArrayList<String> inputs)
	{
		for(String item : inputs)
		{
			if( null == item || item.isEmpty())
			{
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle(getResources().getString(R.string.shop_registration));
				
				
				return false;
			}
			
		}
		return true;
	}

	@Override
	public void onSelectAddress(Bundle address)
	{
		String addr = address.getString(ShopUtils.KEY_ADDRESS);
		String zip = address.getString(ShopUtils.KEY_ZIP);
	
		mShopAddressTv.setText(addr);
		mShopZipTv.setText(zip);
		
	
	}

	private void findLatLng(String addr, String detail)
	{
		Intent intent = ShopUtils.getGeoCodingServiceIntent(this);
		intent.putExtra(ShopUtils.KEY_ADDRESS, addr);
		intent.putExtra(ShopUtils.KEY_DETAIL_ADDRESS, detail);
		
		startService(intent);
		
	}
	
}