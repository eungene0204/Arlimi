package siva.arlimi.activity;

import java.util.ArrayList;

import siva.arlimi.fragment.EventListFragment;
import siva.arlimi.fragment.FeedbackFragment;
import siva.arlimi.fragment.SettingFragment;
import siva.arlimi.navdrawer.NavDrawerItem;
import siva.arlimi.navdrawer.adapter.NavDrawerListAdapter;
import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
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
	
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	private ArrayList<NavDrawerItem> mNavDrawerItems;
	private NavDrawerListAdapter mDrawerAdapter;
	
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	
	private ActionBar mActionBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_arlimi_main);
	
		getSupportFragmentManager().beginTransaction().add(R.id.frame_container, 
				new EventListFragment()).commit();
		
		mActionBar = getActionBar();
		
		mTitle = mDrawerTitle = getTitle();

		setNavigationDrawer();
		setActionBarOption();
	}
	

	private void setActionBarOption()
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
	
	private void setNavigationDrawer()
	{
		String login = getResources().getString(R.string.login);
		String[] eventItems =  
				getResources().getStringArray(R.array.drawer_list_event_items);
		String[] toolItems =
				getResources().getStringArray(R.array.drawer_list_tool);
		
	
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.drawer_listview);
		mDrawerList.setBackgroundColor(Color.WHITE);
	
		NavDrawerItem  items = new NavDrawerItem();
		items.setLogin(login);
		items.addEventItem(eventItems);
		items.addToolItem(toolItems);
		
		mNavDrawerItems = new ArrayList<NavDrawerItem>();
	
		mDrawerAdapter = new NavDrawerListAdapter(this, mNavDrawerItems);
		mDrawerList.setAdapter(mDrawerAdapter);
		//mDrawerList.setOnItemClickListener(new NavDrawerClickListener());
		mDrawerList.setOnItemClickListener(new ItemClickListener());
		
		mDrawerToggle = new ActionBarDrawerToggle (this, mDrawerLayout,
				R.drawable.ic_drawer, 
				R.string.drawer_open,
				R.string.drawer_close)
		{
			public void onDrawerClosed(View drawerView)
			{
				Log.i(TAG, "drawer closed");
				//getActionBar().setTitle(mTitle);
				invalidateOptionsMenu();
			}
			
			public void onDrawerOpened(View drawerView)
			{
				Log.i(TAG, "drawer open");
				//getActionBar().setTitle(mDrawerTitle);
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);
	
	} 
	
	private class ItemClickListener implements AdapterView.OnItemClickListener
	{
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id)
		{
			displayFragmentView(position);
		}

		private void displayFragmentView(int position)
		{
			// Fix it to NullFragment;
			Fragment fragment = null;

			switch (position)
			{
			case 0:
				fragment = new SettingFragment();
				mActionBar.setTitle("Setting");
				break;

			case 1:
				fragment = new FeedbackFragment();
				break;

			default:
				break;
			}

			if (null != fragment)
			{
				getSupportFragmentManager().beginTransaction()
						.replace(R.id.frame_container, fragment).commit();
				
				mDrawerList.setItemChecked(position, true);
				mDrawerList.setSelection(position);
				mDrawerLayout.closeDrawer(mDrawerList);
			} else
			{
				Log.e(TAG, "Error in creaing fragment");
			}

		}

	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState)
	{
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig)
	{
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
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
		if(mDrawerToggle.onOptionsItemSelected(item))
		{
			Log.i(TAG, "Toggle onOptionItemSelected");
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}


}
