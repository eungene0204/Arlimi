package siva.arlimi.adapter;

import siva.arlimi.fragment.EventListFragment;
import siva.arlimi.fragment.FavoriteListFragment;
import siva.arlimi.fragment.MyPageFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabPagerAdapter extends FragmentPagerAdapter
{
	public static final int TAB_COUNT= 3;
	
	public TabPagerAdapter(FragmentManager fm)
	{
		super(fm);
	}
	
	@Override
	public Fragment getItem(int index)
	{
		switch(index)
		{
		case 0:
			return new EventListFragment();
		case 1:
			return new FavoriteListFragment();
		case 2:
			//return new UserSettingsFragment();
			return new MyPageFragment();
		}
		
		return null;
		
	}
	
	@Override
	public int getCount()
	{
		return TAB_COUNT;
	}

}
