package siva.arlimi.navdrawer.adapter;

import java.util.ArrayList;


import siva.arlimi.main.R;
import siva.arlimi.navdrawer.NavDrawerItem;
import siva.arlimi.navdrawer.ViewHolder;
import siva.arlimi.navdrawer.util.ITEM_TYPE;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class NavDrawerListAdapter extends BaseAdapter
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
			else if(type == ITEM_TYPE.DIVIDER.ordinal())
			{
				 convertView =
					 mInflater.inflate(R.layout.drawer_list_divider, null);
			}
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
