package siva.arlimi.event.detail.fragment;

import siva.arlimi.main.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class EventDateFragment extends Fragment
{
	private final CharSequence mStartDate;
	private final CharSequence mEndDate;
	
	public EventDateFragment(CharSequence start, CharSequence end)
	{
		mStartDate = start;
		mEndDate = end;
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View root = 
				inflater.inflate(R.layout.event_detail_date, container, false);
		
		TextView startDate =
				(TextView) root.findViewById(R.id.event_detail_start_date);
		TextView endDate =
				(TextView) root.findViewById(R.id.event_detail_end_date);
		
		startDate.setText(mStartDate);
		endDate.setText(mEndDate);
		
		return root;
	}
	

}
