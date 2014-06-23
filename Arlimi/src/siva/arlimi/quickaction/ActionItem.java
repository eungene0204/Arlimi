package siva.arlimi.quickaction;

import android.graphics.drawable.Drawable;

public class ActionItem
{
	private Drawable icon;
	private String title;
	private boolean isSelected;
	private boolean isSticky;
	private int actionId = -1;
	
	public ActionItem()
	{
	}
	
	public void setSticky(boolean isSticky)
	{
		this.isSticky = isSticky;
	}
	
	public boolean isSticky()
	{
		return this.isSticky;
		
	}
	
	public void setActionId(int id)
	{
		this.actionId = id;
	}
	
	public int getActionId()
	{
		return this.actionId;
	}
	
	public  ActionItem(Drawable icon)
	{
		this.icon = icon;
	}
	
	public void setTitle(String title)
	{
		this.title = title;
	}
	
	public String getTitle()
	{
		return this.title;
	}
	
	public void setIcon(Drawable icon)
	{
		this.icon = icon;
	}
	
	public Drawable getIcon()
	{
		return this.icon;
	}
	
	public boolean isSelected()
	{
		return this.isSelected;
	}

}
