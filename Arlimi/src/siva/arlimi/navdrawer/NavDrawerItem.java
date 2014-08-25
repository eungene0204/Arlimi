package siva.arlimi.navdrawer;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class NavDrawerItem
{
	private String mTitle;
	private String mLogin;
	
	private String[]  mEventItems;
	private String[] mToolItem;

	public NavDrawerItem()
	{
	}
	
	public void addEventItem(String[] item)
	{
		if(null == item)
			item = new String[0];
		
		mEventItems = item;
	}
	
	public void addToolItem(String[] item)
	{
		if(null == item)
			item = new String[0];
		
		mToolItem = item;
	}
	
	public void setLogin(String login)
	{
		this.mLogin = login;
	}
	
	
	public void setTitle(String title)
	{
		mTitle = title;
	}
	
	public String getTitle()
	{
		return mTitle;
	}
}
