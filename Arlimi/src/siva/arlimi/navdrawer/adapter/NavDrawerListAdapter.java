package siva.arlimi.navdrawer.adapter;

import java.util.ArrayList;

import siva.arlimi.main.R;
import siva.arlimi.navdrawer.NavDrawerItem;
import siva.arlimi.navdrawer.NavDrawerUtil;
import siva.arlimi.navdrawer.ViewHolder;
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
		
		return (item.getItemType() == NavDrawerUtil.ITEM_LIST_SECTION_TITLE
				|| item.getItemType() == NavDrawerUtil.ITEM_LIST_DIVIDER)?
				false : true;
	}

	@Override
	public int getItemViewType(int position)
	{
		NavDrawerItem item = mItemList.get(position);
	
		return (null!= item) ? item.getItemType() : NavDrawerUtil.ITEM_ERROR;
	}
	
	@Override
	public int getViewTypeCount()
	{
		return NavDrawerUtil.ITEM_MAX_COUNT;
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
			
			switch(type)
			{
			
			case NavDrawerUtil.ITEM_LIST_LOGIN:
				convertView = mInflater.inflate(R.layout.drawer_list_login, null);
				viewHolder.mItemTextView = (TextView)
						convertView.findViewById(R.id.drawer_list_item_login);
				break;
				
			case NavDrawerUtil.ITEM_LIST_ITEM:
				convertView = mInflater.inflate(R.layout.drawer_list_item, null);
				viewHolder.mItemTextView = (TextView)
						convertView.findViewById(R.id.drawer_list_item_title);
				break;
				
			case NavDrawerUtil.ITEM_LIST_DIVIDER:
				convertView = mInflater.inflate(R.layout.drawer_list_divider, null);
				break;
				
			case NavDrawerUtil.ITEM_LIST_SECTION_TITLE:
				convertView = mInflater.inflate(R.layout.drawer_list_section_title, null);
				viewHolder.mItemTextView = (TextView)
						convertView.findViewById(R.id.drawer_list_section_title_textview);
				break;
			
			}
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
