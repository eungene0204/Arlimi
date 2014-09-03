package siva.arlimi.event.detail.fragment;

import siva.arlimi.main.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;

public class EventMapFragment extends Fragment 
{
	public static final String TAG = "EventMap";
	
	private GoogleMap mGoogleMap;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View root = inflater.inflate(R.layout.event_detail_map, container,false);
		
		
		return root;
	}

	

}
