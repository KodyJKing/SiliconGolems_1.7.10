package com.kjk.silicongolem.scripting;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.mozilla.javascript.ClassShutter;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class APIRegistry implements ClassShutter{
	
	private static Set<String> visibleClasses = new HashSet<String>();
	public static void expose(Class c){
		visibleClasses.add(c.getName());
	}
	
//	static API loadAPI(Class<? extends API> api, Computer comp) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
//	    Constructor ctor;
//		ctor = api.getConstructor(Computer.class);
//		ctor.setAccessible(true);
//		return (API) ctor.newInstance(comp);
//	}
	
	@Override
	public boolean visibleToScripts(String fullClassName) {
		return visibleClasses.contains(fullClassName);
	}
}