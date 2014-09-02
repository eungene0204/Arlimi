package siva.arlimi.activity;

import org.json.JSONException;
import org.json.JSONObject;

import siva.arlimi.event.EventUtil;
import siva.arlimi.main.R;
import siva.arlimi.owner.Owner;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class BusinessRegistration extends Activity implements OnClickListener
{
	private EditText mBusiness_name_et;
	private EditText mBusiness_address_et;
	private EditText mBusiness_phone_num_et;
	
	private Button mRegistration_btn;
	
	private Owner mOwner;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_business_registration);
		
		//Get Owner
		mOwner = (Owner)getIntent().getSerializableExtra(Owner.TAG);
		
		
		mBusiness_name_et = (EditText) findViewById(R.id.business_name);
		mBusiness_address_et = (EditText) findViewById(R.id.business_address);
		mBusiness_phone_num_et = (EditText) findViewById(R.id.business_phone_number);
		
		mRegistration_btn = (Button) findViewById(R.id.business_registration_btn);
		mRegistration_btn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v)
	{
		switch(v.getId())
		{
			case R.id.business_registration_btn:
				registerOwner();
				break;
				
			default:
				break;
		}
		
	}

	private void registerOwner()
	{
		JSONObject json = new JSONObject();
		
		try
		{
			json.put(EventUtil.USER, mOwner.getName());
			json.put(EventUtil.EMAIL, mOwner.getEmail());
			json.put(EventUtil.SHOP_NAME, mBusiness_name_et.getText().toString());
			json.put(EventUtil.SHOP_ADDRESS, mBusiness_address_et.getText().toString());
			json.put(EventUtil.SHOP_PHONE_NUMBER, mBusiness_phone_num_et.getText().toString());
		} 
		catch (JSONException e)
		{
			e.printStackTrace();
		}
		catch (NullPointerException e)
		{
			e.printStackTrace();
		}
		
		System.out.println(json);
		
		
	}
	
	

}
