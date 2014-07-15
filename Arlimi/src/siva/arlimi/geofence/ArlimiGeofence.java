package siva.arlimi.geofence;

import com.google.android.gms.location.Geofence;

public class ArlimiGeofence
{
	private final String mId;
	private final double mLatitude;
	private final double mLongitude;
	private final float mRadius;
	private long mExpirationDuration;
	private int mTransitionType;
	
	
	
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

	public double getmLatitude()
	{
		return mLatitude;
	}

	public double getmLongitude()
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
				.setCircularRegion(getmLatitude(), getmLongitude(), getmRadius())
				.setExpirationDuration(mExpirationDuration)
				.build();
	}

}
