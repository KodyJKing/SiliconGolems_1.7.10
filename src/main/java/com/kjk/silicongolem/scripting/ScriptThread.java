package com.kjk.silicongolem.scripting;

import org.mozilla.javascript.Context;

public class ScriptThread extends Thread {
	
	private Environment env;
	private String script;
	
	private int locks;
	
	public void lock(){
		locks += 1;
	}
	
	public void unlock(){
		locks -= 1;
		if(!env.isLive){
			kill();
		}
	}
	
	public boolean kill(){
		if(locks <= 0){
			stop();
			return true;
		}
		return false;
	}
	
	public ScriptThread(Environment env, String script){
		this.env = env;
		this.script = script;
	}
	
	public void run(){
		Context context = Context.enter();
		context.evaluateString(env.userScope, script, "<cmd>", 0, null);
	}
}
