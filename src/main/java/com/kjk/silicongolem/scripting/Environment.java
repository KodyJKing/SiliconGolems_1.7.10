package com.kjk.silicongolem.scripting;

import net.minecraft.entity.Entity;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class Environment {
	
	Context context;
	Scriptable topLevelScope, userScope;
	private Entity owner;
	
	public Environment(){
		context = Context.enter();
		topLevelScope = APIList.subscribe(context, this);
		userScope = context.initStandardObjects();
		userScope.setParentScope(topLevelScope);
	}
	
	public void run(String script){
		try{
		    context.evaluateString(userScope, script, "<cmd>", 0, null);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	public Entity getOwner() {
		return owner;
	}

	public void setOwner(Entity owner) {
		this.owner = owner;
	}
}
