package siva.arlimi.facebook;

import java.io.Serializable;

public class FaceBookUserInfo implements Serializable
{
	
	private static final long serialVersionUID = 1L;

	public final static String 	FB_USER_INFO ="FACEBOOK_USER_INFO";
	
	private String mUserName;
	private String mEmail;
	
	public String getUserName()
	{
		return mUserName;
	}
	public void setUserName(String mUserName)
	{
		this.mUserName = mUserName;
	}
	public String getEmail()
	{
		return mEmail;
	}
	public void setEmail(String mEmail)
	{
		this.mEmail = mEmail;
	}
	

}
