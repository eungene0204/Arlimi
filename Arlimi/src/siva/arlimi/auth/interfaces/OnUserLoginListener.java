package siva.arlimi.auth.interfaces;

import siva.arlimi.user.EmailUser;
import siva.arlimi.user.FacebookUser;

public interface OnUserLoginListener
{
	void facebookUserLogin(FacebookUser user);
	void emailUserLogin(EmailUser user);

}
