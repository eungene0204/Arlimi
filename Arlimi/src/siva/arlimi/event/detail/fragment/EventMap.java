package siva.arlimi.event.detail.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;

public class EventMap extends MapFragment
{
	public static final String TAG = "EventMap";
	
	private GoogleMap mGoogleMap;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		mGoogleMap = getMap();
		
		if(null == mGoogleMap)
			Log.e(TAG, "GoogleMap is null");
			
		return super.onCreateView(inflater, container, savedInstanceState);
	}

}
