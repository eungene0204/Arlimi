package siva.arlimi.event.service;

import org.json.JSONObject;

import siva.arlimi.event.connection.EventRegistrationConnection;
import siva.arlimi.event.connection.EventRegistrationConnection.OnEventRegistrationListener;
import siva.arlimi.event.util.EventUtils;
import siva.arlimi.json.JSONHelper;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class EventRegistrationService extends Service implements OnEventRegistrationListener
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
		
		EventRegistrationConnection conn = new EventRegistrationConnection(this);
		JSONObject json = JSONHelper.getJSONObjectFromIntent(intent);
		
		return START_REDELIVER_INTENT;
	}

	@Override
	public void onEventRegistration(final String result)
	{
		sendResult(result);
	}

	private void sendResult(final String result)
	{
		final Intent intent = new Intent(EventUtils.ACTION_REG_RESULT);
		intent.putExtra(EventUtils.KEY_REG_RESULT, result);
		
		sendBroadcast(intent);
		
	}

}
