package siva.arlimi.shop.progress;

import siva.arlimi.main.R;
import siva.arlimi.shop.util.ShopUtils;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

public class AddressSearchProgressBar
{
	private final ProgressBar mBar;
	private final Activity mAcitivity;
	private final Intent mIntent;
	
	public AddressSearchProgressBar(Activity activity, Intent intent, ProgressBar bar)
	{
		this.mAcitivity = activity;
		this.mIntent = intent;
		this.mBar = bar;
		
	}

	public void executeAddressSearchProgressTask()
	{
		new AddressSearchProgressTask().execute();
	}
	
	private class AddressSearchProgressTask extends AsyncTask<Void, Void, Void>
	{
		@Override
		protected void onPreExecute()
		{
			super.onPreExecute();
			mBar.setVisibility(ProgressBar.VISIBLE);
		}

		@Override
		protected Void doInBackground(Void... params)
		{
		
			mAcitivity.startService(mIntent);

			return null;
		}

		@Override
		protected void onPostExecute(Void result)
		{
			super.onPostExecute(result);
			//mBar.setVisibility(View.GONE);
		}
	
	}
	


}
