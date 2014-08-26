package siva.arlimi.navdrawer;

public class NavDrawerItem 
{
	private String mTitle;
	private final int mItemType;
	private final int mId;
	
	public NavDrawerItem(final int type, final int id)
	{
		mItemType = type;
		mId = id;
	}

	public int getItemType() 
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
	
}
