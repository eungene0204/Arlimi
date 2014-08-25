package siva.arlimi.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class EventCardList extends LinearLayout
{
	
	public EventCardList(Context context)
	{
		super(context);
		init();
	}

	public EventCardList(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		init();
	}
	
	private void init()
	{
		setOrientation(LinearLayout.VERTICAL);
	}

}
