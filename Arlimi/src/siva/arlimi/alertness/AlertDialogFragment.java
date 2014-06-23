package siva.arlimi.alertness;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

public class AlertDialogFragment extends DialogFragment
{
	private Dialog mDialog;
	
	public AlertDialogFragment()
	{
		super();
		 mDialog = null;
	}
	
	public void setDialog(Dialog dialog)
	{
		mDialog = dialog;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		return mDialog;
	}

}
