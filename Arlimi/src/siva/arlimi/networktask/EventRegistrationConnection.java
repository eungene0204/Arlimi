package siva.arlimi.networktask;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import siva.arlimi.event.Event;

public class EventRegistrationConnection extends NetworkConnection 
{
	/*

	@Override
	public void makeData(Object obj)
	{
		Event event = (Event)obj;
		
		String resName = "Woodsin";
		String contents = "KimChi soupp";//event.getContents();
		
		String data = null;
		
		try
		{
			data = URLEncoder.encode("RESNAME", "UTF-8") + "=" + URLEncoder.encode(resName,"UTF-8");
			data += "&" + URLEncoder.encode("EVENT_CONTENTS","UTF-8") + "=" + URLEncoder.encode(contents,"UTF-8");
		} 
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		
		setData(data);
	} */

}
