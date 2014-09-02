package siva.arlimi.event.widget;

import siva.arlimi.main.R;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class EventDescription extends LinearLayout
{
	public static final String TAG ="EventDescription";
	
	private ImageView mImageView;
	private TextView mTextView;

	public EventDescription(Context context)
	{
		super(context);
		init(context);
	}

	private void init(Context context)
	{
	
		setOrientation(LinearLayout.VERTICAL);
		setLayoutParams(getParams());
		setPadding(20, 20, 20, 20);
		setBackgroundColor(Color.TRANSPARENT | Color.BLACK);
		
		mImageView = new ImageView(context);
		mImageView.setImageResource(R.drawable.my);
		mImageView.setLayoutParams(getParams());
		mTextView = new TextView(context);
		mTextView.setText("Sale everything");
		mTextView.setTextSize(20);
		
		addView(mImageView);
		addView(mTextView);
	}
	
	private LinearLayout.LayoutParams getParams()
	{
		LinearLayout.LayoutParams params =
				new LayoutParams(LayoutParams.MATCH_PARENT,
						LayoutParams.WRAP_CONTENT);
		
		params.gravity = Gravity.CENTER_HORIZONTAL;
		
		params.setMargins(20, 20, 20, 0);
		
		return params;
	}
	
	
	
	
	

}
