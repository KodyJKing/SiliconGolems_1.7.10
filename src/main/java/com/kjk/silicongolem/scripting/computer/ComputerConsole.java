package com.kjk.silicongolem.scripting.computer;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import net.minecraft.network.PacketBuffer;

import com.kjk.silicongolem.common.Common;
import com.kjk.silicongolem.scripting.Computer;

public class ComputerConsole extends Computer {

	ComponentConsole console;
	ComponentDrive drive;
	
	public ComputerConsole(){
		super();
		console = new ComponentConsole(this);
		console.setAddress(getAddress() + ".console");
		drive = new ComponentDrive(this);
		drive.setAddress(getAddress() + ".drive");
	}
	
	@Override
	public void fromBytes(PacketBuffer buf) throws IOException {
	}

	@Override
	public void toBytes(PacketBuffer buf) throws IOException {
	}

	@Override
	public void partialUpdate(PacketBuffer buf) {
		
	}

}
