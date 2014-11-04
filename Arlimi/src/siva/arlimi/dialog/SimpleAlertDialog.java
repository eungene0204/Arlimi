package siva.arlimi.dialog;

import siva.arlimi.main.R;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class SimpleAlertDialog
{
	
	static public void showOneButtonDialog(Context context, String title, String message)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setPositiveButton(context.getResources().getString(R.string.dialog_confirm),
				new DialogInterface.OnClickListener()
				{
					
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						// TODO Auto-generated method stub
						
					}
				});
		
		AlertDialog alert = builder.create();
		alert.show();
	}

}
