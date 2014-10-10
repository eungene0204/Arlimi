package siva.arlimi.navdrawer;

import java.io.Serializable;
import java.util.ArrayList;

import siva.arlimi.auth.activity.LoginActivity;
import siva.arlimi.main.MainActivity;
import siva.arlimi.main.R;
import siva.arlimi.navdrawer.adapter.NavDrawerListAdapter;
import siva.arlimi.navdrawer.adapter.ViewHolder;
import siva.arlimi.navdrawer.util.ITEM_ID;
import siva.arlimi.navdrawer.util.ITEM_TYPE;
import siva.arlimi.shop.activity.ShopRegistrationActivity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class NavigationDrawer
{
	public static final String TAG = "NavigationDrawer";
			
	private final MainActivity mContext;
	
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private ArrayList<NavDrawerItem> mNavDrawerItems;
	private NavDrawerListAdapter mDrawerAdapter;
	
	private final ViewPager mViewPager;
	

	public NavigationDrawer(MainActivity context, ViewPager pager)
	{
		mContext = context;
		mViewPager = pager;
		
		initNavigationDrawer(context);
	}
		
	private void initNavigationDrawer(final FragmentActivity context)
	{
		mDrawerLayout = (DrawerLayout) context.findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) context.findViewById(R.id.drawer_listview);
		mDrawerList.setBackgroundColor(Color.WHITE);
	
		mNavDrawerItems = new ArrayList<NavDrawerItem>();
		
		addItems(mNavDrawerItems);
	
		mDrawerAdapter = new NavDrawerListAdapter(mContext, mNavDrawerItems);
		mDrawerList.setAdapter(mDrawerAdapter);
		mDrawerList.setOnItemClickListener(new ItemClickListener());
		
		mDrawerToggle = new ActionBarDrawerToggle (context, mDrawerLayout,
				R.drawable.ic_drawer, 
				R.string.drawer_open,
				R.string.drawer_close)
		{
			public void onDrawerClosed(View drawerView)
			{
				Log.i(TAG, "drawer closed");
				context.invalidateOptionsMenu();
			}
			
			public void onDrawerOpened(View drawerView)
			{
				Log.i(TAG, "drawer open");
				context.invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);
	
	} 
	
	private void addItems(ArrayList<NavDrawerItem> list)
	{
		String login = mContext.getResources().getString(R.string.login);
		String event = mContext.getResources().getString(R.string.event);
		String tool = mContext.getResources().getString(R.string.tool);
		String registration = mContext.getResources().getString(R.string.registration);
		String[] eventItems = mContext.getResources().getStringArray(
				R.array.drawer_list_event_items);
		String[] toolItems = mContext.getResources().getStringArray(
				R.array.drawer_list_tool);
		
		ITEM_ID[] eventItemIds = {ITEM_ID.CURRENT_REGION, 
								ITEM_ID.FAVORITE,
								ITEM_ID.ALL_EVENT};
		
		ITEM_ID[] toolItemIDs = {ITEM_ID.SETTING,
							ITEM_ID.FEEDBACK,
							ITEM_ID.SHARE };
		
		//Add Login item
		NavDrawerItem loginItem =
				new NavDrawerItem(ITEM_TYPE.LOGIN, ITEM_ID.LOG_IN);
		loginItem.setTitle(login);
		list.add(loginItem);
		
		//Add Divider
		NavDrawerItem divider = new NavDrawerItem(ITEM_TYPE.DIVIDER);
		list.add(divider);
		
		
		//Add Event Section Title
		NavDrawerItem eventSectionTitle = 
				new NavDrawerItem(ITEM_TYPE.SECTIONTITLE);
		eventSectionTitle.setTitle(event);
		list.add(eventSectionTitle);
		
		// Add event Items 
		for(int i = 0; i < eventItems.length; i++)
		{
			NavDrawerItem item =
					new NavDrawerItem(ITEM_TYPE.ITEM, eventItemIds[i]);
			item.setTitle(eventItems[i]);
			
			list.add(item);
		}
		
		//Add Divider
		list.add(divider);
		
		//Add Tool Section Title
		NavDrawerItem toolSectionTitle = 
				new NavDrawerItem(ITEM_TYPE.SECTIONTITLE);
		toolSectionTitle.setTitle(tool);
		list.add(toolSectionTitle);
	
		//Add Tool Items;
		for(int i = 0; i < toolItems.length; i++)
		{
			NavDrawerItem item = 
					new NavDrawerItem(ITEM_TYPE.ITEM, toolItemIDs[i]);
			item.setTitle(toolItems[i]);
		
			list.add(item);
		}
		
		//Add Divider
		list.add(divider);
		
		//Add registration
		NavDrawerItem regItem = new NavDrawerItem(ITEM_TYPE.REGISTRATION,
				ITEM_ID.REGISTRATION);
		regItem.setTitle(registration);
		list.add(regItem);
		
	}
	
	public void syncState()
	{
		mDrawerToggle.syncState();
	}
	
	public void onConfigurationChanged(Configuration newConfig)
	{
		mDrawerToggle.onConfigurationChanged(newConfig);
	}
	
	public boolean onOptionsItemSelected(MenuItem item)
	{
		return mDrawerToggle.onOptionsItemSelected(item);
	}

	private class ItemClickListener implements AdapterView.OnItemClickListener
	{
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id)
		{
			ViewHolder holder = new ViewHolder();
			holder = (ViewHolder) view.getTag();
			displayFragment(holder.mId);
		}

		private void displayFragment(ITEM_ID id)
		{
			switch(id)
			{
			case LOG_IN:
				openLoginActivity();
				break;
				
			case CURRENT_REGION:
				mViewPager.setCurrentItem(0);
				break;
				
			case FAVORITE:
				mViewPager.setCurrentItem(1);
				break;
				
			case ALL_EVENT:
				mViewPager.setCurrentItem(2);
				break;
				
			case REGISTRATION:
				openShopRegistrationActivity();
				break;
			}
			
			mDrawerLayout.closeDrawer(mDrawerList);
		}

		private void openShopRegistrationActivity()
		{
			Intent intent = new Intent(mContext,ShopRegistrationActivity.class );
			mContext.startActivity(intent);
		}

		private void openLoginActivity()
		{
			Intent intent = new Intent(mContext, LoginActivity.class );
			mContext.startActivity(intent);
		}

	}

}
