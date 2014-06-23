package siva.arlimi.networktask;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;

import siva.arlimi.owner.OwnerRegistrationActivity;

public class CheckTheSameId extends NetworkTask
{
	final public static String CHECK_THE_SAME_ID_ADDR = "http://192.168.0.21:8080/SIVA_Arlimi/Registration/IDCheck.jsp";
	final public static String INVALID_ID = "사용할 수 없는 사용자 ID입니다.";
	final public static String VALID_ID = "사용할 수 있는 사용자 ID입니다.";
	
	public CheckTheSameId(OwnerRegistrationActivity ownerRegistrationActivity)
	{
		// TODO Auto-generated constructor stub
		super(ownerRegistrationActivity);
	}

	@Override
	public void sendData(HttpURLConnection conn, String[] params)
			throws UnsupportedEncodingException, IOException
	{
		//Get Info from parameters
		String id = params[1].toString();
		
		String data = URLEncoder.encode("ID", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");
		data += "&" + URLEncoder.encode("OWNERS", "UTF-8") + "=" + URLEncoder.encode("OWNERS", "UTF-8");

		writeData(conn, data);			
	//	getResponse();
	}
	

}
