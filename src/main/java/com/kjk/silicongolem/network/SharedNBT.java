package com.kjk.silicongolem.network;

import java.util.LinkedList;
import java.util.List;

import com.kjk.silicongolem.SGolem;
import com.kjk.silicongolem.entity.EntitySGolem;

import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.nbt.NBTTagCompound;

public class SharedNBT {
	
	String netId;
	
	NBTTagCompound nbt;
	
	List<EmptyCallback> updateCallbacks;
	
	public SharedNBT(String networkName){
		netId = networkName;
		this.nbt = new NBTTagCompound();
		updateCallbacks = new LinkedList<EmptyCallback>();
	}
	public NBTTagCompound getNbt() {
		return nbt;
	}

	public void setNbt(NBTTagCompound sharedNBT) {
		this.nbt = sharedNBT;
	}

	boolean dirty;
	
	public boolean isDirty() {
		return dirty;
	}

	public void setDirty(boolean dirty) {
		this.dirty = dirty;
	}
	
	private void sendUpdate(){
		SharedNBTManager.network.sendToServer(new SharedNBTUpdate(this));
	}
	
	private void requestUpdate(EmptyCallback callback) {
		if(isDirty()){
			SharedNBTManager.network.sendToServer(new SharedNBTRequest(this));
			updateCallbacks.add(callback);
		}
		else {
			callback.run();
		}
	}
	
}
