package siva.arlimi.event.activity;



import siva.arlimi.auth.session.SessionManager;
import siva.arlimi.event.Event;
import siva.arlimi.event.EventRadius;
import siva.arlimi.event.util.EventUtils;
import siva.arlimi.main.R;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


public class EventRegistrationActivity extends FragmentActivity implements OnClickListener
{
	public static final String TAG = "RegisterEventActivity";
	
	private static final int SELECT_PICTURE = 1;

	private Button mEventStartDateBtn;
	private Button mEventStartTimeBtn;
	private Button mEventEndTimeBtn;
	private Button mEventSendBtn;
	private Button mAccessImgBtn;
	private EditText mContentsEditTxt;
	private ImageView mImageGallery;
	
	private Event mEvent; 
	
	private String mSelecedPath;
	
	private SessionManager mSession;
	
	private BroadcastReceiver mEventRegResultReceiver = new BroadcastReceiver()
	{
		@Override
		public void onReceive(Context context, Intent intent)
		{
			onEventRegResult(intent);
		}
	
	};
	
	private void onEventRegResult(Intent intent)
	{
		String result = intent.getStringExtra(EventUtils.KEY_REG_RESULT);
		
		Log.i(TAG, "result :" + result);
		
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		Log.i(TAG, "onCreate");
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_event);
		
		mSession = new SessionManager(getApplicationContext());
		
		//Event
		mEvent = new Event(this);
		
		//EditText
		mContentsEditTxt = (EditText) findViewById(R.id.regEvent_event_contents_et);
	
		//Buttons
		mEventStartTimeBtn = (Button) findViewById(R.id.event_start_time_btn);
		mEventStartTimeBtn.setOnClickListener(this);
		
		mEventEndTimeBtn = (Button) findViewById(R.id.event_end_time_btn);
		mEventEndTimeBtn.setOnClickListener(this);
		
		mEventSendBtn = (Button) findViewById(R.id.event_send_btn);
		mEventSendBtn.setOnClickListener(this);
		
		mEventStartDateBtn = (Button) findViewById(R.id.event_start_date_btn); 
		mEventStartDateBtn.setOnClickListener(this);
		
		mEventEndTimeBtn = (Button) findViewById(R.id.event_end_date_btn);
		mEventEndTimeBtn.setOnClickListener(this); 
	
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
	
		//Broadcast 
		IntentFilter eventRegFilter = new IntentFilter(EventUtils.ACTION_REG_RESULT);
		registerReceiver(mEventRegResultReceiver, eventRegFilter);
	}
	
	@Override
	protected void onPause()
	{
		super.onPause();
		
		unregisterReceiver(mEventRegResultReceiver);
	}

	@Override
	public void onClick(View v)
	{
		switch(v.getId())
		{
		case  R.id.event_start_time_btn:
			mEvent.setIsStart(true);
			mEvent.showTimePicker();
			break;
			
		case R.id.event_end_time_btn:
			mEvent.setIsStart(false);
			mEvent.showTimePicker();
			break;
				
		case R.id.event_start_date_btn:
			mEvent.setIsStart(true);
			mEvent.showDatePicker(); 
			break; 
			
		case R.id.event_end_date_btn:
			mEvent.setIsStart(false);
			mEvent.showDatePicker(); 
			break;
			
		case R.id.event_send_btn:
			registerEvent(mEvent);
			break;
		
		default:
			break;
		}
	}

	
	private void accessImageGallery()
	{
		Intent intent = new Intent(Intent.ACTION_PICK,
				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(intent, SELECT_PICTURE);
	}

	private void registerEvent(final Event event)  
	{
		event.setContents(mContentsEditTxt.getText().toString());

		Intent intent = EventUtils.getEventRegistrationIntent(this);
				
		intent.putExtra(EventUtils.KEY_NAME, mSession.getUserDetails().getName());
		intent.putExtra(EventUtils.KEY_EMAIL, mSession.getUserDetails()
				.getEmail());
		intent.putExtra(EventUtils.EVENT_CONTENTS, event.getContents());
		intent.putExtra(EventUtils.EVENT_START_DATE, event.getEventStartDate()
				.toString());
		intent.putExtra(EventUtils.EVENT_END_DATE, event.getEventEndDate()
				.toString());
		intent.putExtra(EventUtils.EVENT_START_TIME, event.getEventStartTime()
				.toString());
		intent.putExtra(EventUtils.EVENT_END_TIME, event.getEventEndTime()
				.toString());		
		
		startService(intent);

	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if(resultCode == RESULT_OK)
		{
			if(requestCode ==  SELECT_PICTURE)
			{
				Uri selectedImageUri = data.getData();
				mSelecedPath = getPath(selectedImageUri);
				
				mImageGallery.setImageBitmap(BitmapFactory.decodeFile(mSelecedPath));
			}
			
		}
	}

	private String getPath(Uri selectedImageUri)
	{
		if(selectedImageUri == null)
		{
			return null;
		}
	
		String[] projection = {MediaStore.Images.Media.DATA };
		Cursor cursor = getContentResolver().query(selectedImageUri, projection,
				null, null, null);
		cursor.moveToFirst();
		String path = cursor.getString(cursor.getColumnIndex(projection[0]));
	
		cursor.close();
		return path;
	}

}
