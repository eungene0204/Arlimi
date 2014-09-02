package siva.arlimi.event.widget;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

public class EventDate extends LinearLayout
{
	private TextView mStartDate;
	private TextView mEndDate;
	
	public EventDate(Context context)
	{
		super(context);
		init(context);
	}

	private void init(Context context)
	{
		setOrientation(LinearLayout.VERTICAL);
		setLayoutParams(getParams());
		setBackgroundColor(Color.BLACK);
		
		mStartDate = new TextView(context);
		mEndDate = new TextView(context);
		
		mStartDate.setText("Start at 2014-01-01");
		mEndDate.setText("End at 2014-12-31");
		mStartDate.setTextSize(25);
		mEndDate.setTextSize(25);
		
		addView(mStartDate);
		addView(mEndDate);
		
	}

	private LinearLayout.LayoutParams getParams()
	{
		LinearLayout.LayoutParams params =
				new LayoutParams(LayoutParams.MATCH_PARENT,
						LayoutParams.MATCH_PARENT);
		
		params.gravity = Gravity.CENTER_HORIZONTAL;
		
		
		params.setMargins(20, 20, 20, 0);
		return params;
	}
	
	
}
