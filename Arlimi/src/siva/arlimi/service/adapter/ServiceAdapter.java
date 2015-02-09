package siva.arlimi.service.adapter;

import java.util.ArrayList;

import siva.arlimi.main.R;
import siva.arlimi.service.Service;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ServiceAdapter extends BaseAdapter
{
	private ArrayList<Service> mList;
	private final int mItemLayout;
	private final Context mContext;

	public ServiceAdapter(Context context, int layout)
	{
		this.mContext = context;
		this.mItemLayout = layout;
	}

	public ServiceAdapter(Context context, int layout, ArrayList<Service> list)
	{
		this.mList = list;
		this.mContext = context;
		this.mItemLayout = layout;
	}

	@Override
	public int getCount()
	{
		return mList.size();
	}

	@Override
	public Object getItem(int position)
	{
		return mList.get(position);
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

		if (null == convertView)
		{
			convertView = View.inflate(mContext, mItemLayout, null);
			
			viewHolder = new ViewHolder();
			
			viewHolder.mName = (TextView) convertView
				.findViewById(R.id.listview_item_shop_name);
			
			convertView.setTag(viewHolder);
			
		}
		else
		{
			viewHolder = (ViewHolder)convertView.getTag();
		}
		
		viewHolder.mName.setText(mList.get(position).getmName());


		return convertView;

	}
	
	private static class ViewHolder
	{
		public TextView mName;
		
	}
}
