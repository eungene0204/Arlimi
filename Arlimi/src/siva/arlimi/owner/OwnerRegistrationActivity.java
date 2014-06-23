package siva.arlimi.owner;

import siva.arlimi.activity.FindAddressActivity;

import siva.arlimi.activity.R;
import siva.arlimi.activity.R.id;
import siva.arlimi.activity.R.layout;
import siva.arlimi.networktask.CheckTheSameId;
import siva.arlimi.networktask.NetworkTask;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class OwnerRegistrationActivity extends Activity implements View.OnClickListener 
{
	private Button mRegistrationButton;
	private Button mCheckIdButton;
	private Button mFindAddressButton;

	private EditText mNameEditText;
	private EditText mIdEditText;
	private EditText mPassEditText;
	private EditText mRestaurantNameEditText;
	private EditText mAddrEditText;
	private EditText mMobileEditText;
	private EditText mPhoneEditText;
	private EditText mEmailEditText;
	private EditText mCategoryEditText;


	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_owner_registration);

		//Buttons
		mRegistrationButton = (Button)findViewById(R.id.btn_new_register);
		mRegistrationButton.setOnClickListener(this);
		
		mCheckIdButton = (Button)findViewById(R.id.btn_thesameid_check);
		mCheckIdButton.setOnClickListener(this);
		
		mFindAddressButton = (Button)findViewById(R.id.btn_find_address);
		mFindAddressButton.setOnClickListener(this);
		
		//EditText
		mNameEditText = (EditText)findViewById(R.id.edittext_new_name);
		mIdEditText = (EditText)findViewById(R.id.edittext_new_id);
		mPassEditText = (EditText)findViewById(R.id.edittext_new_pass);
		mRestaurantNameEditText = (EditText)findViewById(R.id.edittext_new_restaurant_name);
		mAddrEditText = (EditText)findViewById(R.id.edittext_new_address);
		mMobileEditText = (EditText)findViewById(R.id.edittext_new_mobile);
		mPhoneEditText = (EditText)findViewById(R.id.edittext_new_phone);
		mEmailEditText = (EditText)findViewById(R.id.edittext_new_email);
		mCategoryEditText = (EditText)findViewById(R.id.edittext_new_category);

	}


	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.btn_new_register:
			NetworkTask newOwner = new RegisterOwner(this);
			newOwner.execute(getNewOwenerInfo());
			break;

		case R.id.btn_thesameid_check:	
			NetworkTask checkId = new CheckTheSameId(this);
			checkId.execute(getNewOwnerId());
			break;
			
		case R.id.btn_find_address:
			Intent intent = new Intent(OwnerRegistrationActivity.this,FindAddressActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}

	}

	public String[] getNewOwenerInfo()
	{
		String[] list = new String[10];
		list[0] = RegisterOwner.OWNER_REGISTRATION_ADDR;

		//Get Info from Login form
		String id = mIdEditText.getText().toString();
		String pass = mPassEditText.getText().toString();
		String ownerName = mNameEditText.getText().toString();
		String address = mAddrEditText.getText().toString();
		String mobile = mMobileEditText.getText().toString();
		String phone = mPhoneEditText.getText().toString();
		String email = mEmailEditText.getText().toString();
		String category = mCategoryEditText.getText().toString();
		String restaurantName = mRestaurantNameEditText.getText().toString();

		list[1] = id;
		list[2] = pass;
		list[3] = ownerName;
		list[4] = address;
		list[5] = mobile;
		list[6] = phone;
		list[7] = email;
		list[8] = category;
		list[9] = restaurantName;
		
		return list;
	}

	public String[] getNewOwnerId()
	{
		String[] checkId = new String[2];
		String id = mIdEditText.getText().toString();
		
		checkId[0] = CheckTheSameId.CHECK_THE_SAME_ID_ADDR;
		checkId[1] = id;
		
		return checkId;
	}

	
}
