package siva.arlimi.navdrawer;

import java.util.ArrayList;

import siva.arlimi.auth.activity.LoggedInActivity;
import siva.arlimi.auth.activity.LoginActivity;
import siva.arlimi.auth.session.SessionManager;
import siva.arlimi.main.MainActivity;
import siva.arlimi.main.R;
import siva.arlimi.navdrawer.util.ITEM_ID;
import siva.arlimi.navdrawer.util.ITEM_TYPE;
import siva.arlimi.user.User;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

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
	
	private final SessionManager mSession;
	

	public NavigationDrawer(MainActivity context, ViewPager pager)
	{
		mContext = context;
		mViewPager = pager;
		
		mSession = new SessionManager(mContext.getApplicationContext());
		
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
		String service = mContext.getResources().getString(R.string.serivce);
		String tool = mContext.getResources().getString(R.string.tool);
		String registration = mContext.getResources().getString(R.string.shop_registration);
		String[] eventItems = mContext.getResources().getStringArray(
				R.array.drawer_list_serice_items);
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
		
		//Check if user is logged in
		if(mSession.isLoggedIn())
		{
			User user = mSession.getUserDetails();
			
			String name = user.getName();
			loginItem.setTitle(name);
					
		}
		else
		{
			loginItem.setTitle(login);
		}

		list.add(loginItem);
		
	
		
		//Add Event Section Title
		NavDrawerItem serviceSectionTitle = 
				new NavDrawerItem(ITEM_TYPE.SECTIONTITLE);
		serviceSectionTitle.setTitle(service);
		list.add(serviceSectionTitle);
		
		// Add event Items 
		for(int i = 0; i < eventItems.length; i++)
		{
			NavDrawerItem item =
					new NavDrawerItem(ITEM_TYPE.ITEM, eventItemIds[i]);
			item.setTitle(eventItems[i]);
			
			list.add(item);
		}
		
		
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
		

		/*
		 * 
		// Add registration
		if(mSession.isLoggedIn())
		{
			NavDrawerItem regItem = new NavDrawerItem(ITEM_TYPE.REGISTRATION,
					ITEM_ID.REGISTRATION);
			regItem.setTitle(registration);
			list.add(regItem);
		} */
		
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
				
				if(mSession.isLoggedIn())
					openLoggedInActivity();
				else
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
				
	
			}
			
			mDrawerLayout.closeDrawer(mDrawerList);
		}

		private void openLoggedInActivity()
		{
			Intent intent = new Intent(mContext, LoggedInActivity.class );
			mContext.startActivity(intent);
		}

	
		private void openLoginActivity()
		{
			Intent intent = new Intent(mContext, LoginActivity.class );
			mContext.startActivity(intent);
		}

	}
	
	//Adapter
	private class NavDrawerListAdapter extends BaseAdapter
	{
		public static final String TAG = "NavDrawerListAdapter";
		public static final int ITEM_MAX_COUNT =  10;	
		
		private final Context mContext;
		private ArrayList<NavDrawerItem> mItemList;
		
		private final LayoutInflater mInflater;  
		
		public NavDrawerListAdapter(Context context, ArrayList<NavDrawerItem> list)
		{
			this.mContext = context;
			mItemList = list;

			mInflater = (LayoutInflater) mContext
					.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

		}
		
		@Override
		public boolean isEnabled(int position)
		{
			NavDrawerItem item = mItemList.get(position);
			
			return (item.getItemType() == ITEM_TYPE.SECTIONTITLE 
					|| item.getItemType() == ITEM_TYPE.DIVIDER)?
					false : true;
		}

		@Override
		public int getItemViewType(int position)
		{
			NavDrawerItem item = mItemList.get(position);
		
			return (null!= item) ? item.getItemType().ordinal() 
					: ITEM_TYPE.DEFAULT.ordinal();
		}
		
		@Override
		public int getViewTypeCount()
		{
			return ITEM_MAX_COUNT;
		}

		@Override
		public int getCount()
		{
			return mItemList.size();
		}

		@Override
		public Object getItem(int position)
		{
			return mItemList.get(position);
		}

		@Override
		public long getItemId(int position)
		{
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			ViewHolder viewHolder;
			int type = getItemViewType(position);
			
			if(null == convertView)
			{
				viewHolder = new ViewHolder();
				
				if(type == ITEM_TYPE.LOGIN.ordinal())
				{
					convertView = 
							mInflater.inflate(R.layout.drawer_list_login, null);
					viewHolder.mItemTextView = (TextView) convertView
							.findViewById(R.id.drawer_list_item_login);
					
				}
				else if(type == ITEM_TYPE.ITEM.ordinal())
				{
					convertView = mInflater
							.inflate(R.layout.drawer_list_item, null);
					viewHolder.mItemTextView = (TextView) convertView
							.findViewById(R.id.drawer_list_item_title);

				}
				/*else if(type == ITEM_TYPE.DIVIDER.ordinal())
				{
					 convertView =
						 mInflater.inflate(R.layout.drawer_list_divider, null);
				}*/
				else if(type == ITEM_TYPE.SECTIONTITLE.ordinal())
				{
					
					convertView =  mInflater.inflate(R.layout.drawer_list_section_title, null);
				  viewHolder.mItemTextView = (TextView) convertView.findViewById(R.id
				  .drawer_list_section_title_textview); 
				 
				}
				else if(type == ITEM_TYPE.REGISTRATION.ordinal())
				{
						convertView = mInflater
							.inflate(R.layout.drawer_shop_registration, null);
					viewHolder.mItemTextView = (TextView) convertView
							.findViewById(R.id.drawer_list_item_shop_registration);
				}

				/*
				 * switch(type) {
				 * 
				 * case DrawerUtil.ITEM_LIST_ITEM: convertView =
				 * mInflater.inflate(R.layout.drawer_list_item, null);
				 * viewHolder.mItemTextView = (TextView)
				 * convertView.findViewById(R.id.drawer_list_item_title); break;
				 * 
				 * case NavDrawerUtil.ITEM_LIST_DIVIDER: 
				 * convertView =
				 * mInflater.inflate(R.layout.drawer_list_divider, null); break;
				 * 
				 * case NavDrawerUtil.ITEM_LIST_SECTION_TITLE: convertView =
				 * mInflater.inflate(R.layout.drawer_list_section_title, null);
				 * viewHolder.mItemTextView = (TextView)
				 * convertView.findViewById(R.id
				 * .drawer_list_section_title_textview); break;
				 * 
				 * }
				 */
				convertView.setTag(viewHolder);
				
			}
			else
			{
				viewHolder = (ViewHolder)convertView.getTag(); 
			}
			
			if(null != viewHolder.mItemTextView)
			{
				viewHolder.mItemTextView.
					setText(mItemList.get(position).getTitle());
			}
			
			//Set Id
			viewHolder.mId = mItemList.get(position).getId();
		
			return convertView;
		}
		
	}
	
		private static class ViewHolder
		{
			public TextView mItemTextView;
			public ITEM_ID mId;
			
		}

}
