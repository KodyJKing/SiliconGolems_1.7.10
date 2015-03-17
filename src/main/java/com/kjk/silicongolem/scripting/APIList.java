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

public class APIList implements ClassShutter{
	
	private List<Entry> entries;
	private Set<String> visibleClasses;
	
	public APIList(){
		entries = new LinkedList<Entry>();
		visibleClasses = new HashSet<String>();
	}
	
	public void addAPI(String name, Class<? extends ScriptAPI> api){
		entries.add(new Entry(name, api));
		visibleClasses.add(api.getClass().getName());
	}
	
	
	public void addPeripheral(Class api){
		visibleClasses.add(api.getName());
	}
	
	public Scriptable subscribe(Context cx, Environment env){
		cx.setClassShutter(this);
		Scriptable scope = cx.initStandardObjects();
		for(Entry e: entries){
			try{
				Class api = e.getApi();
			    Constructor ctor = api.getConstructor(Environment.class);
			    ctor.setAccessible(true);
			    scope.put(e.getName(), scope, ctor.newInstance(env));
			} catch(Exception er){
				System.out.print("Failed to load api:  " + e.getName());
				System.out.println(er.getMessage());
			}
			
		}
		return scope;
	}

	@Override
	public boolean visibleToScripts(String fullClassName) {
		return visibleClasses.contains(fullClassName);
	}
}

class Entry{
	
	private String name;
	private Class<? extends ScriptAPI> api;
	
	public Entry(String name, Class<? extends ScriptAPI> api){
		this.name = name;
		this.api = api;
	}
	
	public String getName() {
		return name;
	}
	
	public Class<? extends ScriptAPI> getApi() {
		return api;
	}
}