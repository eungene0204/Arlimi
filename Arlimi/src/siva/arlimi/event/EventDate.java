package siva.arlimi.event;

import java.io.Serializable;
import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.util.Log;
import android.widget.DatePicker;

public class EventDate implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6860746910479850262L;

	public static final String TAG = "EventDate";
	
	private Context mContext;
	
	private int mDay;
	private int mMonth;
	private int mYear;
	
	private boolean mIsStart = false;
	
		
	DatePickerDialog.OnDateSetListener mDateSetListner = new OnDateSetListener()
	{
		
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth)
		{
			setYear(year);
			setMonth(monthOfYear + 1);
			setDay(dayOfMonth);
			
			Log.i(TAG, "Year: " + year + "Month: " + (monthOfYear + 1 )+ "Day: "
					+ dayOfMonth);
			
		}
	};
	
	public EventDate(Context context)
	{
		this.mContext = context;
	}

	public void showDialog(String title)
	{
		Calendar today = Calendar.getInstance();
		
		new DatePickerDialog(mContext, mDateSetListner, 
				today.get(Calendar.YEAR),
				today.get(Calendar.MONTH),
				today.get(Calendar.DAY_OF_MONTH)).show();;
	}
	
	public void setIsStart(boolean is)
	{
		this.mIsStart = is;
	}
	
	public boolean getIsStart()
	{
		return this.mIsStart;
	}
	
	public int getDay()
	{
		return mDay;
	}

	public void setDay(int mDay)
	{
		this.mDay = mDay;
	}

	public int getMonth()
	{
		return mMonth;
	}

	public void setMonth(int mMonth)
	{	
		this.mMonth = mMonth;
	}

	public int getYear()
	{
		return mYear;
	}

	public void setYear(int mYear)
	{
		this.mYear = mYear;
	}
}
