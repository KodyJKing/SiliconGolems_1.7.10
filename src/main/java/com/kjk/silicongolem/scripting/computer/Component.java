package com.kjk.silicongolem.scripting.computer;

import java.io.IOException;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;

import com.kjk.silicongolem.network.AddressBook;
import com.kjk.silicongolem.network.IStateful;
import com.kjk.silicongolem.scripting.API;
import com.kjk.silicongolem.scripting.Computer;

/*
 * A Component is meant to extend the state and functionality of a computer.
 */
public abstract class Component implements IStateful {
	
	Computer computer;
	API api;
	
	String address;

	
	public Component(Computer comp){
		computer = comp;
		addAPI();
	}
	
	void addAPI(){
		
	}

	@Override
	public void setAddress(String address) {
		this.address = address;
		AddressBook.add(this);
	}
	
	@Override
	public String getAddress() {
		return address;
	}

	@Override
	public void fromBytes(PacketBuffer buf) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void toBytes(PacketBuffer buf) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void partialUpdate(PacketBuffer buf) {
		// TODO Auto-generated method stub
		
	}
	
	public void readNBT(NBTTagCompound nbt){
		
	}
	
	public void writeNBT(NBTTagCompound nbt){
		
	}
	
}
