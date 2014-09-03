package siva.arlimi.event.detail.fragment;

import siva.arlimi.main.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class EventDescriptionFragment extends Fragment
{
	private final int mResId;
	private final CharSequence mText;
	
	public EventDescriptionFragment(int id, CharSequence text)
	{
		mResId = id;
		mText = text;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		View root = inflater.inflate(R.layout.event_detail_desc,
				container, false);
	
		init(root);
		
		return root;
	}

	private void init(View root)
	{
		ImageView imageView = 
				(ImageView) root.findViewById(R.id.event_detail_desc_pic);
		TextView textView =
				(TextView) root.findViewById(R.id.event_detail_desc_tv);
		
		imageView.setImageResource(mResId);
		textView.setText(mText);
	}
	
	
	
}
