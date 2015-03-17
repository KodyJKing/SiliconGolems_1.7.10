package com.kjk.silicongolem.scripting;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class Environment {
	
	public Context context;
	private Scriptable topLevelScope, userScope;
	
	public Environment(APIList apis){
		context = Context.enter();
		topLevelScope = apis.subscribe(context, this);
		userScope = context.initStandardObjects();
		userScope.setParentScope(topLevelScope);
	}
	
	
}
