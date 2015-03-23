package com.kjk.silicongolem.scripting.computer;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;

import com.kjk.silicongolem.common.Common;
import com.kjk.silicongolem.scripting.Computer;

public class ComputerConsole extends Computer {

	public ComponentConsole console;
	public ComponentDrive drive;
	
	public ComputerConsole(){
		super();

	}

	@Override
	public void writeNBT(NBTTagCompound nbt) {
		drive.writeNBT(nbt);
	}

	@Override
	public void readNBT(NBTTagCompound nbt) {
		drive.readNBT(nbt);
	}

	@Override
	public void onLoad() {
		console = new ComponentConsole(this);
		console.setAddress(getAddress() + ".console");
		drive = new ComponentDrive(this);
		drive.setAddress(getAddress() + ".drive");
		drive.onLoad();
	}
	
	
	
}
