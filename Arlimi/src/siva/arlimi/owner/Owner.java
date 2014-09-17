package siva.arlimi.owner;



import siva.arlimi.person.Person;
import android.content.Context;

public class Owner extends Person 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5572176394051722634L;

	public static final String TAG = "Owner";
	
	private Context context;
	private String mId;
	private String mCatetory;
	private String mResName;
	private String mMobile;
	private int mFacebookId;
	
	
	public Owner()
	{
		super();
	}
	
	
	public void setFacebookId(int id)
	{
		this.mFacebookId = id;
	}
	
	public int getFacebookId()
	{
		return mFacebookId;
	}
	
	
	public String getmId()
	{
		return mId;
	}
	public void setmId(String mId)
	{
		this.mId = mId;
	}
	public String getmCatetory()
	{
		return mCatetory;
	}
	public void setmCatetory(String mCatetory)
	{
		this.mCatetory = mCatetory;
	}
	public String getmResName()
	{
		return mResName;
	}
	public void setmResName(String mResName)
	{
		this.mResName = mResName;
	}
	public String getmMobile()
	{
		return mMobile;
	}
	public void setmMobile(String mMobile)
	{
		this.mMobile = mMobile;
	}

}
