package siva.arlimi.shop.fragment;

import siva.arlimi.main.R;

import siva.arlimi.shop.adapter.ShopAddressAdapter;
import siva.arlimi.shop.adapter.ShopAddressAdapter.ViewHolder;
import siva.arlimi.shop.util.AddrNodeList;
import siva.arlimi.shop.util.ShopUtils;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;

public class ShowAddressDialogFragment extends DialogFragment 
{
	public static final String TAG = "ShowAddressDialogFragment";
	
	private Context mActivity;
	
	private ListView mListView;
	private ShopAddressAdapter mAdapater;
	
	private onSelectAddressListener mAddressSelectListener;
	
	public ShowAddressDialogFragment()
	{
		// TODO Auto-generated constructor stub
	}
	
	public ShowAddressDialogFragment(Activity activity)
	{
		this.mActivity = activity;
	}
	
	@Override
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
		
		try
		{
			mAddressSelectListener = (onSelectAddressListener)activity;
		}
		catch(ClassCastException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		Log.d(TAG, "onCreateDialog");

		LayoutInflater inflater = 
				(LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.dialogfragment_search_address, null, false);
		mListView = (ListView) view.findViewById(R.id.shop_address_listview);
	
		Bundle bundle = getArguments();
		String result = bundle.getString(ShopUtils.KEY_ADDRESS_SEARCH_RESULT);
		AddrNodeList nodeList = ShopUtils.parseXml(result);

		this.mAdapater = new ShopAddressAdapter(getActivity(), nodeList);
		
		mListView.setAdapter(mAdapater);
		mListView.setOnItemClickListener(new ItemClickListener());
		
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		
		builder.setTitle(getActivity().getResources().getString(R.string.dialog_search_address_title));
		builder.setView(view);
		//builder.setAdapter(mAdapater, mListener);
		
		return builder.create();
	}
	
	private class ItemClickListener implements AdapterView.OnItemClickListener
	{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id)
		{
			ViewHolder holder =  (ViewHolder) view.getTag(); 
			Bundle bundle = new Bundle();
			
			bundle.putString(ShopUtils.KEY_ZIP,
					holder.mZipTextView.getText().toString());
			bundle.putString(ShopUtils.KEY_ADDRESS,
					holder.mAddrTextView.getText().toString());
			
			mAddressSelectListener.onSelectAddress(bundle);
			
			dismiss();
			
		}
	}
	
	public interface onSelectAddressListener
	{
		void onSelectAddress(Bundle address);
	}
	
}
