package com.kjk.silicongolem.scripting;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class Environment {
	
	public Context context;
	private Scriptable scope;
	
	public Environment(APIList apis){
		context = Context.enter();
		scope = apis.subscribe(context, this);
	}
	
}
