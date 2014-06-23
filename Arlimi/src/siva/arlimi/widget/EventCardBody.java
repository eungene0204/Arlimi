package siva.arlimi.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

public class EventCardBody extends LinearLayout
{
	private TextView mContentsTextView;

	public EventCardBody(Context context)
	{
		super(context);
	}
	
	public EventCardBody(Context context, AttributeSet attrs, int defStyle)
	{
		super(context,attrs,defStyle);
		init(context, defStyle);
		
	}
	private void init(Context context, int defStyle)
	{
		setOrientation(LinearLayout.VERTICAL);
		setLayoutParams(getParams());
		
	
		mContentsTextView = new TextView(context, null, defStyle); 
		mContentsTextView.setTextAppearance(context, defStyle);
		
		addView(mContentsTextView, getParams());
		
	}
		
	public LinearLayout.LayoutParams getParams()
	{
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		
		return params;
	}
	
	public void setContents(String contents)
	{
		mContentsTextView.setText(contents);
	}
	
	public String getContents()
	{
		return mContentsTextView.getText().toString();
	}

}
