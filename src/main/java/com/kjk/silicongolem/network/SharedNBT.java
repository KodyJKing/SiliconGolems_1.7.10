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
	
	NBTTagCompound sharedNBT;
	
	List<EmptyCallback> updateCallbacks;
	
	public SharedNBT(String networkName){
		netId = networkName;
		updateCallbacks = new LinkedList<EmptyCallback>();
	}
	public void getSharedNBT(EmptyCallback callback) {
		if(isDirty()){
			requestUpdate(callback);
		}
	}

	public void setSharedNBT(NBTTagCompound sharedNBT) {
		this.sharedNBT = sharedNBT;
		SharedNBTManager.network.sendToServer(new SharedNBTUpdate(this));
	}

	boolean dirty;
	
	public boolean isDirty() {
		return dirty;
	}

	public void setDirty(boolean dirty) {
		this.dirty = dirty;
	}
	
	private void requestUpdate(EmptyCallback callback) {
		SharedNBTManager.network.sendToServer(new SharedNBTRequest(this));
		updateCallbacks.add(callback);
	}
	
}
