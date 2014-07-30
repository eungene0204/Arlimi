package siva.arlimi.widget;

import siva.arlimi.activity.R;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class EventCardHeader extends LinearLayout 
{
	public static final String TAG = "EventCardHeader";
	
	private TextView mBusinessNameTextView;
	private ImageView mPlusImg;
	
	public EventCardHeader(Context context)
	{
		super(context);
	}
	
	public EventCardHeader(Context context, AttributeSet attrs, int defStyle,OnTouchListener listener )
	{
		super(context,attrs,defStyle);
		init(context,defStyle, listener);
	}

	private void init(Context context, int defStyle, OnTouchListener listener)
	{
		setOrientation(LinearLayout.HORIZONTAL);
		setLayoutParams(getParams());
		setGravity(Gravity.RIGHT);
	
		mBusinessNameTextView = new TextView(context, null, defStyle);
		mBusinessNameTextView.setTextAppearance(context, defStyle);
		
		mPlusImg = new ImageView(context);
		mPlusImg.setImageResource(R.drawable.plus);
		mPlusImg.setClickable(true);
		mPlusImg.setOnTouchListener(listener);
	
		addView(mBusinessNameTextView,getParams());
		addView(mPlusImg,getParams());
	}
	
	public LinearLayout.LayoutParams getParams()
	{
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		
		
		return params;
	}
	
	public void setBusinessName(String name)
	{
		mBusinessNameTextView.setText(name);
	}
	
	public String getBusinessName()
	{
		return mBusinessNameTextView.getText().toString();
	}


}
