package siva.arlimi.shop.connection;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.io.CachedOutputStream;

import siva.arlimi.shop.util.ShopUtils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class SearchAddressConnection extends AsyncTask<String, String, String>
{
	private final String TAG = "SearchAddressConnection";
	
	private final Context mContext;
	private final OnSearchAddressListener mCallback;
	
	private String mDong;
	
	public SearchAddressConnection(Context context)
	{
		mContext = context;
		
		mCallback = (OnSearchAddressListener) mContext;
	}

	@Override
	protected String doInBackground(String... params)
	{
		Log.d(TAG, "doInBackground");
		
		String addr = "http://openapi.epost.go.kr/postal/retrieveLotNumberAdressService/retrieveLotNumberAdressService/getDetailList"
				+ "?ServiceKey=";

		String key = "fz+D92GHcx2tw1sxIdqMifIhApHVJoNcrzbD6NYOqGyf5aVdMRJ7jw+uDPup3jjK8ntUjWptZarC9Hpt353G6Q==";
		try
		{
			key = URLEncoder.encode(key, "UTF-8");
			mDong = URLEncoder.encode(mDong, "UTF-8");
		} catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}

		String param = "&searchSe=dong" +
		"&srchwrd=" + mDong;

		addr = addr + key + param;
		
		Log.d(TAG, addr);
		
		String result = "null";

		try
		{
			URL url = new URL(addr);
			InputStream in = url.openStream();
			CachedOutputStream bos = new CachedOutputStream();
			IOUtils.copy(in, bos);
			in.close();
			bos.close();
			result = bos.getOut().toString();

		} catch (MalformedURLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}
	
	@Override
	protected void onPostExecute(String result)
	{
		Log.d(TAG, "onPost result: " + result);
		
		super.onPostExecute(result);
		
		mCallback.getResult(result);
		mContext.stopService(ShopUtils.getSearchAddressServiceIntent(mContext));
	}
	
	public void setDong(String dong)
	{
		mDong = dong;
	}
	
	public interface OnSearchAddressListener
	{
		void getResult(String result);
	}
}
