package siva.arlimi.actionbar;

import siva.arlimi.activity.R;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

public class ActionBarManager implements TabListener
{
	private FragmentActivity mActivity;
	private ViewPager mViewPager;
	
	private ActionBar mActionBar;
	private String[] m_tabs = {"Event", "Favorite", "Setting"};
	private int[] m_icons = {R.drawable.ic_action_view_as_list,
		R.drawable.ic_action_important, R.drawable.ic_action_settings};
	

	public ActionBarManager(FragmentActivity activity, ActionBar actionbar)
	{
		this.mActivity = activity;
		this.mActionBar = actionbar;
	}
	
	public void setHomeButtonEnabled(boolean enable)
	{
		mActionBar.setHomeButtonEnabled(enable);
	}
	
	public void setNavigatonMode(int mode)
	{
		mActionBar.setNavigationMode(mode);
	}
	
	public void setDisplayOptions(int option)
	{
		mActionBar.setDisplayOptions(option);
	}
	
	public void setViewPager(ViewPager viewPager)
	{
		this.mViewPager = viewPager;
	}
	
	public void addTab(int tabCount)
	{
		for(int i = 0; i < tabCount; i++)
		{
			mActionBar.addTab(mActionBar.newTab()
			.setIcon(m_icons[i])
			.setText(m_tabs[i])
			.setTabListener(this));
		}
		
	}
	
	public ActionBar getActionBar()
	{
		return this.mActionBar;
	}
 

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft)
	{
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft)
	{
		// TODO Auto-generated method stub
		
	}
}
