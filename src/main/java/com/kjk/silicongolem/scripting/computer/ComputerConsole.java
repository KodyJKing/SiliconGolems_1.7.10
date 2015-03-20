package com.kjk.silicongolem.scripting.computer;

import java.util.List;

import net.minecraft.nbt.NBTTagCompound;

import com.kjk.silicongolem.scripting.APIPeripheral;
import com.kjk.silicongolem.scripting.Computer;
import com.kjk.silicongolem.scripting.peripheral.APIConsole;

public class ComputerConsole extends Computer {
	
	public ComputerConsole(){
		super();
		APIConsole console = new APIConsole(this, "console");
		peripherals.put("console", console);
	}

}
