package siva.arlimi.event.activity;


import siva.arlimi.event.widget.EventDate;
import siva.arlimi.event.widget.EventDescription;
import siva.arlimi.main.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class EventDetail extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.event_detail);
		
		init();
		
	}

	private void init()
	{
		LinearLayout container = 
				(LinearLayout) findViewById(R.id.event_detail_container);
		
		
		
	}

	/*
	private void initMapFragment(EventMap mapFragment)
	{
		LinearLayout.LayoutParams params = 
				new LinearLayout.LayoutParams(
						LinearLayout.LayoutParams.MATCH_PARENT,
						LinearLayout.LayoutParams.WRAP_CONTENT);
		
		params.gravity = Gravity.CENTER_HORIZONTAL;
		
		params.setMargins(20, 20, 20, 0);
		
		mapFragment.getView().setLayoutParams(getParams());
	} */

	private LinearLayout.LayoutParams getParams()
	{
		LinearLayout.LayoutParams params = 
				 new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		
		return params;
	}

}
