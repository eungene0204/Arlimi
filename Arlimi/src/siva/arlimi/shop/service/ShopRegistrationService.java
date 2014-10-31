package siva.arlimi.shop.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import siva.arlimi.auth.util.AuthUtil;
import siva.arlimi.networktask.NetworkURL;
import siva.arlimi.shop.connection.ShopRegistrationConnection;
import siva.arlimi.shop.connection.ShopRegistrationConnection.OnShopRegistrationListener;
import siva.arlimi.shop.util.ShopUtils;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class ShopRegistrationService extends Service implements OnShopRegistrationListener 
{

	@Override
	public IBinder onBind(Intent intent)
	{
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		super.onStartCommand(intent, flags, startId);
	
		String email = intent.getStringExtra(AuthUtil.KEY_EMAIL);
		String name = intent.getStringExtra(ShopUtils.KEY_NAME); 
		String address = intent.getStringExtra(ShopUtils.KEY_ADDRESS); 
		String detailAddress = intent.getStringExtra(ShopUtils.KEY_DETAIL_ADDRESS);
		String phone = intent.getStringExtra(ShopUtils.KEY_PHONE);	
		String zip = intent.getStringExtra(ShopUtils.KEY_ZIP);
		double lat = intent.getDoubleExtra(ShopUtils.KEY_LATITUDE, 0.0);
		double lng = intent.getDoubleExtra(ShopUtils.KEY_LONGITUDE, 0.0);
		
		try
		{
			name = URLEncoder.encode(name,"UTF-8"); 
			address = URLEncoder.encode(address, "UTF-8");
			detailAddress = URLEncoder.encode(detailAddress, "UTF-8");
			
			
		} catch (UnsupportedEncodingException e1)
		{
			e1.printStackTrace();
		}
		
		
		JSONObject json = new JSONObject();
		try
		{
			json.put(AuthUtil.KEY_EMAIL, email);
			json.put(ShopUtils.KEY_NAME, name);
			json.put(ShopUtils.KEY_ADDRESS, address);
			json.put(ShopUtils.KEY_DETAIL_ADDRESS, detailAddress);
			json.put(ShopUtils.KEY_PHONE,phone);
			json.put(ShopUtils.KEY_ZIP, zip);
			json.put(ShopUtils.KEY_LATITUDE, lat);
			json.put(ShopUtils.KEY_LONGITUDE, lng);
			
			ShopRegistrationConnection conn = new ShopRegistrationConnection(this);
			conn.setData(json);
			conn.setURL(NetworkURL.SHOP_REGISTRATION);
			conn.execute();
			
		} catch (JSONException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return START_REDELIVER_INTENT;
	}

	@Override
	public void onShopRegistrationResult(String result)
	{
		sendResult(result);
	}

	private void sendResult(String result)
	{
		Intent intent = new Intent(ShopUtils.ACTION_REGISTRATION_RESULT);
		intent.putExtra(ShopUtils.KEY_REGISTRATION_RESULT, result);
		
		sendBroadcast(intent);
	}

}
