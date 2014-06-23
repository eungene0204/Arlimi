package siva.arlimi.geofence;

import com.google.android.gms.location.Geofence;

public class ArlimiGeofence
{
	private final String mId;
	private final double mLatitude;
	private final double mLongitude;
	private final float mRadius;
	private long mExpirationDuration;
	private int mTrasitionType;
	
	public ArlimiGeofence(
			String id,
			double latitude,
			double longitude,
			float radius,
			long expirationDuration,
			int transitonType
			)
	{
		this.mId = id;
		this.mLatitude = latitude;
		this.mLongitude = longitude;
		this.mRadius = radius;
		this.mExpirationDuration = expirationDuration;
		this.mTrasitionType = transitonType;
	}
	
	public long getmExpirationDuration()
	{
		return mExpirationDuration;
	}

	public void setmExpirationDuration(long mExpirationDuration)
	{
		this.mExpirationDuration = mExpirationDuration;
	}

	public int getmTrasitionType()
	{
		return mTrasitionType;
	}

	public void setmTrasitionType(int mTrasitionType)
	{
		this.mTrasitionType = mTrasitionType;
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
	
	public Geofence toGeofence()
	{
		return new Geofence.Builder()
		.setRequestId(getmId())
		.setTransitionTypes(mTrasitionType)
		.setCircularRegion(getmLatitude(), getmLongitude(), getmRadius())
		.setExpirationDuration(mExpirationDuration)
		.build();
	}


}
