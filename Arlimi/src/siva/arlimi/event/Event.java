package siva.arlimi.event;

import java.io.Serializable;

import siva.arlimi.owner.Owner;
import android.content.Context;

public class Event implements Serializable
{
	private static final long serialVersionUID = -4830835855324278850L;
	public static final String TAG = "Event";

	private Context mContext;
	
	private EventTime mEventStartTime;
	private EventTime mEventEndTime;
	private EventDate mEventStartDate;
	private EventDate mEventEndDate;
	private String mContents;
	private String mBusinessName;
	
	private Owner mOwner;
	
	private boolean mIsStart = false;
	
	public Event()
	{
	}

	public Event(Context context)
	{
		this.mContext = context;
		mOwner = new Owner();
	}

	public void setBusinessName(String name)
	{
		mBusinessName= name;
	}
	
	public String getBusinessName()
	{
		return this.mBusinessName;
	}
	
	public void setContents(String contents)
	{
		this.mContents = contents;
	}
	
	public String getContents()
	{
		return mContents;
	}
	
	public void showTimePicker()
	{
		if(mIsStart)
		{
			mEventStartTime = new EventTime(mContext);
			mEventStartTime.showTimePicker();
		}
		else
		{
			mEventEndTime = new EventTime(mContext);
			mEventEndTime.showTimePicker();
		}
	}
	
	public void setIsStart(boolean isStart)
	{
		mIsStart = isStart;
	}

	public EventDate getEventStartDate()
	{
		if( null == mEventStartDate)
			return null;
		else
			return mEventStartDate;
	}
	
	
	public EventDate getEventEndDate()
	{
		if( null == mEventEndDate)
			return null;
		else
			return mEventEndDate;
	}
	
	public EventTime getEventStartTime()
	{
		if(null == mEventStartTime)
			return null;
		else
			return mEventStartTime;
	}
	
	public EventTime getEventEndTime()
	{
		if(null == mEventEndTime)
			return null;
		else
			return mEventEndTime;
	}
	
	public void setOwner(Owner owner)
	{
		this.mOwner = owner;
	}
	
	public Owner getOwner()
	{
		return this.mOwner;
	}
	
	public void showDatePicker()
	{
		if(mIsStart)
		{
			mEventStartDate = new EventDate(mContext);
			mEventStartDate.showDialog("Start Date");
		}
		else
		{
			mEventEndDate = new EventDate(mContext);
			mEventEndDate.showDialog("End Date");
		}
	}
}
