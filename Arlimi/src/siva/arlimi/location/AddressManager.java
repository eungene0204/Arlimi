package siva.arlimi.location;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import siva.arlimi.activity.R;
import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

public class AddressManager
{
	private static final String TAG = "AddressManager";
	
	private Activity mContext;
	private ProgressBar mActivityIndicator;
	
	public AddressManager(Activity context)
	{
		this.mContext = context;
		
		mActivityIndicator = (ProgressBar)
				mContext.findViewById(R.id.address_progress);
		
	}
	
	public void setProgressBarVisibility(int val)
	{
		mActivityIndicator.setVisibility(val);
	}
	
	public void execute(Location location)
	{
		new GetAddressTask(mContext).execute(location);
	}
	
	private class GetAddressTask extends AsyncTask<Location, Void, String>
	{
		Context mContext;
		
		public GetAddressTask(Context context)
		{
			super();
			this.mContext = context;
		}

		@Override
		protected String doInBackground(Location... params)
		{
			Geocoder geoCoder = 
					new Geocoder(mContext, Locale.KOREA);
			
			Location location = params[0];
			
			List<Address> addresses = null;
			
			try
			{
				addresses = geoCoder.getFromLocation(location.getLatitude(), location.getLongitude(), 5);
			}
			catch (IOException e)
			{
				e.printStackTrace();
				
				return ("IO Exception trying to get address");
			}
			catch (IllegalArgumentException e)
			{
				e.printStackTrace();
				
				return ("IllegalArgumentException");
			}
			
			if(addresses != null && addresses.size() > 0)
			{
				Address address = addresses.get(0);
				
				String addressText = String.format(
						"%s, %s, %s", 
						//if there is a street address, add it
						address.getMaxAddressLineIndex() > 0 ?
								address.getAddressLine(0) :"",
								address.getLocality(),
								address.getCountryName()
						);
				
				return addressText;
				
				
			}
			else
			{
				return "No address found";
				
			}
			
		}
		
		@Override
		protected void onPostExecute(String address)
		{
			super.onPostExecute(address);
			
			mActivityIndicator.setVisibility(View.GONE);
			
			Log.i(TAG, address);
			
			
			Toast.makeText(mContext, address, Toast.LENGTH_LONG).show();
		}
	}

}


