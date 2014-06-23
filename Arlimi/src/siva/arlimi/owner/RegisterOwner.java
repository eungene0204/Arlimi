package siva.arlimi.owner;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;

import siva.arlimi.networktask.NetworkTask;

public class RegisterOwner extends NetworkTask
{
	public final static String OWNER_REGISTRATION_ADDR = 
			"http://192.168.0.21:8080/SIVA_Arlimi/Registration/Owner.jsp";
	
	public final static String SUCCESS_REGISTRATION = "가입이 완료되었습니다.";

	public RegisterOwner(OwnerRegistrationActivity ownerRegistrationActivity)
	{
		// TODO Auto-generated constructor stub
		super(ownerRegistrationActivity);
	}

	public void sendData(HttpURLConnection conn, String[] params) throws IOException
	{
		//Get Info from parameters
		String id = params[1].toString();
		String pass =params[2].toString();
		String ownerName =params[3].toString();
		String address =params[4].toString();
		String mobile = params[5].toString();
		String phone = params[6].toString();
		String email = params[7].toString();
		String category = params[8].toString();
		String restaurantName = params[9].toString();

		//Send owner info
		String regData = URLEncoder.encode("ID", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");
		regData += "&" + URLEncoder.encode("PASS", "UTF-8") + "=" + URLEncoder.encode(pass, "UTF-8");
		regData += "&" + URLEncoder.encode("OWNER_NAME", "UTF-8") + "=" + URLEncoder.encode(ownerName, "UTF-8");
		regData += "&" + URLEncoder.encode("ADDRESS", "UTF-8") + "=" + URLEncoder.encode(address, "UTF-8");
		regData += "&" + URLEncoder.encode("MOBILE", "UTF-8") + "=" + URLEncoder.encode(mobile, "UTF-8");
		regData += "&" + URLEncoder.encode("PHONE", "UTF-8") + "=" + URLEncoder.encode(phone, "UTF-8");
		regData += "&" + URLEncoder.encode("EMAIL", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8");
		regData += "&" + URLEncoder.encode("CATEGORY", "UTF-8") + "=" + URLEncoder.encode(category, "UTF-8");
		regData += "&" + URLEncoder.encode("RESTAURANT_NAME", "UTF-8") + "=" + URLEncoder.encode(restaurantName, "UTF-8");

		//Send data and get response
		writeData(conn, regData);
	//	getResponse();
	}

}
