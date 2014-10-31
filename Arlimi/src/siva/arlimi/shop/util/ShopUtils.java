package siva.arlimi.shop.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import siva.arlimi.main.R;
import siva.arlimi.shop.service.GeoCondingService;
import siva.arlimi.shop.service.SearchAddressService;
import siva.arlimi.shop.service.ShopRegistrationService;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

public class ShopUtils
{
	static public final String KEY_NAME = "shop_name";
	static public final String KEY_ZIP = "zip_number";
	static public final String KEY_ADDRESS = "address";
	static public final String KEY_DETAIL_ADDRESS = "detail_address";
	static public final String KEY_PHONE = "shop_phone_number";
	static public final String KEY_DONG = "dong";
	static public final String KEY_LATITUDE = "shop_latitude";
	static public final String KEY_LONGITUDE = "shop_longitude";
	
	static public final String RESULT_OK = "ok";
	static public final String RESULT_FAIL = "fail"; 
	static public final String RESULT_DUPLICATE = "duplicate";

	static public final String ACTION_REGISTRATION_RESULT =
			"siva_arlimi_shop_registration_result";
	static public final String KEY_REGISTRATION_RESULT =
			"registration_result";

	static public final String ACTION_SEARCH_ADDRESS_RESULT = 
			"siva_arlimi_search_address_result";
	static public final String KEY_ADDRESS_SEARCH_RESULT =
			"address_search_result";
	
	static public final String DIALOG_TAG_ADDRESS_RESULT =
			"address_result_tag";
	
	static public final String ACTION_GEOCODING =
			"siva_arlimi_geocoding";
	static public final String KEY_GEOCODING =
			"geocoding";
	
	
	static public Intent getSearchAddressServiceIntent(Context context)
	{
		return new Intent(context,SearchAddressService.class);
	}
	
	static public Intent getGeoCodingServiceIntent(Context context)
	{
		return new Intent(context, GeoCondingService.class);
	}
	
	static public Intent getShopRegistrationIntent(Context context)
	{
		return new Intent(context, ShopRegistrationService.class);
	}
	
	
	static public AddrNodeList parseXml(String result)
	{
		AddrNodeList list = new AddrNodeList();
		
		try
		{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			InputStream iStream = new ByteArrayInputStream(result.getBytes("utf-8"));
			Document doc = builder.parse(iStream);
			
			Element detailReps = doc.getDocumentElement();
			NodeList details = detailReps.getElementsByTagName("detailList");
			
			int len = details.getLength();
			Node detail = null;
			for(int i = 0; i < len; i++)
			{
				detail = details.item(i);
				NodeList nodes = detail.getChildNodes();
				
				String no = nodes.item(0).getTextContent();
				String zip = nodes.item(1).getTextContent();
				String adres = nodes.item(2).getTextContent();
				
				AddressNode node = new AddressNode(no, zip, adres);
				list.addNode(node);
			}
			
			
		}
		catch (ParserConfigurationException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
	


}
