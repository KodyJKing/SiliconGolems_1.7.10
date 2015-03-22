package com.kjk.silicongolem.scripting;

/*
 * An API is meant to expose functionality to JavaScript.
 * It is not meant to implement internal state or functionality.
 * Do this in a Component and then add the API through it.
 */
public abstract class API {
	
	protected Computer comp;
	
	public API(Computer comp){
		this.comp = comp;
	}

	protected void lockThread() {
		comp.lockThread();
	}

	protected void unlockThread() {
		comp.unlockThread();
	}
	
}
