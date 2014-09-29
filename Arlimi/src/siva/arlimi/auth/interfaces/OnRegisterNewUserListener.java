package siva.arlimi.auth.interfaces;

import siva.arlimi.user.EmailUser;
import siva.arlimi.user.FacebookUser;

public interface OnRegisterNewUserListener
{
	void registerNewFacebookUser(FacebookUser user);
	void registerNewEmailUser(EmailUser user);

}
