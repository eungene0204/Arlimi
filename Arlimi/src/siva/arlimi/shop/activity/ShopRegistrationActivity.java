package siva.arlimi.shop.activity;

import org.json.JSONException;
import org.json.JSONObject;

import siva.arlimi.location.util.LocationUtil;
import siva.arlimi.main.R;
import siva.arlimi.shop.fragment.ShowAddressDialogFragment;
import siva.arlimi.shop.fragment.ShowAddressDialogFragment.SearchShopAddressListener;
import siva.arlimi.shop.progress.AddressSearchProgressBar;
import siva.arlimi.shop.service.ShopRegistrationService;
import siva.arlimi.shop.util.ShopUtils;
import android.app.AlertDialog;
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

public class ShopRegistrationActivity extends FragmentActivity 
		implements OnClickListener, SearchShopAddressListener
{
	static public final String TAG = "ShopRegistrationActivity";
	
	
	private Button mFindAddressBtn;
	
	private Button mRegistrationBtn;
	
	private EditText mShopAddressEditText;
	private EditText mShopSearchAddresEdiText;
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
	
	private BroadcastReceiver mAddrResultReceiver = new BroadcastReceiver()
	{
		
		@Override
		public void onReceive(Context context, Intent intent)
		{
			onReceiveAddrResult(context,intent);
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
		
		
		mShopNameEditText = (EditText) findViewById(R.id.shop_name_et);
		mShopAddressEditText = (EditText) findViewById(R.id.shop_address_et);
		mShopPhoneNumberEditText = (EditText) findViewById(R.id.shop_phone_number_et);
		mShopSearchAddresEdiText = (EditText) findViewById(R.id.shop_dong_address_et);
		
	}
	
	protected void onReceiveAddrResult(Context context, Intent intent)
	{
		String result =
				intent.getStringExtra(ShopUtils.KEY_ADDRESS_SEARCH_RESULT);
		
		showAddrDialog(result);
	}

	private void showAddrDialog(String result)
	{
		//epost server does not respond
		if(result.equals("null"))
		{
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(getResources().getString(R.string.notification));
			builder.setMessage("주소서버 연결이 원활하지 않습니다.\n " +
					"잠시 후 다시 시도해 주십시요.");
			builder.setPositiveButton(getResources().getString(R.string.confirm),
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
		
		ShowAddressDialogFragment dialog = new ShowAddressDialogFragment(result);
		
		Bundle bundle = new Bundle();
		bundle.putString(ShopUtils.KEY_ADDRESS_SEARCH_RESULT,
				result);
		dialog.setArguments(bundle);
		dialog.show(getSupportFragmentManager(), null); 
		
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
		
		IntentFilter geoCodinFilter = new IntentFilter(LocationUtil.ACTION_REVERSE_GEOCODING);
		registerReceiver(mReciever, geoCodinFilter);
		
		IntentFilter addrResult = new IntentFilter(ShopUtils.ACTION_SEARCH_ADDRESS_RESULT);
		registerReceiver(mAddrResultReceiver, addrResult);
		
	}
	
	@Override
	protected void onPause()
	{
		super.onPause();
		unregisterReceiver(mReciever);
		unregisterReceiver(mAddrResultReceiver);
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
		String dong = mShopSearchAddresEdiText.getText().toString();
		
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
		Intent intent = new Intent(this, ShopRegistrationService.class);
		
		String name = mShopNameEditText.getText().toString(); 
		String address = mShopAddressEditText.getText().toString();
		String phone = mShopPhoneNumberEditText.getText().toString();
	}

	@Override
	public void onSearchShopAddress(String result)
	{
		
	}


}
