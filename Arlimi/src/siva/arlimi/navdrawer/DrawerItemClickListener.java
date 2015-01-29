package siva.arlimi.navdrawer;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class DrawerItemClickListener implements ListView.OnItemClickListener
{

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id)
	{
		selecItem(position);
		
	}

	private void selecItem(int position)
	{
		
	}
	

}
