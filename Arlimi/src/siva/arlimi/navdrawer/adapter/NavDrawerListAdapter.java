package siva.arlimi.navdrawer.adapter;

import java.util.ArrayList;

import siva.arlimi.activity.R;
import siva.arlimi.navdrawer.NavDrawerItem;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class NavDrawerListAdapter extends BaseAdapter
{
	private Context mContext;
	private ArrayList<NavDrawerItem> mItemList;
	
	public NavDrawerListAdapter(Context context, ArrayList<NavDrawerItem> list)
	{
		this.mContext = context;
		mItemList = list;
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
		if(null == convertView)
		{
			/*convertView =
					View.inflate(mContext, R.layout.drawer_list_item, null);*/
			LayoutInflater inflater = (LayoutInflater) 
					mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			
			convertView = inflater.inflate(R.layout.drawer_list_item, null);
		}
		
		TextView title = (TextView)
				convertView.findViewById(R.id.drawer_list_item_title);
		
		title.setText(mItemList.get(position).getTitle());
		
		return convertView;
	}

}
