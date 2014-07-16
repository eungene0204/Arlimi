package siva.arlimi.geofence;

import com.google.android.gms.location.Geofence;

public class ArlimiGeofence
{
	private String mId;
	private double mLatitude;
	private double mLongitude;
	private float mRadius;
	private long mExpirationDuration;
	private int mTransitionType;
	
	public ArlimiGeofence()
	{
	}
	
	public ArlimiGeofence(
			String id,
			double latitude,
			double longitude,
			float radius,
			long expiration,
			int transition)
	{
		this.mId = id;
		this.mLatitude = latitude;
		this.mLongitude = longitude;
		this.mRadius = radius;
		this.mExpirationDuration = expiration;
		this.mTransitionType = transition;
	}
	
	public void setLatitude(double lat)
	{
		this.mLatitude = lat;
	}
	
	public void setLongitude(double lon)
	{
		this.mLongitude = lon;
	}

	public long getmExpirationDuration()
	{
		return mExpirationDuration;
	}

	public void setmExpirationDuration(long mExpirationDuration)
	{
		this.mExpirationDuration = mExpirationDuration;
	}

	public int getmTransitionType()
	{
		return mTransitionType;
	}

	public void setmTransitionType(int mTransitionType)
	{
		this.mTransitionType = mTransitionType;
	}

	public String getmId()
	{
		return mId;
	}

	public double getLatitude()
	{
		return mLatitude;
	}

	public double getLongitude()
	{
		return mLongitude;
	}

	public float getmRadius()
	{
		return mRadius;
	}
	
	/*
	 * Create Geofence Object
	 */
	
	public Geofence toGeofence()
	{
		return new Geofence.Builder()
				.setRequestId(getmId())
				.setTransitionTypes(getmTransitionType())
				.setCircularRegion(getLatitude(), getLongitude(), getmRadius())
				.setExpirationDuration(mExpirationDuration)
				.build();
	}

}
