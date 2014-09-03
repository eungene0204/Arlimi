package siva.arlimi.viewpager.adapter;

import siva.arlimi.event.fragment.AllListFragment;
import siva.arlimi.event.fragment.EventListFragment;
import siva.arlimi.event.fragment.FavoriteListFragment;
import siva.arlimi.navdrawer.util.ITEM_ID;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter
{
	public static final String TAG = "ViewPagerAdapter";
	
	private final FragmentManager mFm;

	public ViewPagerAdapter(FragmentManager fm)
	{
		super(fm);
		
		mFm = fm;
		
	}

	@Override
	public Fragment getItem(int index)
	{
		Fragment fragment = null;
	
		switch(index)
		{
		case 0:
			fragment = new EventListFragment();
			break;
			
		case 1:
			fragment = new FavoriteListFragment();
			break;
			
		case 2:
			fragment = new AllListFragment();
			break;
		} 
		
		
		return fragment;

	}

	@Override
	public int getCount()
	{
		return 3;
	}

}
