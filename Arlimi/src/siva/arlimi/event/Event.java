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
	private EventRadius mEventRadius;

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

	public void setRadius(EventRadius radius)
	{
		this.mEventRadius = radius;
	}
	
	public EventRadius getRadius()
	{
		return this.mEventRadius;
	}

	public void setBusinessName(String name)
	{
		mBusinessName = name;
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
		if (mIsStart)
		{
			mEventStartTime = new EventTime(mContext);
			mEventStartTime.showTimePicker();
		} else
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
		return (null == mEventStartDate) ? new EventDate(mContext): mEventStartDate;
	}

	public EventDate getEventEndDate()
	{
		return  (null == mEventEndDate) ? new EventDate(mContext) : mEventEndDate;
	}

	public EventTime getEventStartTime()
	{
		return (null == mEventStartTime) ? new EventTime(mContext) : mEventStartTime;
	}

	public EventTime getEventEndTime()
	{
		return (null == mEventEndTime) ? new EventTime(mContext) : mEventEndTime;
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
		if (mIsStart)
		{
			mEventStartDate = new EventDate(mContext);
			mEventStartDate.showDialog("Start Date");
		} else
		{
			mEventEndDate = new EventDate(mContext);
			mEventEndDate.showDialog("End Date");
		}
		
	}
}
