package siva.arlimi.user;


public class EmailUser extends User 
{
	private static final long serialVersionUID = 2010712509365365617L;
	
	
	public EmailUser()
	{
	}
	
	public EmailUser(String name, String email, String password)
	{
		this.mName = name;
		this.mEmail = email;
		this.mPassword = password;
	}
	
}
