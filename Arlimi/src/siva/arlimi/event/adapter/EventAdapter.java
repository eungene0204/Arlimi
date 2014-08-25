package siva.arlimi.event.adapter;

import java.util.ArrayList;

import siva.arlimi.activity.R;
import siva.arlimi.event.Event;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class EventAdapter extends BaseAdapter 
{
	public static final String TAG = "EventAdapter";
	
	private ArrayList<Event> mEventList;
	private int mItemLayout;
	private Context mContext;
	
	public EventAdapter(FragmentActivity activity, int listviewItem,
			ArrayList<Event> eventList)
	{
		this.mContext = activity;
		mItemLayout = listviewItem;
		mEventList = (null == eventList) ? new ArrayList<Event>() :
			eventList;
	}

	public void setEventList(final ArrayList<Event> eventList)
	{
		mEventList = (null == eventList) ? new ArrayList<Event>() :
			eventList;
	}

	public ArrayList<Event> getEvetList()
	{
		return mEventList;
	}

	@Override
	public int getCount()
	{
		return mEventList.size();
	}

	@Override
	public Object getItem(int position)
	{
		return mEventList.get(position);
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
			convertView = View.inflate(mContext, mItemLayout, null);
		
		TextView name = (TextView) convertView.findViewById(R.id.listview_item_shop_name);
		
		name.setText(mEventList.get(position).getBusinessName());
		
		return convertView;
	}
	

}
