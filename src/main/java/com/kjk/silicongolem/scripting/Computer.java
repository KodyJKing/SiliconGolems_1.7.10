package com.kjk.silicongolem.scripting;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public abstract class Computer {
	
	Context context;
	public Scriptable excScope;
	ScriptThread thread;
	boolean isLive;
	
	public Map<String, APIPeripheral> peripherals;
	
	public void lockThread() {
		thread.lock();
	}

	public void unlockThread() {
		thread.unlock();
	}
	
	public Computer(){
		peripherals = new HashMap<String, APIPeripheral>();
		context = Context.enter();
		excScope = context.initStandardObjects();
		
		new APITest(this, "test");
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
	
	public World getWorld(){
		return null;
	}
	
	public void writeNBT(NBTTagCompound nbt){
	}
	
	public void readNBT(NBTTagCompound nbt){
	}
}
