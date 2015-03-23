package com.kjk.silicongolem.scripting.computer;

import com.kjk.silicongolem.scripting.API;

public class APIConsole extends API{
	
	ComponentConsole console;
	
	protected APIConsole(ComponentConsole console){
		super(console.computer);
		this.console = console;
	}
	
	public void print(String msg){
		lockThread();
		console.add(msg);
		console.sendALL();
		unlockThread();
	}
	
}
