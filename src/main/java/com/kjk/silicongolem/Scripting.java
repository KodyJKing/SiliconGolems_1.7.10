package com.kjk.silicongolem;

import com.kjk.silicongolem.scripting.APIRegistry;
import com.kjk.silicongolem.scripting.APITest;

public class Scripting {
	
	public static void load(){
		
		Class[] visible = new Class[] {String.class};
		
		for(Class c : visible){
			APIRegistry.expose(c);
		}
		
	}
	
}
