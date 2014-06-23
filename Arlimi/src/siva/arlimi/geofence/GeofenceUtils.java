package siva.arlimi.geofence;

public class GeofenceUtils
{
	public enum REMOVE_TYPE {INTENT, LIST}
	public enum REQUEST_TYPE {ADD, REMOVE}
	
	public static final String APPTAG = "Geofence Detection";
	
	public static final String ACTION_CONNECTION_ERROR =
			"siva.arlimi.location.ACTION_CONNECTION_ERROR"; 
	
	public static final String ACTION_CONNECTION_SUCCESS =
			"siva.arlimi.location.ACTION_CONNECTION_SUCCESS";
	
	public static final String ACTION_GEOFENCE_ADDED =
			"siva.arlimi.location.ACTION_GEOFENCE_ADDED";
	
	public static final String ACTION_GEOFENCE_REMOVED =
			"siva.arlimi.location.ACTION_GEOFENCE_DELETED";
	
	public static final String ACTION_GEOFENCE_ERROR =
			"siva.arlimi.location.ACTION_GEOFENCE_ERROR";
	
	public static final String ACTION_GEOFENCE_TRANSITION =
			"siva.arlimi.location.ACTION_GEOFENCE_TRANSITION";
	
	public static final String ACTION_GEOFENCE_TRANSITION_ERROR =
			"siva.arlimi.location.ACTION_GEOFENCE_TRANSITION_ERROR";
	
	public static final String KEY_LATITUDE = "siva.arlimi.location_KEY_LATITUDE";
	public static final String KEY_LONGITUDE = "siva.arlimi.location_KEY_LONGITUDE";
	public static final String KEY_RADIUS = "siva.arlimi.location_KEY_RADIUS";
	public static final String KEY_EXPIRATION_DURATION =
			"siva.arlimi.location_KEY_EXPIRATION_DURATION";
	public static final String KEY_TRANSITION_TYPE =
			"siva.arlimi.location_KEY_TRANSITON_TYPE";
	
	public static final String KEY_PREFIX =
			"siva.arlimi.location_KEY";
	
	public static final long INVALID_LONG_VALUE = -999l;
	public static final float INVALID_FLOAT_VALUE = -999.0F;
	public static final int INVALID_INT_VALUE = -999;
	
	public static final double MAX_LATITUDE = 90.d;
	public static final double MIN_LATITUDE = -90.d;
	public static final double MAX_LONGITUDE = 180.d;
	public static final double MIN_LONGITUDE = -180.d;
	public static final float MIN_RADIUS = 1f;
	
	public final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
	
	public static final String EMPTY_STRING = new String();
	public static final CharSequence GEOFENCE_ID_DELIMITER = ",";
	
	public static final String CATEGORY_LOCATION_SERVICES =
			"siva.arlimi.location_CATEGORY_LOCATION_SERVICES";
	
	public static final String EXTRA_CONNECTION_CODE =
			"siva.arlimi.location_EXTRA_CONNECTION_CODE";
	
	public static final String EXTRA_CONNECTION_ERROR_CODE =
			"siva.arlimi.location_EXTRA_CONNECTION_ERROR_CODE";
	
	public static final String EXTRA_CONNECTION_ERROR_MESSAGE =
			"siva.arlimi.location_EXTRA_CONNECTION_ERROR_MESSAGE";
	
	public static final String EXTRA_GEOFENCE_STATUS =
			"siva.arlimi.location_EXTAR_GEOFENCE_STATUS";
	
	
}
