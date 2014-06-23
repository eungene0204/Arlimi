package siva.arlimi.location;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.widget.Toast;

public class EventReceiver extends BroadcastReceiver
{

	@Override
	public void onReceive(Context context, Intent intent)
	{
		boolean isEnter = intent.getBooleanExtra(LocationManager.KEY_PROXIMITY_ENTERING,
				false);
		
		Toast.makeText(context, isEnter ? "I am in":"I am out", Toast.LENGTH_LONG).show();

	}

}
