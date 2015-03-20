package com.kjk.silicongolem.scripting;

import org.mozilla.javascript.Context;

public class ScriptThread extends Thread {
	
	private Computer env;
	private String script;
	
	private Context context;
	
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
			context.exit();
			return true;
		}
		return false;
	}
	
	public ScriptThread(Computer env, String script){
		this.env = env;
		this.script = script;
	}
	
	public void run(){
		context = Context.enter();
		try {
			context.evaluateString(env.excScope, script, "<cmd>", 0, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		kill();
	}
}
