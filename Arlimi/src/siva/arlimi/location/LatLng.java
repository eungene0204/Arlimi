package siva.arlimi.location;

public class LatLng
{
	private double mLatitude;
	private double mLongitude;
	
	public LatLng()
	{
	}
	
	public LatLng(double lat, double lng)
	{
		mLatitude = lat;
		mLongitude = lng;
	}
	
	public void setLatitude(double lat)
	{
		this.mLatitude = lat;
	}
	
	public void setLongitude(double lng)
	{
		this.mLongitude = lng;
	}
	
	public void setCoordinate(double lat, double lng)
	{
		this.mLatitude = lat;
		this.mLongitude = lng;
	}
	
	public double getLatitude()
	{
		return this.mLatitude;
	}
	
	public double getLongitude()
	{
		return this.mLongitude;
	}
	

}
