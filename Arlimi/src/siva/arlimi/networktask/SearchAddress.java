package siva.arlimi.networktask;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;

import net.daum.mf.map.api.MapView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import siva.arlimi.activity.FindAddressActivity;
//import siva.arlimi.jsonparse.AddressInfo;
//import siva.arlimi.jsonparse.JsonParser;
//import siva.arlimi.openapi.DaumMapView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class SearchAddress extends NetworkTask
{
	public static final String GOOGLE_PLACE_URL = "https://maps.googleapis.com/maps/api/place/textsearch/";
	public static final String GOOGLE_BROWSER_API_KEY = "AIzaSyCwtlcJP36LPjyT1kncN2Q8NW7uYVwB6nM";
	
	private static final String TAG_MAIN_TITLE = "main_title"; 
	private static final String TAG_DESCRIPTION = "description";
	private static final String TAG_TOTALCOUNT = "totalcount";
	private static final String TAG_RESULT = "result";
	private static final String TAG_Q = "q";
	private static final String TAG_ITEM = "item";
	private static final String TAG_ADDRESS_TITLE = "title";
	private static final String TAG_MOUNTAIN = "mountain";
	private static final String TAG_LOCALNAME_1 = "localName_1";
	private static final String TAG_LOCALNAME_2 = "localName_2";
	private static final String TAG_LOCALNAME_3 = "localName_3";
	private static final String TAG_MAINADDRESS = "mainAddress";
	private static final String TAG_SUBADDRESS = "subAddress";
	private static final String TAG_BUILDINGADDRESS = "buildingAddress";
	private static final String TAG_ISNEWADDRESS = "isNewAddress";
	private static final String TAG_NEWADDRESS = "newAddress";
	private static final String TAG_LNG = "lng";
	private static final String TAG_LAT = "lat";
	private static final String TAG_POINT_X = "point_x";
	private static final String TAG_POINT_Y = "point_y";
	private static final String TAG_POINT_WX = "point_wx";
	private static final String TAG_POINT_Wy = "point_wy";
	private static final String TAG_ID = "id";
	
	//private JsonParser mJsonParser;
	private JSONArray mItems = null;
	//private AddressInfo mAddrInfo; 
	private FindAddressActivity mContext;
	
	public SearchAddress()
	{
		super();
	}
	
	public SearchAddress(FindAddressActivity findAddressActivity)
	{
		super(findAddressActivity);
	//	mJsonParser = new JsonParser();
	//	mAddrInfo = new AddressInfo();
		mContext = findAddressActivity;
	}

	@Override
	public void sendData(HttpURLConnection conn, String[] params) throws UnsupportedEncodingException, IOException
	{
		
	}
	
	public static String getGoogleURL(String query) throws UnsupportedEncodingException
	{
		String url = GOOGLE_PLACE_URL;
		url += "json";
		url += "?query=pasta";
		url += "&sensor=true";
		url += "&language=ko";
		url += "&key=" + GOOGLE_BROWSER_API_KEY;
		return url;
	}
	
	@Override
	protected void onPostExecute(String result) 
	{
		super.onPostExecute(result);
		parseJson(result);
	}
	
	public void parseJson(String result) 
	{
		/*
		try
		{
			JSONObject json = mJsonParser.getJsonObject(result);
			JSONObject channel = json.getJSONObject("channel");
			
			//Get array of item
			mItems = channel.getJSONArray(TAG_ITEM);
			
			for(int i = 0; i < mItems.length(); i++)
			{
				 JSONObject item = mItems.getJSONObject(i);
				
				 mAddrInfo.setmLatitude(item.getString(TAG_LAT));
				 mAddrInfo.setmLogitude(item.getString(TAG_LNG));
				 mAddrInfo.setmPoint_x(item.getString(TAG_POINT_X));
				 mAddrInfo.setmPoint_y(item.getString(TAG_POINT_Y)); 
			}
			
		} catch (JSONException e)
		{
			e.printStackTrace();
		} */
		
		showAddressInfo();
	}
	
	public void showAddressInfo()
	{
//		DaumMapView mDaumMapView = mContext.getDaumMapView();
//		
//		mDaumMapView.setPOIItem(mAddrInfo);
//		LinearLayout linear = mContext.getLinearLayout();
//		RelativeLayout relay = mContext.getRelativeLayout();
//		MapView mapView = mDaumMapView.getMapView();
//		
	//	mapView.postInvalidate();
	//	linear.postInvalidate();
	//	relay.postInvalidate();
	
	
	}

}
