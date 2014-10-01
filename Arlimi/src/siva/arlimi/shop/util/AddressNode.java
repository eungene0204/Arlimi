package siva.arlimi.shop.util;

public class AddressNode
{
	private String mNum;
	private String mZipNum;
	private String mAddress;
	
	public AddressNode(String num, String zip, String addr)
	{
		this.mNum = num;
		this.mZipNum = zip;
		this.mAddress= addr;
	}
	
	public String getmNum()
	{
		return mNum;
	}

	public void setmNum(String mNum)
	{
		this.mNum = mNum;
	}

	public String getmZipNum()
	{
		return mZipNum;
	}

	public void setmZipNum(String mZipNum)
	{
		this.mZipNum = mZipNum;
	}

	public String getmAddress()
	{
		return mAddress;
	}

	public void setmAddress(String mAddress)
	{
		this.mAddress = mAddress;
	}

	
	
	

}
