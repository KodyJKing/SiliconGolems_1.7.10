package com.kjk.silicongolem.network;

import java.util.WeakHashMap;

public class AddressBook {
	
	public static WeakHashMap<String, IStateful> destinations = new WeakHashMap<String, IStateful>();
	
	public static void add(IStateful element){
		destinations.put(element.getAddress(), element);
	}
	
	public static IStateful get(String address){
		return destinations.get(address);
	}
	
}
