package siva.arlimi.widget;

import siva.arlimi.activity.R;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class EventCardHeader extends LinearLayout implements OnTouchListener
{
	public static final String TAG = "EventCardHeader";
	
	private TextView mBusinessNameTextView;
	
	public EventCardHeader(Context context)
	{
		super(context);
	}
	
	public EventCardHeader(Context context, AttributeSet attrs, int defStyle)
	{
		super(context,attrs,defStyle);
		init(context,defStyle);
	}

	private void init(Context context, int defStyle)
	{
		setOrientation(LinearLayout.HORIZONTAL);
		setLayoutParams(getParams());
	
		mBusinessNameTextView = new TextView(context, null, defStyle);
		mBusinessNameTextView.setTextAppearance(context, defStyle);
		ImageView plusImg = new ImageView(context);
		plusImg.setImageResource(R.drawable.plus);
		plusImg.setClickable(true);
		plusImg.setOnTouchListener(this);
		
		addView(mBusinessNameTextView,getParams());
		addView(plusImg,getParams());
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

	@Override
	public boolean onTouch(View v, MotionEvent event)
	{
		// TODO Auto-generated method stub
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
