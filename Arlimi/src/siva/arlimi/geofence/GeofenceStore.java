package siva.arlimi.geofence;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class GeofenceStore
{
	public static final String KEY_LATITUDE =
			"siva.arlimi.geofence.KEY_LATITUDE"; 
	public static final String KEY_LONGITUDE =
			"siva.arlimi.geofence.KEY_LONGITUDE";
	public static final String KEY_RADIUS = 
			"siva.arlimi.geofence.KEY_RADIUS";
	public static final String KEY_EXPIRATION_DURATION =
			"siva.arlimi.geofence.KEY_TRANSITION_TYPE";
	public static final String KEY_TRANSITION_TYPE =
			"siva.arlimi.geofence.KEY_TRANSITION_TYPE";
	public static final String KEY_PREFIX = 
			"siva.arlimi.geofence.KEY_PREFIX";
	
	public static final long INVALID_LONG_VALUE = -999l;
	public static final float INVALIDE_FLOAT_VALUE = -999.0f;
	public static final int INVALID_INT_VALUE = -999;
		
	private static final String SHARED_PREFERENCES = 
			"Geofence_Sharedpreferences";

	private final  SharedPreferences mPrefs;
	
	public GeofenceStore(Context context)
	{
		mPrefs = context.getSharedPreferences(SHARED_PREFERENCES, 
				Context.MODE_PRIVATE);
	}
	
	public ArlimiGeofence getGeofence(String id)
	{
		double lat = mPrefs.getFloat(getGeofenceFieldKey(id,KEY_LATITUDE), 
				INVALIDE_FLOAT_VALUE);
		
		double lng = mPrefs.getFloat(getGeofenceFieldKey(id, KEY_LONGITUDE)
				, INVALIDE_FLOAT_VALUE);
		
		float radius = mPrefs.getFloat(getGeofenceFieldKey(id, KEY_RADIUS)
				, INVALIDE_FLOAT_VALUE);
		
		long expirationDuration = mPrefs.getLong(getGeofenceFieldKey(id, KEY_EXPIRATION_DURATION)
				, INVALID_LONG_VALUE);
		
		int transitionType = mPrefs.getInt(getGeofenceFieldKey(id, KEY_TRANSITION_TYPE)
				, INVALID_INT_VALUE);
	
		//If All values are valid 
		if( INVALIDE_FLOAT_VALUE != lat &&
			INVALIDE_FLOAT_VALUE != lng &&
			INVALIDE_FLOAT_VALUE != radius &&
			INVALID_LONG_VALUE != expirationDuration &&
			INVALID_INT_VALUE != transitionType)
		{
			return new ArlimiGeofence (id,lat,lng,radius,expirationDuration,
					transitionType);
		}
		else
		{
			return null;
		}
		
	}
	
	public void setGeofence(String id, ArlimiGeofence geofence)
	{
		Editor editor = mPrefs.edit();
		
		editor.putFloat(getGeofenceFieldKey(id, KEY_LATITUDE), 
				(float) geofence.getLatitude());
		editor.putFloat(getGeofenceFieldKey(id, KEY_LONGITUDE),
				(float) geofence.getLongitude());
		editor.putFloat(getGeofenceFieldKey(id, KEY_RADIUS), 
				(float) geofence.getmRadius());
		editor.putLong(getGeofenceFieldKey(id, KEY_EXPIRATION_DURATION), 
				geofence.getmExpirationDuration());
		editor.putInt(getGeofenceFieldKey(id, KEY_TRANSITION_TYPE),
				geofence.getmTransitionType());
		
		editor.commit();
	}
	
	public void clearGeofence(String id)
	{
		Editor editor = mPrefs.edit();
		
		editor.remove(getGeofenceFieldKey(id, KEY_LATITUDE));
		editor.remove(getGeofenceFieldKey(id, KEY_LONGITUDE));
		editor.remove(getGeofenceFieldKey(id, KEY_RADIUS));
		editor.remove(getGeofenceFieldKey(id, KEY_EXPIRATION_DURATION));
		editor.remove(getGeofenceFieldKey(id, KEY_TRANSITION_TYPE));
		editor.commit();
	}

	private String getGeofenceFieldKey(String id, String fieldName)
	{
		return KEY_PREFIX + "_" + id + "_" + fieldName;
	}
	
}
