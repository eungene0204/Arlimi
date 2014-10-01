package siva.arlimi.shop.util;

import java.util.ArrayList;

public class AddrNodeList
{
	private final ArrayList<AddressNode> list;
	
	public AddrNodeList()
	{
		list = new ArrayList<AddressNode>();
	}
	
	public AddressNode getNode(int index)
	{
		return list.get(index);
	}
	
	public void addNode(AddressNode node)
	{
		list.add(node);
	}
	
	public int getSize()
	{
		return list.size();
	}
	
	

}
