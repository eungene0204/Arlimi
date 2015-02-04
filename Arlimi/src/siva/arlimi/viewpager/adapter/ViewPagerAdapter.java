package siva.arlimi.viewpager.adapter;

import siva.arlimi.event.fragment.AllListFragment;
import siva.arlimi.event.fragment.FavoriteListFragment;
import siva.arlimi.service.fragment.RecommendedServiceListFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter
{
	public static final String TAG = "ViewPagerAdapter";
	
	private static final int NUM_PAGES = 3; 
	
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
			fragment = new RecommendedServiceListFragment();
			//fragment = new EventListFragment();
			break;
			
		case 1:
			fragment = new FavoriteListFragment();
			break;
			
		case 2:
			fragment = new AllListFragment();
			break;
			
			default:
				break;
		} 
		
		return fragment;
	}

	@Override
	public int getCount()
	{
		return NUM_PAGES;
	}

}
