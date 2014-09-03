package siva.arlimi.event.activity;

import siva.arlimi.event.detail.fragment.CallFragment;
import siva.arlimi.event.detail.fragment.EventDateFragment;
import siva.arlimi.event.detail.fragment.EventDescriptionFragment;
import siva.arlimi.event.detail.fragment.EventMapFragment;
import siva.arlimi.event.detail.fragment.EventShareFragment;
import siva.arlimi.main.R;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.gms.internal.ft;

public class EventDetailActivity extends FragmentActivity
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
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		
		EventDescriptionFragment eventDesc = 
				new EventDescriptionFragment(R.drawable.my, "Sale!!");
		
		EventMapFragment map = new EventMapFragment();
		EventDateFragment date = new EventDateFragment("2014-01-01",
				"2014-07-06");
		CallFragment call = new CallFragment();
		EventShareFragment share = new EventShareFragment();
		
		ft.add(R.id.event_detail_container, eventDesc);
		ft.add(R.id.event_detail_container, date);
		ft.add(R.id.event_detail_container, map);
		ft.add(R.id.event_detail_container, share);
		ft.add(R.id.event_detail_container, call);
		
		ft.commit();
		
	}

}
