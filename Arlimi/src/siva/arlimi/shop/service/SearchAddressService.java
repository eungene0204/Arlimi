package siva.arlimi.shop.service;

import siva.arlimi.shop.connection.SearchAddressConnection;
import siva.arlimi.shop.connection.SearchAddressConnection.OnSearchAddressListener;
import siva.arlimi.shop.util.ShopUtils;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class SearchAddressService extends Service implements OnSearchAddressListener
{
	
	@Override
	public IBinder onBind(Intent intent)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		super.onStartCommand(intent, flags, startId);
		
		String dong = intent.getStringExtra(ShopUtils.KEY_DONG);
		
		SearchAddressConnection conn = new SearchAddressConnection(this);
		conn.setDong(dong);
		conn.execute();
		
		return START_REDELIVER_INTENT;
	}

	@Override
	public void getResult(String result)
	{
		sendResult(result);
	}

	private void sendResult(String result)
	{
		final Intent intent = new Intent(ShopUtils.ACTION_SEARCH_ADDRESS_RESULT);
		intent.putExtra(ShopUtils.KEY_ADDRESS_SEARCH_RESULT, result);
		sendBroadcast(intent);
		
	}

}
