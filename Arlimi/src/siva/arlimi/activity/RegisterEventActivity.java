package siva.arlimi.activity;



import java.io.UnsupportedEncodingException;

import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import siva.arlimi.event.Event;
import siva.arlimi.event.EventRadius;
import siva.arlimi.event.EventUtil;
import siva.arlimi.networktask.NetworkConnection;
import siva.arlimi.networktask.NetworkURL;
import siva.arlimi.owner.Owner;
import android.content.Intent;
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


public class RegisterEventActivity extends FragmentActivity implements OnClickListener
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
	private EventRadius mEventRadius; 
	
	private String mEventContents;
	private String mSelecedPath;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		Log.i(TAG, "onCreate");
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_event);
		
//		mImageGallery = (ImageView)findViewById(R.id.img_gallery_imgView);
		
		//Event
		mEvent = new Event(this);
		Owner owner = (Owner)getIntent().getSerializableExtra(Owner.TAG);
		mEvent.setOwner(owner);
		
		Log.i(TAG, owner.getName());
		
		//Get EventDate, Owner
		//mEvent = new Event(this);
		//mEvent.setEventDate((EventDate)getIntent().getSerializableExtra(EventDate.TAG));
		//mEvent.setOwner((Owner)getIntent().getSerializableExtra(Owner.TAG));
	
		//Buttons
		mEventStartTimeBtn = (Button) findViewById(R.id.event_start_time_btn);
		mEventStartTimeBtn.setOnClickListener(this);
		
		mEventEndTimeBtn = (Button) findViewById(R.id.event_end_time_btn);
		mEventEndTimeBtn.setOnClickListener(this);
		
		mEventSendBtn = (Button) findViewById(R.id.event_send_btn);
		mEventSendBtn.setOnClickListener(this);
		
	//	mAccessImgBtn = (Button) findViewById(R.id.access_image_gallery_btn);
	//	mAccessImgBtn.setOnClickListener(this);
		
		mEventStartDateBtn = (Button) findViewById(R.id.event_start_date_btn); 
		mEventStartDateBtn.setOnClickListener(this);
		
		mEventEndTimeBtn = (Button) findViewById(R.id.event_end_date_btn);
		mEventEndTimeBtn.setOnClickListener(this); 
		
		//Radius Seekbar
		mEventRadius = new EventRadius(this);
		
		mContentsEditTxt = (EditText) findViewById(R.id.edittext_event_contents);
		
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
			
/*		case R.id.access_image_gallery_btn:
			accessImageGallery();
			break; */
		
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

	private void registerEvent(Event event)  
	{
		event.setContents(mContentsEditTxt.getText().toString());
		event.setRadius(mEventRadius);
	
		Log.i(TAG, "User: " + event.getOwner().getName());
		Log.i(TAG, "Email: " + event.getOwner().getEmail());
		Log.i(TAG, "EventContents: " + event.getContents());
		Log.i(TAG, "Event Start Date: " + event.getEventStartDate().getYear() +
				event.getEventStartDate().getMonth() + event.getEventStartDate().getDay());
		Log.i(TAG, "Event Start Time: " + event.getEventStartTime().getHour() +
				event.getEventStartTime().getMin());
		Log.i(TAG, "Event End Date: " + event.getEventEndDate().getYear() 
				+ event.getEventEndDate().getMonth() + event.getEventEndDate().getDay());
		Log.i(TAG, "Event End Time: " + event.getEventEndTime().getHour() +
				event.getEventEndTime().getMin());
		Log.i(TAG, "Radius: " + event.getRadius().getRadius() );
		
		JSONObject json = new JSONObject();
		
		
		try
		{
			
			json.put(EventUtil.USER, event.getOwner().getName());
			json.put(EventUtil.EMAIL, event.getOwner().getEmail());
			json.put(EventUtil.EVENT_CONTENTS, event.getContents());
			json.put(EventUtil.EVENT_START_DATE, event.getEventStartDate().toString());
			json.put(EventUtil.EVENT_END_DATE, event.getEventEndDate().toString());
			json.put(EventUtil.EVENT_START_TIME, event.getEventStartTime().toString());
			json.put(EventUtil.EVENT_END_TIME, event.getEventEndTime().toString());
			json.put(EventUtil.EVENT_RADIUS, event.getRadius().getRadius());
			
			System.out.println("JSON Object: " + json);

			String jsonString = json.toString();

			System.out.println("JSON String: " + jsonString);
			
			NetworkConnection conn = new NetworkConnection();
			conn.setURL(NetworkURL.LOCAL_REGISTRATIONEVENT);
			conn.setData(json);
			conn.execute();
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
		catch (NullPointerException e)
		{
			e.printStackTrace();
		}
		
		/*
		EventRegistrationConnection conn = new EventRegistrationConnection();
		String[] param = new String[1];
		event.setContents(mContentsEditTxt.getText().toString());
		
		//param[0] = "http://192.168.0.21:8088/SIVA_Arlimi_Test_Server/event/eventregistration";
		param[0] = "http://192.168.0.21:8088/SIVA_Arlimi_Test_Server/event/eventregistration";
		
		
	//	conn.makeData(event);
		conn.execute(param); */
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
