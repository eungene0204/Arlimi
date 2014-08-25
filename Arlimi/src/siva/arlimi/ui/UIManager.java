package siva.arlimi.ui;

import siva.arlimi.actionbar.manager.ActionBarManager;
import android.app.ActionBar;
import android.support.v4.app.FragmentActivity;

public class UIManager
{
	FragmentActivity mActivity;
	
	private ViewPagerManager mViewPagerMng;
	private ActionBarManager mActionBarMng;
	
	public UIManager(FragmentActivity activity, ActionBar actionBar)
	{
		mViewPagerMng = new ViewPagerManager(activity);
		mActionBarMng= new ActionBarManager(activity, actionBar);
	}
	
	public void setActionBar()
	{
		mActionBarMng.setHomeButtonEnabled(false);
		mActionBarMng.setNavigatonMode(ActionBar.NAVIGATION_MODE_TABS);
		mActionBarMng.setDisplayOptions(ActionBar.DISPLAY_SHOW_HOME);
		mActionBarMng.setViewPager(mViewPagerMng.getViewPager());
	}
	
	public void addTab(int tabCount)
	{
		mActionBarMng.addTab(tabCount);
	}
	
	public void viewPagerPageChangerListener()
	{
		mViewPagerMng.setOnPageChangerListener(mActionBarMng.getActionBar());
	}
	

}
