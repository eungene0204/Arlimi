package siva.arlimi.location.util;

import java.util.concurrent.ExecutionException;

import siva.arlimi.location.service.ReverseGeocodingService;
import siva.arlimi.main.R;
import android.content.Context;
import android.content.Intent;

public class LocationUtil
{
	static public final String QUERY_REVERSE_GEOCODING = "QUERY";
	static public final String ACTION_REVERSE_GEOCODING = "REVESE_GEOCODING_RESULT";
	static public final String RESULT_REVERSE_GEOCODING = "REVESE_GEOCODING_RESULT";

	static public final String JSON_RESULTS = "results";
	static public final String JSON_FORMATTED_ADDRESS = "formatted_address";
	
	static public Intent getReverseGeocodingIntent(final Context context)
	{
		return new Intent(context, ReverseGeocodingService.class);
	}
	
	static public String getQuery(final Context context, final double latitude, final double longitude) throws InterruptedException, ExecutionException
	{
	
		String query = 
				context.getResources().getString(R.string.reverse_geocoding_query);
	
		String lat = String.valueOf(latitude);
		String lng = String.valueOf(longitude);
		
		StringBuilder url = new StringBuilder(query);
		url.append(lat);
		url.append(",");
		url.append(lng);
		url.append("&language=ko");
	
		return url.toString();
		
	}
	

}
