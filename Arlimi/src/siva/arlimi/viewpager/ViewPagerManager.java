package siva.arlimi.viewpager;

import siva.arlimi.activity.R;

import siva.arlimi.adapter.TabPagerAdapter;
import android.app.ActionBar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

public class ViewPagerManager
{
	private ViewPager mViewPager;
	private TabPagerAdapter mTapPagerAdapt;
	
	FragmentActivity mActivity;

	public ViewPagerManager(FragmentActivity activity)
	{
		mActivity = activity;
		
		mViewPager = (ViewPager) mActivity.findViewById(R.id.pager); //activity_main.xml
		mTapPagerAdapt = new TabPagerAdapter(mActivity.getSupportFragmentManager());
		mViewPager.setAdapter(mTapPagerAdapt);
	}
	
	public ViewPager getViewPager()
	{
		return this.mViewPager;
	}
	
	public void setOnPageChangerListener(final ActionBar actionbar)
	{
		mViewPager.setOnPageChangeListener(new  ViewPager.OnPageChangeListener()
		{
			
			@Override
			public void onPageSelected(int position)
			{
				actionbar.setSelectedNavigationItem(position);
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2)
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0)
			{
				// TODO Auto-generated method stub
				
			}
		});
		
	}

	

}