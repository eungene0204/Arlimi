package siva.arlimi.widget;

import siva.arlimi.activity.R;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.LinearLayout;

public class EventCardWidget extends LinearLayout
{
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
				
		mCardHeader = new EventCardHeader(context, null, R.style.card_header);
		mCardBody = new EventCardBody(context, null, R.style.card_body);
	
		addView(mCardHeader,getParams());
		addView(mCardBody, getParams());
	}
	
	public LinearLayout.LayoutParams getParams()
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
	
	

}
