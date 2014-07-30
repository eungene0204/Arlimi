package siva.arlimi.widget;

import siva.arlimi.activity.R;
import siva.arlimi.event.Event;
import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;

public class EventCardWidget extends LinearLayout implements OnTouchListener
{
	public static final String TAG = "EventCardWidget";
	
	private EventCardHeader mCardHeader;
	private EventCardBody mCardBody;
	
	
	public EventCardWidget(Context context)
	{
		super(context);
		init(context);
	}
	
	public void init(Context context)
	{
		setOrientation(LinearLayout.VERTICAL);
		setBackgroundResource(R.drawable.search_bg_shadow);
				
		mCardHeader = new EventCardHeader(context, null, R.style.card_header, this);
		mCardBody = new EventCardBody(context, null, R.style.card_body);
	
		addView(mCardHeader,getParams());
		addView(mCardBody, getParams());
	}
	
	public LayoutParams getParams()
	{
		LinearLayout.LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		
		params.setMargins(3, 3, 3, 3);
		
		return params;
	}
	
	public void setBusinessName(String name)
	{
		mCardHeader.setBusinessName(name);
	}
	
	public void setEventContents(String contents)
	{
		mCardBody.setContents(contents);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event)
	{
		switch(event.getAction() )
		{
		
		case MotionEvent.ACTION_DOWN:
			Log.i(TAG, "Plus touch");
			return true;
			
			default:
				break;
		}
		
		return false;
	}
	

}
