package com.kjk.silicongolem.scripting;

import net.minecraft.nbt.NBTTagCompound;

public abstract class APIPeripheral extends API {
	
	public APIPeripheral(Computer env, String name) {
		super(env, name);
	}

	public static String type = "";
	
	void save(NBTTagCompound nbt){}
	
	void load(NBTTagCompound nbt){}
	
	protected void writeNBT(NBTTagCompound nbt){}
	
	protected void readNBT(NBTTagCompound nbt){}
	
}
