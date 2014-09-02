package siva.arlimi.event;

import java.io.Serializable;

import siva.arlimi.main.R;
import android.app.Activity;
import android.content.Context;
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
	
	private int mRadius;
	
	public EventRadius(Context context)
	{
		mContext = (Activity)context;
		
		mSeekBar = (SeekBar) mContext.findViewById(R.id.radius_seekbar);
		mRadiusTextView = (TextView) mContext.findViewById(R.id.radius_textview);
		
		mSeekBar.setOnSeekBarChangeListener(this);
	}
	
	public void setRadius(int radius)
	{
		this.mRadius = radius;
	}
	
	public int getRadius()
	{
		return mRadius;
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser)
	{
		setRadius(progress);
		
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
