package siva.arlimi.user;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import siva.arlimi.auth.util.AuthUtil;

import android.widget.ImageView;

public class User implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3956499962326088800L;
	
	protected String mName;
	protected String mEmail;
	protected String mPassword;
	protected ImageView mProfileImg;
	
	public void setProfileImg(ImageView imageView)
	{
		mProfileImg = imageView;
	}
	
	
	public ImageView getProfileImage()
	{
		return mProfileImg;
	}
	
	public void setName(String name)
	{
		this.mName = name;
	}
	
	public void setEmail(String email)
	{
		this.mEmail = email;
	}
	
	public String getName()
	{
		return this.mName;
	}
	
	public String getEmail()
	{
		return this.mEmail;
	}
	
	public String get(String key)
	{
		if(key.equals(AuthUtil.KEY_EMAIL))
			return mEmail;
		else if(key.equals(AuthUtil.KEY_NAME))
			return mName;
		else if(key.equals(AuthUtil.KEY_PASSWORD))
			return mPassword;
		
		return "";
		
	}
	
	public Set<String> getKeys()
	{
		Set<String> set = new HashSet<String>();
	
		set.add(AuthUtil.KEY_EMAIL);
		set.add(AuthUtil.KEY_NAME);
		set.add(AuthUtil.KEY_PASSWORD);
		
		return set;
		
		
	}

}
