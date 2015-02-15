package siva.arlimi.main;

import siva.arlimi.navdrawer.NavigationDrawer;
import siva.arlimi.service.util.ServiceUtil;
import siva.arlimi.viewpager.adapter.ViewPagerAdapter;
import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

public class MainActivity extends FragmentActivity 
{
	public static final String TAG = "MainActivity";
	
	private ImageView overflowIcon;
	
	private ActionBar mActionBar;
	
	private ViewPager mViewPager;
	private ViewPagerAdapter mViewPagerAdapter;
	
	private NavigationDrawer mNavDrawer;
	
	private OnServiceListListener mServiceCallback;
	
		
	private BroadcastReceiver mServiceListReceiver = new BroadcastReceiver()
	{
		@Override
		public void onReceive(Context context, Intent intent)
		{
			onServiceListReceiver(intent);
		}

	};
	
	private void onServiceListReceiver(Intent intent)
	{
		String result = intent.getStringExtra(ServiceUtil.KEY_ALL_SERVICE_LIST);
		
		Log.i(TAG, result.toString());
	}
	
	public void setServiceListListener(OnServiceListListener listner)
	{
		this.mServiceCallback = listner;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_arlimi_main);
		
		Log.i(TAG, "onCreate");
		
		//Init ViewPager
		mViewPager = (ViewPager)findViewById(R.id.main_viewpager);
		mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
		mViewPager.setAdapter(mViewPagerAdapter);
	
		//getSupportFragmentManager().beginTransaction().add(R.id.frame_container, 
		//		new EventListFragment()).commit();
		
		mActionBar = getActionBar();
		initActionBarOption();
		
		//Navigation Drawer
		mNavDrawer = new NavigationDrawer(this,mViewPager);
		
	}
	
	
	@Override
	protected void onResume()
	{
		// TODO Auto-generated method stub
		super.onResume();
		
		Log.i(TAG, "onResume");
		
		//register Broadcast
		IntentFilter serviceListfilter = new IntentFilter(ServiceUtil.ACTION_ALL_SERVICE_LIST);
		registerReceiver(mServiceListReceiver, serviceListfilter);
	}
	
	@Override
	protected void onPause()
	{
		super.onPause();
		
		Log.i(TAG, "onPause");
		
		unregisterReceiver(mServiceListReceiver);
	}


	private void initActionBarOption()
	{
		ActionBar actionBar = getActionBar();
		
		overflowIcon = new ImageView(getApplicationContext());
		overflowIcon.setImageResource(R.drawable.ic_action_overflow);
		ActionBar.LayoutParams layout = 
				new LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT,
						ActionBar.LayoutParams.WRAP_CONTENT);
		layout.setMargins(25, 0, 0, 0);
		overflowIcon.setLayoutParams(layout);
		//overflowIcon.setOnClickListener(this);
		overflowIcon.setId(R.id.actionbar_item_overflow);
		
		//actionBar.setCustomView(overflowIcon);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowTitleEnabled(true);
		
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState)
	{
		super.onPostCreate(savedInstanceState);
		mNavDrawer.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig)
	{
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
		mNavDrawer.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		//getMenuInflater().inflate(R.menu.actionbar_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		if(mNavDrawer.onOptionsItemSelected(item))
		{
			Log.i(TAG, "Toggle onOptionItemSelected");
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	public interface OnServiceListListener
	{
		void onListListener(final String result);
		
	}
	

}