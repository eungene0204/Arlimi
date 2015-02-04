package siva.arlimi.service.service;

import siva.arlimi.networktask.NetworkURL;
import siva.arlimi.service.connection.AllServiceListConn;
import siva.arlimi.service.connection.AllServiceListConn.OnAllServiceListListener;
import siva.arlimi.service.util.ServiceUtil;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class AllServiceListService extends Service implements OnAllServiceListListener
{
	static public final String TAG = "AllServiceListService";
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
		
		String url = NetworkURL.SERVICE_READ_ALL_LIST;
		
		AllServiceListConn conn = new AllServiceListConn(this);
		conn.setURL(url);
		conn.setListener(this);
		conn.execute();
		
		return START_REDELIVER_INTENT;
	}

	@Override
	public void onAllServiceList(final String result)
	{
		Log.i(TAG, "Result" + result);
		sendResult(result);
		
	}

	private void sendResult(final String result)
	{
		final Intent intent = new Intent(ServiceUtil.ACTION_ALL_SERVICE_LIST);
		intent.putExtra(ServiceUtil.KEY_ALL_SERVICE_LIST, result);
		
		sendBroadcast(intent);
		
	}

}
