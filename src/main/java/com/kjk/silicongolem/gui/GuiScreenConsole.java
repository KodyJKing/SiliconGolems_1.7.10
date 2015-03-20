package com.kjk.silicongolem.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;

import com.kjk.silicongolem.common.Common;
import com.kjk.silicongolem.scripting.Computer;
import com.kjk.silicongolem.scripting.computer.ComputerConsole;
import com.kjk.silicongolem.scripting.peripheral.APIConsole;

public class GuiScreenConsole extends GuiScreenText {
	
	boolean fileOpen;
	
	APIConsole console;
	
	public GuiScreenConsole(ComputerConsole comp){
		console = (APIConsole) comp.peripherals.get("console");
		Common.msg(Minecraft.getMinecraft().thePlayer, ""+console.lineCount());
	}
	
	public void drawScreen(int a, int b, float c){
		super.drawScreen(a, b, c);
		drawChar(0,0,'z', EnumChatFormatting.GREEN);
		for(int y = 0; y < console.lineCount(); y++){
			String line = console.getLine(y);
			for(int x = 0; x < line.length(); x++){
				this.drawChar(x,y,line.charAt(x), EnumChatFormatting.GREEN);
			}
		}
	}
}
