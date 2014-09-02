package siva.arlimi.calendar;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;

public class EventCalendar implements OnClickListener
{
	public static final String TAG = "EventCalendar";
	
	private Context mContext;
	private DatePicker mDatePicker;
	private View mView;
	
	private int mYear;
	private int mMonth;
	private int mDay;
	
	OnDateChangedListener mDateListenr = new OnDateChangedListener()
	{
		
		@Override
		public void onDateChanged(DatePicker view, int year, int monthOfYear,
				int dayOfMonth)
		{
			Log.i(TAG, "Year: " + year + " " + "month: " + " "  + monthOfYear
					+ "day: " +  dayOfMonth);
		}
	};
	
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
	
	public EventCalendar(Context context)
	{
		mContext = context;
	
		LayoutInflater inflater = (LayoutInflater)
				mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//		mView = inflater.inflate(R.layout.fragment_calendar, null); 
	
		Calendar today = Calendar.getInstance();
		
/*		mDatePicker = (DatePicker) mView.findViewById(R.id.myDatePicker);
		mDatePicker.init(
				today.get(Calendar.YEAR),
				today.get(Calendar.MONTH),
				today.get(Calendar.DAY_OF_MONTH),
				mDateListenr
				); */
	}
	
	public void setYear(int year)
	{
		this.mYear = year;
	}
	
	public void setMonth(int month)
	{
		this.mMonth = month;
	}
	
	public void setDay(int day)
	{
		this.mDay = day;
	}
	
	public int getYear()
	{
		return this.mYear;
	}
	
	public int getMonth()
	{
		return this.mMonth;
	}
	
	public int getDay()
	{
		return this.mDay;
	}
	
	public void showDialog(String title)
	{
		Calendar today = Calendar.getInstance();
		
		new DatePickerDialog(mContext, mDateSetListner, 
				today.get(Calendar.YEAR),
				today.get(Calendar.MONTH),
				today.get(Calendar.DAY_OF_MONTH)).show();;
		
		/*
	
		LayoutInflater inflater = (LayoutInflater)
				mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mView = inflater.inflate(R.layout.fragment_calendar, null); 
		
		new AlertDialog.Builder(mContext)
						.setView(mView)
						.setTitle(title)
						.setPositiveButton(R.string.confirm, null)
						.setNegativeButton(R.string.cancel, null)
						.show(); */
		
	}

	@Override
	public void onClick(DialogInterface dialog, int which)
	{
		// TODO Auto-generated method stub
		
	}

}
