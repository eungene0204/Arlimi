package siva.arlimi.service.fragment;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import siva.arlimi.main.R;
import siva.arlimi.service.Service;
import siva.arlimi.service.adapter.ServiceAdapter;
import siva.arlimi.service.service.AllServiceListService;
import siva.arlimi.service.service.AllServiceListService.AllServiceListBinder;
import siva.arlimi.service.service.AllServiceListService.OnResultListener;
import siva.arlimi.service.util.ServiceUtil;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class RecommendedServiceListFragment extends Fragment implements OnResultListener
{
	static public final String TAG = "RecommendedServiceListFargment";

	private ServiceConn mSvrConn = new ServiceConn();
	private AllServiceListService mService;
	private ListView mListView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		Log.i(TAG, "onCreateView");
		
		View root = inflater.inflate(R.layout.fragment_recommended_service_listview, container,
				false); 
	
		mListView =
				(ListView)root.findViewById(R.id.recommended_service_listview); 
		
		
		return root; 
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		
		Log.i(TAG, "onResume");
		//Bind Service 
		Intent intent = ServiceUtil.getAllServiceListIntent(getActivity());
		getActivity().bindService(intent, mSvrConn, getActivity().BIND_AUTO_CREATE);
		
	}
	
	@Override
	public void onPause()
	{
		super.onPause();
		
		Log.i(TAG, "onPause");
		//UnBind Service
		getActivity().unbindService(mSvrConn);
	}
	
	class ServiceConn implements ServiceConnection
	{
		@Override
		public void onServiceConnected(ComponentName name, IBinder service)
		{
			mService = ((AllServiceListBinder)service).getService();
			mService.setClient(RecommendedServiceListFragment.this);
		}

		@Override
		public void onServiceDisconnected(ComponentName name)
		{
			mService = null;
		}
	}

	@Override
	public void getResult(String result) 
	{
		// TODO Auto-generated method stub
		Log.i(TAG, result);
		
		JSONArray jsonArr = null;
		try
		{
			jsonArr = new JSONArray(result.trim()); 
			
		} catch (JSONException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ArrayList<Service> list = new ArrayList<Service>();
		int size = jsonArr.length();
		
		for(int i = 0; i < size; i++)
		{
			Service service = new Service();
			
				try
				{
					JSONObject json = (JSONObject) jsonArr.get(i);
					
					service.setmName(json.getString(ServiceUtil.SHOP_NAME));
					service.setmAddress(json.getString(ServiceUtil.SHOP_ADDRESS));
					service.setmDetailAddress(json.getString(ServiceUtil.SHOP_ADDRESS_DETAIL));
					service.setmPhoneNum(json.getString(ServiceUtil.SHOP_NUM));
					service.setmZip(json.getString(ServiceUtil.SHOP_ZIP));
					
					list.add(service);
		
				} catch (JSONException e)
				{
					e.printStackTrace();
				}
		}
		
		ServiceAdapter serviceAdapter = new ServiceAdapter(getActivity(),
				R.layout.service_list_item_layout, list);
		
	
		if(null != mListView)
		{
			Log.i(TAG, "not null");
			mListView.setAdapter(serviceAdapter);
		}
		
			/*	
		mListView = (ListView)root.findViewById(R.id.eventlist_listview); 
		mListView.setAdapter(eventAdapter);
		mListView.setOnItemClickListener(new ItemClickListener()); */
	
	}


}
