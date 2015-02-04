package siva.arlimi.networktask;

public interface NetworkURL
{
	public static final String LOCAL_HELLOWORLD =
			"http://192.168.25.60:8888/siva_arlimi_appengine";
	
	public static final String LOCAL_REGISTRATIONEVENT = 
			"http://123.143.179.44:8080/event/eventregistration";
	
	public static final String APP_ENGINE_REGISTRATIONEVENT =
			"http://1-dot-savvy-primacy-635.appspot.com/event/eventregistration";
	
	public static final String READ_EVENT_LIST_FROM_DB =
			"http://123.143.179.44:8080/event/readEventList";
	
	public static final String READ_EVENT_BY_ID =
			"http://123.143.179.44:8080/event/readEventByID";
	
	public static final String HOME_EMAIL_USER_REGISTRATION =
			"http://192.168.0.1:8888/user/reg/emailuser";
		
	public static final String FACEBOOK_USER_REGISTRATION =
			"http://192.168.0.1:8888/user/reg/facebookuser";
	
	public static final String FACEBOOK_USER_LOGIN =
			"http://192.168.0.1:8888/user/login/facebookuser";
	
	public static final String EMAIL_USER_LOGIN =
			"http://192.168.0.1:8888/user/login/emailuser";
	
	public static final String SHOP_REGISTRATION =
			"http://192.168.0.1:8888/shop/registration";
	
	public static final String EVENT_REGISTRATION =
			"http://192.168.0.1:8888/event/registration";
	
	public static final String SERVICE_RECOMMENDED_LIST =
			"http://192.168.0.1:8888/service/registration";
	
	public static final String SERVICE_READ_ALL_LIST =
			"http://192.168.0.1:8888/service/readAllServiceList";
	
	
  
	
}
