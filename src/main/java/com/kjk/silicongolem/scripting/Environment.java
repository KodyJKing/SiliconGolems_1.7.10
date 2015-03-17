package com.kjk.silicongolem.scripting;

import net.minecraft.entity.Entity;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class Environment {
	
	Context context;
	Scriptable topLevelScope, userScope;
	ScriptThread thread;
	boolean isLive;
	private Entity owner;
	
	void lockThread() {
		thread.lock();
	}

	void unlockThread() {
		thread.unlock();
	}
	
	public Environment(){
		context = Context.enter();
		topLevelScope = APIList.subscribe(context, this);
		userScope = context.initStandardObjects();
		userScope.setParentScope(topLevelScope);
	}
	
	public void run(String script){
		if (tryKillThread()) {
			isLive = true;
			thread = new ScriptThread(this, script);
			thread.start();
		}
	}
	
	public boolean tryKillThread(){
		isLive = false;
		if(!threadLive()){
			return true;
		}
		return thread.kill();
	}
	
	public void kill(){
		tryKillThread();
	}
	
	public boolean threadLive(){
		return thread != null && thread.isAlive();
	}

	public Entity getOwner() {
		return owner;
	}

	public void setOwner(Entity owner) {
		this.owner = owner;
	}
}
