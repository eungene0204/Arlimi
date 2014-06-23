package siva.arlimi.owner;

import siva.arlimi.activity.R;
import siva.arlimi.activity.R.id;
import siva.arlimi.activity.R.layout;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class OwnerManagementActivity extends Activity implements OnClickListener
{
	Button mUpLoadButton;
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_owner_management);
	    
	    mUpLoadButton = (Button)findViewById(R.id.btn_owner_uploadmeal);
	    mUpLoadButton.setOnClickListener(this);
	
	}

	@Override
	public void onClick(View v)
	{
		switch(v.getId())
		{
		case R.id.btn_owner_uploadmeal:
			default:
				break;
		}
		
	}

}
