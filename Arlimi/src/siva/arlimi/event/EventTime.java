package siva.arlimi.event;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.util.Log;
import android.widget.TimePicker;

public class EventTime implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4428426446096864563L;

	public static final String TAG = "EventTime";
	
	Context mContext;
	
	protected int mHour;
	protected int mMin;
	transient protected Calendar cal;
	
	private boolean isStartTime;
	
	public EventTime()
	{
	}
	
	public EventTime(Context context)
	{
		mContext = context;
		
		cal = new GregorianCalendar();
		mHour = cal.get(Calendar.HOUR_OF_DAY);
		mMin = cal.get(Calendar.MINUTE);
	}
		
	public int getHour()
	{
		return this.mHour;
	}
	
	public int getMin()
	{
		return this.mMin;
	}

	public void showTimePicker()
	{
		new TimePickerDialog(mContext, mTimeSetListener, 
				mHour, mMin, false).show();
	}
	
	OnTimeSetListener mTimeSetListener = new OnTimeSetListener()
	{
		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute)
		{
			if(mHour > 12)
				mHour = hourOfDay - 12;
			else
				mHour = hourOfDay;
			
			mMin = minute;
			
			
			Log.i(TAG, "Hour: " + mHour);
			Log.i(TAG, "Min: " + mMin);
		}
	};

	public void setIsStartTime(boolean isStart)
	{
		isStartTime = isStart;
	}
	
	public boolean isStartTime()
	{
		return isStartTime;
	}
}
