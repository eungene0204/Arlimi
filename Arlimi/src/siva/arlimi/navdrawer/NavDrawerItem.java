package siva.arlimi.navdrawer;

import siva.arlimi.navdrawer.util.ITEM_ID;
import siva.arlimi.navdrawer.util.ITEM_TYPE;

public class NavDrawerItem 
{
	private String mTitle;
	private final ITEM_TYPE mItemType;
	private final ITEM_ID mId;

	public NavDrawerItem(ITEM_TYPE type)
	{
		mItemType = type;
		mId = ITEM_ID.DEFAULT;
	}
	
	public NavDrawerItem(ITEM_TYPE type, ITEM_ID id)
	{
		mItemType = type;
		mId = id;
	}

	public ITEM_TYPE getItemType() 
	{
		return this.mItemType;
	}

	public void setTitle(String title)
	{
		mTitle = title;
	}
	
	public String getTitle()
	{
		return mTitle;
	}
	
	public ITEM_ID getId()
	{
		return this.mId;
	}
	
}
