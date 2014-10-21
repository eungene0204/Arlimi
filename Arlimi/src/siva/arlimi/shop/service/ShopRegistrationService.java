package siva.arlimi.shop.service;

import org.json.JSONException;
import org.json.JSONObject;

import siva.arlimi.networktask.NetworkURL;
import siva.arlimi.shop.connection.ShopRegistrationConnection;
import siva.arlimi.shop.util.ShopUtils;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class ShopRegistrationService extends Service
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
		
		String name = intent.getStringExtra(ShopUtils.KEY_NAME); 
		String address = intent.getStringExtra(ShopUtils.KEY_ADDRESS); 
		String detailAddress = intent.getStringExtra(ShopUtils.KEY_DETAIL_ADDRESS);
		String phone = intent.getStringExtra(ShopUtils.KEY_PHONE);	
		String zip = intent.getStringExtra(ShopUtils.KEY_ZIP);
		
		JSONObject json = new JSONObject();
		try
		{
			json.put(ShopUtils.KEY_NAME, name);
			json.put(ShopUtils.KEY_ADDRESS, address);
			json.put(ShopUtils.KEY_DETAIL_ADDRESS, detailAddress);
			json.put(ShopUtils.KEY_PHONE,phone);
			json.put(ShopUtils.KEY_ZIP, zip);
			
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

}
