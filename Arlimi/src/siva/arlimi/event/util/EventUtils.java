package siva.arlimi.event.util;

import siva.arlimi.event.service.EventRegistrationService;
import android.content.Context;
import android.content.Intent;

public class EventUtils
{
	public static final String KEY_NAME = "NAME";
	public static final String KEY_EMAIL = "EMAIL";

	public static final String EVENT_ID = "EVENT_ID";
	public static final String EVENT_CONTENTS = "EVENT_CONTENTS";
	public static final String EVENT_START_DATE = "EVENT_START_DATE";
	public static final String EVENT_END_DATE = "EVENT_END_DATE";
	public static final String EVENT_START_TIME = "EVENT_START_TIME";
	public static final String EVENT_END_TIME = "EVENT_END_TIME";
	public static final String EVENT_RADIUS = "EVENT_RADIUS";
	public static final String EVENT_LATITUDE = "EVENT_LATITUDE";
	public static final String EVENT_LONGITUDE = "EVENT_LONGITUDE";
	
	public static final String SHOP_NAME = "SHOP_NAME";
	public static final String SHOP_ADDRESS = "SHOP_ADDRESS";
	public static final String SHOP_PHONE_NUMBER = "SHOP_PHONE_NUMBER";

	public static final String KEY_REG_RESULT = "key_reg_reuslt";
	
	public static final String ACTION_REG_RESULT = "action_reg_result";
	
	public static Intent getEventRegistrationIntent(Context context)
	{
		return new Intent(context,EventRegistrationService.class);
	}
	
}
