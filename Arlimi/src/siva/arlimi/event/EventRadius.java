package siva.arlimi.event;

import java.io.Serializable;

import siva.arlimi.activity.R;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class EventRadius implements OnSeekBarChangeListener, Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3697250586977626340L;
	public static final String ONE_KM = "1km";
	public static final String TWO_KM = "2km";
	public static final String THREE_KM = "3km";
	public static final String FOUR_KM = "4km";
	public static final String FIVE_KM = "5km";
	
	private Activity mContext;
	
	private SeekBar mSeekBar;
	private TextView mRadiusTextView;
	
	public EventRadius(Context context)
	{
		mContext = (Activity)context;
		
		mSeekBar = (SeekBar) mContext.findViewById(R.id.radius_seekbar);
		mRadiusTextView = (TextView) mContext.findViewById(R.id.radius_textview);
		
		mSeekBar.setOnSeekBarChangeListener(this);
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser)
	{
		switch(progress)
		{
		
		case 1:
			mRadiusTextView.setText(ONE_KM);
			break;
		case 2:
			mRadiusTextView.setText(TWO_KM);
			break;
		case 3:
			mRadiusTextView.setText(THREE_KM);
			break;
		case 4:
			mRadiusTextView.setText(FOUR_KM);
			break;
		case 5:
			mRadiusTextView.setText(FIVE_KM);
			break;
			
			default:
				break;
		}
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar)
	{
		// TODO Auto-generated method stub
		
	}

}
