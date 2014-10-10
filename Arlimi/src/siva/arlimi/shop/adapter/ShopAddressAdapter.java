package siva.arlimi.shop.adapter;

import siva.arlimi.main.R;
import siva.arlimi.shop.util.AddrNodeList;
import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

public class ShopAddressAdapter implements ListAdapter
{
	private final LayoutInflater mInflater;
	
	private final Context mContext;
	private final AddrNodeList mList;
	
	public ShopAddressAdapter(Context context, AddrNodeList list)
	{
		this.mContext = context;
		this.mList = list;
		this.mInflater =
				(LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
	}
	

	@Override
	public void registerDataSetObserver(DataSetObserver observer)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver observer)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getCount()
	{
		return mList.getSize();
	}

	@Override
	public Object getItem(int position)
	{
		return mList.getNode(position);
	}

	@Override
	public long getItemId(int position)
	{
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public boolean hasStableIds()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder viewHolder;
		
		if(convertView == null)
		{
			viewHolder = new ViewHolder();
			
			convertView =
					mInflater.inflate(R.layout.adapter_address, null, false);
			
			viewHolder.mZipTextView = (TextView)
					convertView.findViewById(R.id.addrs_zip);
			viewHolder.mAddrTextView = (TextView)
					convertView.findViewById(R.id.addrs_dong);
			
			convertView.setTag(viewHolder);
			
		}
		else
		{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		viewHolder.mZipTextView.setText(mList.getNode(position).getmZipNum());
		viewHolder.mAddrTextView.setText(mList.getNode(position).getmAddress());
		
		return convertView;
	}
	
	public class ViewHolder 
	{
		public TextView mZipTextView;
		public TextView mAddrTextView;
		
	}

	@Override
	public int getItemViewType(int position)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getViewTypeCount()
	{
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public boolean isEmpty()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean areAllItemsEnabled()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled(int position)
	{
		// TODO Auto-generated method stub
		return false;
	}

}
