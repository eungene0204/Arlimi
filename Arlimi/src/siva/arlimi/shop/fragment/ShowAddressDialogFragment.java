package siva.arlimi.shop.fragment;

import siva.arlimi.main.R;
import siva.arlimi.shop.adapter.ShopAddressAdapter;
import siva.arlimi.shop.util.AddrNodeList;
import siva.arlimi.shop.util.ShopUtils;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class ShowAddressDialogFragment extends DialogFragment implements android.view.View.OnClickListener
{
	private final String mResult;
	private ShopAddressAdapter mAdapter;
	
	private SearchShopAddressListener mAddressLisentner;
	
	
	public interface SearchShopAddressListener
	{
		void onSearchShopAddress(String result);
	}
	
	static ShowAddressDialogFragment newInstance(String result)
	{
		ShowAddressDialogFragment dialogFragment = new ShowAddressDialogFragment(result);
		
		Bundle bundle = new Bundle();
		bundle.putString(ShopUtils.ACTION_SEARCH_ADDRESS_RESULT, result);
		dialogFragment.setArguments(bundle);
		
		return dialogFragment;
	}
	
	public ShowAddressDialogFragment(String result)
	{
		this.mResult = result;
		
	}
	
	@Override
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
		
		try
		{
			mAddressLisentner = (SearchShopAddressListener)activity;
		}
		catch(ClassCastException e)
		{
			throw new ClassCastException(activity.toString() + 
					" must implemnet SearchShopAddressListener");
		}
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	
		AddrNodeList list = ShopUtils.parseXml(mResult);
		mAdapter = new ShopAddressAdapter(getActivity(), list);
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View root = inflater.inflate(R.layout.dialogfragment_search_address, 
				container,false);
		
		ListView listView = (ListView)
				root.findViewById(R.id.shop_address_listview);
		
		listView.setAdapter(mAdapter);
		listView.setOnClickListener(this);
		
		return root;
	}
	
	private OnClickListener positiveListener = new OnClickListener()
	{
		@Override
		public void onClick(DialogInterface dialog, int which)
		{
			AlertDialog alert = (AlertDialog) dialog;
			int position = alert.getListView().getCheckedItemPosition();
			
			//Get address here
			
		}
	};

	@Override
	public void onClick(View v)
	{
		// TODO Auto-generated method stub
		
	}

	/*
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) 
	{
		Bundle bundle = getArguments();
		String result = bundle.getString(ShopUtils.KEY_ADDRESS_SEARCH_RESULT);
	
		AddrNodeList list = ShopUtils.parseXml(result);
		ShopAddressAdapter adapter = new ShopAddressAdapter(getActivity(), list);
		
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle( getActivity().getResources().getString(R.string.shop_address));
		builder.setSingleChoiceItems(adapter, -1, null);
		builder.setPositiveButton(getActivity().getResources().getString(R.string.confirm)
				,positiveListener);
		builder.setNegativeButton(getActivity().getResources().getString(R.string.cancel)
				, null);
		
		AlertDialog dialog = builder.create();
		
		return dialog;
		
	} */
	
}
