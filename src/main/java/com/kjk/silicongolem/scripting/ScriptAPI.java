package com.kjk.silicongolem.scripting;

public abstract class ScriptAPI {
	
	protected Environment env;
	
	void lockThread() {
		env.lockThread();
	}

	void unlockThread() {
		env.unlockThread();
	}

	public ScriptAPI(Environment env){
		this.env = env;
	}
	
}
