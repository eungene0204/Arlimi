package siva.arlimi.auth.session;

import siva.arlimi.main.MainActivity;
import siva.arlimi.user.User;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SessionManager
{
	private SharedPreferences mPref;
	private Editor mEditor;
	
	private Context mContext;
	
	private int PRIVATE_MODE = 0;

	private static final String PREF_NAME = "user_pref";
	private static final String IS_LOGIN = "IsLoggedIn";
	
	public static final String KEY_EMAIL = "email";
	public static final String KEY_NAME = "name";
	
	public SessionManager(Context context)
	{
		mContext = context;
		mPref = mContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
		mEditor = mPref.edit();
	}
	
	public void createLoginSession(final String email, final String name)
	{
		mEditor.putBoolean(IS_LOGIN, true);
		mEditor.putString(KEY_EMAIL, email);
		mEditor.putString(KEY_NAME, name);
		
		mEditor.commit();
	}
	
	public void checkLogin()
	{
		
	}
	
	public User getUserDetails()
	{
		User user = new User(); 
		
		user.setEmail(mPref.getString(KEY_EMAIL, null));
		user.setName(mPref.getString(KEY_NAME, null));
		
		return user;
	}
	
	public void logoutUser()
	{
		mEditor.clear();
		mEditor.commit();
		
		Intent i = new Intent(mContext.getApplicationContext(),MainActivity.class );
		
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		mContext.startActivity(i);		
	}
	
	public boolean isLoggedIn()
	{
		return mPref.getBoolean(IS_LOGIN, false);
	}

}
