package siva.arlimi.user;

import android.content.Context;

import com.facebook.widget.ProfilePictureView;

public class FacebookUser extends User 
{
	private static final long serialVersionUID = -6110899389803200528L;
	
	private transient ProfilePictureView mProfilePicture;
	private boolean isValid = false;

	public FacebookUser(Context context)
	{
		mProfilePicture = new ProfilePictureView(context); 
	}
	
	
	public void setProfilePicture(ProfilePictureView pic)
	{
		if( null != pic)
			this.mProfilePicture = pic;
	}
	
	public ProfilePictureView getProfilePicture()
	{
		return mProfilePicture;
	} 
	
	public void setValid(boolean valid)
	{
		this.isValid = valid;
	}
	
	public boolean isValid()
	{
		return isValid;
	}

}
