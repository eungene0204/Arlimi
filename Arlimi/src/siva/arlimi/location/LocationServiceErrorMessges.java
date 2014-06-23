package siva.arlimi.location;

import siva.arlimi.activity.R;

import com.google.android.gms.common.ConnectionResult;

import android.content.Context;
import android.content.res.Resources;

public class LocationServiceErrorMessges
{
	private LocationServiceErrorMessges() {}
	
	public static String getErrorString(Context context, int errorCode)
	{
		Resources resources = context.getResources();
		
		String errorString;
		
		switch(errorCode)
		{
		
		case ConnectionResult.DEVELOPER_ERROR:
			errorString = resources.getString(R.string.connection_error_misconfigured);
			break;
			
		case ConnectionResult.INTERNAL_ERROR:
			errorString = resources.getString(R.string.connection_error_internal);
			break;
			
		case ConnectionResult.INVALID_ACCOUNT:
			errorString = resources.getString(R.string.connection_error_invalid_account);
			break;
			
		case ConnectionResult.LICENSE_CHECK_FAILED:
			errorString = resources.getString(R.string.connection_error_license_check_failed);
			break;
			
		case ConnectionResult.NETWORK_ERROR:
			errorString = resources.getString(R.string.connection_error_network);
			break;
			
		case ConnectionResult.RESOLUTION_REQUIRED:
			errorString = resources.getString(R.string.connection_error_needs_resolution);
			break;
			
		case ConnectionResult.SERVICE_DISABLED:
			errorString = resources.getString(R.string.connection_error_disabled);
			break;
			
		case ConnectionResult.SERVICE_INVALID:
			errorString = resources.getString(R.string.connection_error_invalid);
			break;
			
		case ConnectionResult.SERVICE_MISSING:
			errorString = resources.getString(R.string.connection_error_missing);
			break;
			
		case ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED:
			errorString = resources.getString(R.string.connection_error_outdated);
			break;
			
		case ConnectionResult.SIGN_IN_REQUIRED:
			errorString = resources.getString(R.string.connection_error_sign_in_required);
			break;
			
		default:
			errorString = resources.getString(R.string.connection_error_unknown);
			break;
		
		}
		
		return errorString;
		
	}
	
	

}
