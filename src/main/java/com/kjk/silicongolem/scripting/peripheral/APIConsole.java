package com.kjk.silicongolem.scripting.peripheral;

import java.util.LinkedList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

import com.kjk.silicongolem.scripting.APIPeripheral;
import com.kjk.silicongolem.scripting.Computer;

public class APIConsole extends APIPeripheral {

	public APIConsole(Computer comp, String name) {
		super(comp, name);
		history = new LinkedList<String>();
	}
	
	List<String> history;
	
	protected void writeNBT(NBTTagCompound nbt){}
	
	protected void readNBT(NBTTagCompound nbt){}
	
	public int lineCount(){
		return history.size();
	}
	
	public String getLine(int i){
		return history.get(i);
	}
	
	public void print(String msg){
		lockThread();
		history.add(msg);
		unlockThread();
	}

}
