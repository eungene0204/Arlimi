package siva.arlimi.service.util;

import siva.arlimi.service.service.AllServiceListService;
import siva.arlimi.service.service.RecommendedServiceListService;
import android.content.Context;
import android.content.Intent;

public class ServiceUtil
{
	static public final String ACTION_ALL_SERVICE_LIST = "SIVA_ACTION_ALL_SERVICE_LIST";
	static public final String KEY_ALL_SERVICE_LIST = "SIVA_KEY_ALL_SERVICE_LIST";
	
	static public Intent getAllServiceListIntent(final Context context)
	{
		return new Intent(context, AllServiceListService.class);
	}
	
	static public Intent getRecommendedServiceIntent(final Context context)
	{
		return new Intent(context, RecommendedServiceListService.class);
	}

}
