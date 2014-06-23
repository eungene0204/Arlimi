package siva.arlimi.person;

import java.io.Serializable;

import android.widget.ImageView;

import com.facebook.widget.ProfilePictureView;

public class Person implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3956499962326088800L;
	
	private String mName;
	private String mEmail;
	private ImageView mProfileImg;
	transient ProfilePictureView mFacebookProfilePic;
	
	
	boolean mIsFacebookUser = false;
	
	public Person()
	{
	}
	
	public void setProfileImg(ImageView imageView)
	{
		mProfileImg = imageView;
	}
	
	public void setFacebookProfileImg(ProfilePictureView profileImg)
	{
		mFacebookProfilePic = profileImg;
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

}
