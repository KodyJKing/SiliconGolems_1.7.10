package com.kjk.silicongolem.scripting;

import net.minecraft.entity.Entity;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class Environment {
	
	Context context;
	Scriptable topLevelScope, userScope;
	ScriptThread thread;
	private Entity owner;
	
	public Environment(){
		context = Context.enter();
		topLevelScope = APIList.subscribe(context, this);
		userScope = context.initStandardObjects();
		userScope.setParentScope(topLevelScope);
	}
	
	public void run(String script){
		if (tryKillThread()) {
			thread = new ScriptThread(this, script);
			thread.start();
		}
	}
	
	public boolean tryKillThread(){
		if(thread == null || !thread.isAlive()){
			return true;
		}
		return thread.kill();
	}

	public Entity getOwner() {
		return owner;
	}

	public void setOwner(Entity owner) {
		this.owner = owner;
	}
}
