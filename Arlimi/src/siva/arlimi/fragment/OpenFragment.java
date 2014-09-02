package siva.arlimi.fragment;

import siva.arlimi.main.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class OpenFragment extends Fragment
{
	private final static String TAG = "OpenFragment";
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		Log.i(TAG, this.toString() + " onCreate");
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		Log.i(TAG, this.toString() + " onCreateView");
		
		View view = inflater.inflate(R.layout.fragment_open, container,false);
	
		return view;
	}

	/*
	@Override
	public void onResume()
	{
		super.onResume();
		mFacebook.onResume();

	}
	
	@Override
	public void onPause()
	{
		super.onPause();
		mFacebook.onPause();
	}
	
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		mFacebook.onDestroy();
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		mFacebook.onActivityResult(requestCode, resultCode, data);
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);
		mFacebook.onSaveInstanceState(outState);
	} */

}
