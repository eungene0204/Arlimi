package siva.arlimi.shop.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import siva.arlimi.shop.connection.GeoCodingConnection;
import siva.arlimi.shop.connection.GeoCodingConnection.OnGeoCodingListener;
import siva.arlimi.shop.util.ShopUtils;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class GeoCondingService extends Service implements OnGeoCodingListener
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
	
		String addr = intent.getStringExtra(ShopUtils.KEY_ADDRESS);
		String detail = intent.getStringExtra(ShopUtils.KEY_DETAIL_ADDRESS);

		String url = "https://maps.googleapis.com/maps/api/geocode/json?address=";
		try
		{
			addr = URLEncoder.encode(addr, "UTF-8");
			detail = URLEncoder.encode(detail, "UTF-8");
			
		} catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		url += addr + detail;
		
		GeoCodingConnection conn = new GeoCodingConnection(this);
		conn.setURL(url);
		conn.execute();
		
		return START_REDELIVER_INTENT;
	}

	@Override
	public void onGeoCoding(String result)
	{
		sendResult(result);
	}

	private void sendResult(String result)
	{
		Intent intent = new Intent(ShopUtils.ACTION_GEOCODING);
		intent.putExtra(ShopUtils.KEY_GEOCODING, result);
		sendBroadcast(intent);
	}

}
