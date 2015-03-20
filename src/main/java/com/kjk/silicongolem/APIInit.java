package com.kjk.silicongolem;

import com.kjk.silicongolem.scripting.APIRegistry;
import com.kjk.silicongolem.scripting.APITest;
import com.kjk.silicongolem.scripting.PeripheralRegistry;
import com.kjk.silicongolem.scripting.peripheral.APIConsole;

public class APIInit {
	
	public static void load(){
		
		Class[] visible = new Class[] {String.class};
		
		for(Class c : visible){
			APIRegistry.expose(c);
		}
		
		APIRegistry.expose(APITest.class);
		PeripheralRegistry.addPeripheral(APIConsole.class);
		
	}
	
}
