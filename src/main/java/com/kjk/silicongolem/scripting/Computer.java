package com.kjk.silicongolem.scripting;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import com.kjk.silicongolem.Network;
import com.kjk.silicongolem.network.AddressBook;
import com.kjk.silicongolem.network.IStateful;
import com.kjk.silicongolem.network.PartialUpdate;
import com.kjk.silicongolem.network.Update;

public abstract class Computer implements IStateful {
	
	Context context;
	public Scriptable excScope;
	ScriptThread thread;
	boolean isLive;
	String address;
	
	public void lockThread() {
		thread.lock();
	}

	public void unlockThread() {
		thread.unlock();
	}
	
	public Computer(){
		context = Context.enter();
		excScope = context.initStandardObjects();
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
		AddressBook.add(this);
	}

	public void addAPI(API api, String name){
		APIRegistry.expose(api.getClass());
		excScope.put(name, excScope, api);
	}
	
	public void sendUpdate(IStateful toUpdate){
		if(isRemote()){
			Network.network.sendToServer(new Update(toUpdate));
		}
		else {
			Network.network.sendToAll(new Update(toUpdate));
		}
	}
	
	public void sendPartialUpdate(PartialUpdate update){
		if(isRemote()){
			Network.network.sendToServer(update);
		}
		else {
			Network.network.sendToAll(update);
		}
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
	
	public boolean isRemote(){
		return getWorld().isRemote;
	}
	
	public void writeNBT(NBTTagCompound nbt){
	}
	
	public void readNBT(NBTTagCompound nbt){
	}
	
	@Override
	public void fromBytes(PacketBuffer buf) throws IOException {}

	@Override
	public void toBytes(PacketBuffer buf) throws IOException {}

	@Override
	public void partialUpdate(PacketBuffer buf) {}

	@Override
	public void onLoad() {
		
	}
}
