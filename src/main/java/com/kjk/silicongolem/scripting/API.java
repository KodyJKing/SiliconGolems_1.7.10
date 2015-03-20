package com.kjk.silicongolem.scripting;

public abstract class API {
	
	protected Computer comp;
	
	public API(Computer comp, String name){
		this.comp = comp;
		comp.excScope.put(name, comp.excScope, this);
	}

	protected void lockThread() {
		comp.lockThread();
	}

	protected void unlockThread() {
		comp.unlockThread();
	}
	
}
