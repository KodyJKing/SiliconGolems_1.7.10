package com.kjk.silicongolem.scripting;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class PeripheralRegistry {

	static Map<String, Class<? extends APIPeripheral>> peripheralTypes = 
	new HashMap<String, Class<? extends APIPeripheral>>();

	public static void addPeripheral(Class<? extends APIPeripheral> peripheral){
		try {
			Field f = peripheral.getField("type");
			String type = (String) f.get(null);
			PeripheralRegistry.peripheralTypes.put(type, peripheral);
		} 
		catch (Exception e) {
			e.printStackTrace();
		} 
		APIRegistry.expose(peripheral);
	}

	public static APIPeripheral loadPeripheral(String type, Computer comp){
		try {
			return (APIPeripheral) APIRegistry.loadAPI(PeripheralRegistry.peripheralTypes.get(type), comp);
		} catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}

}
