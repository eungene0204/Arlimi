package siva.arlimi.alertness;

import java.net.HttpURLConnection;

import siva.arlimi.networktask.CheckTheSameId;
import siva.arlimi.networktask.SearchAddress;
import siva.arlimi.owner.RegisterOwner;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class AlertManager 
{
	private Context mContext;
	private String mParam;
	private int mResCode;
	
	public void setmContext(Context mContext)
	{
		this.mContext = mContext;
	}

	public void setmParam(String mParam)
	{
		this.mParam = mParam;
	}

	public void setmResCode(int mResCode)
	{
		this.mResCode = mResCode;
	}

	public void alertDialg(String result) 
	{
		String msg ="";
		try
		{
			AlertDialog.Builder dlg = new AlertDialog.Builder(mContext);
			dlg.setTitle("¾Ë¸³´Ï´Ù");
			
			if( CheckTheSameId.CHECK_THE_SAME_ID_ADDR == mParam && mResCode == HttpURLConnection.HTTP_OK)
			{
				msg = CheckTheSameId.VALID_ID;

				if(result.equalsIgnoreCase("TRUE")) msg = CheckTheSameId.INVALID_ID;
				dlg.setMessage(msg);
				dlg.setPositiveButton("´Ý±â", new DialogInterface.OnClickListener()
				{

					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						// TODO Auto-generated method stub

					}
				});
				dlg.show();
			}
			else if(RegisterOwner.OWNER_REGISTRATION_ADDR == mParam && mResCode == HttpURLConnection.HTTP_OK)
			{
				msg = RegisterOwner.SUCCESS_REGISTRATION;

				dlg.setMessage(msg);
				dlg.setPositiveButton("´Ý±â", new DialogInterface.OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						// TODO Auto-generated method stub

					}
				});
				dlg.show();
			}
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
