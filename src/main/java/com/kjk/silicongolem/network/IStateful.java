package com.kjk.silicongolem.network;

import java.io.IOException;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import io.netty.buffer.ByteBuf;

public interface IStateful {
	
	public void setAddress(String address);
	
	public String getAddress();
	
	public void fromBytes(PacketBuffer buf) throws IOException;

	public void toBytes(PacketBuffer buf) throws IOException;
	
	public void partialUpdate(PacketBuffer buf);
	
	public void readNBT(NBTTagCompound nbt);
	
	public void writeNBT(NBTTagCompound nbt);

	public void onLoad();
	
}
