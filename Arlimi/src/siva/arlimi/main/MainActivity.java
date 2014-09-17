package siva.arlimi.main;

import java.io.Serializable;
import java.util.ArrayList;


import siva.arlimi.event.fragment.AllListFragment;
import siva.arlimi.event.fragment.EventListFragment;
import siva.arlimi.event.fragment.FavoriteListFragment;
import siva.arlimi.fragment.LoginFragment;
import siva.arlimi.navdrawer.NavDrawerItem;
import siva.arlimi.navdrawer.NavigationDrawer;
import siva.arlimi.navdrawer.ViewHolder;
import siva.arlimi.navdrawer.adapter.NavDrawerListAdapter;
import siva.arlimi.navdrawer.util.ITEM_ID;
import siva.arlimi.viewpager.adapter.ViewPagerAdapter;
import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

public class MainActivity extends FragmentActivity 
{

	public static final String TAG = "MainActivity";
	
	private ImageView overflowIcon;
	
	private ActionBar mActionBar;
	
	private ViewPager mViewPager;
	private ViewPagerAdapter mViewPagerAdapter;
	
	private NavigationDrawer mNavDrawer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_arlimi_main);
		
		//Init ViewPager
		mViewPager = (ViewPager)findViewById(R.id.pager);
		mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
		mViewPager.setAdapter(mViewPagerAdapter);
	
		//getSupportFragmentManager().beginTransaction().add(R.id.frame_container, 
		//		new EventListFragment()).commit();
		
		mActionBar = getActionBar();

		initActionBarOption();
		
		mNavDrawer = new NavigationDrawer(this,mViewPager);
		
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


}
