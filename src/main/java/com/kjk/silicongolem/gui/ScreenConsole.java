package com.kjk.silicongolem.gui;

import java.io.Console;

import com.kjk.silicongolem.common.Common;

import net.minecraft.util.EnumChatFormatting;

public class ScreenConsole extends Screen {
	
	StringBuilder command;
	
	int cursorX, scrollY;
	
	ScreenConsole(GuiScreenConsole gui){
		super(gui);
		setCommand("");
	}
	
	public void setCommand(String cmd){
		command = new StringBuilder(cmd);
	}
	
	public void processCommand(){
		String cmd = command.toString();
		gui.console.add(">" + cmd);
		gui.console.sendALL();
		setCommand("");
		cursorX = 0;
		
		String[] words = cmd.split(" ");
		if(words[0].equals("edit")){
			gui.openEditor(words[1]);
		} else if(words[0].equals("run")){
			String script = gui.comp.drive.readFile(words[1]);
			gui.comp.run(script);
		}
	}
	
	public void draw(){
		clampScroll();
		int y = 0;
		int x = 0;
		for(y = scrollY; y < gui.console.size(); y++){
			String line = gui.console.get(y);
			for(x = 0; x < Math.min(line.length(), gui.textWidth); x++){
				gui.drawChar(x, y - scrollY, line.charAt(x), EnumChatFormatting.GREEN);
			}
		}
		
		gui.drawChar(0, y - scrollY, '>', EnumChatFormatting.GREEN);
		
		for(x = 0; x < Math.min(command.length(), gui.textWidth); x++){
			gui.drawChar(x + 1, y - scrollY, command.charAt(x), EnumChatFormatting.GREEN);
		}
		if(Common.blink(1000, 500)){
			gui.drawChar(cursorX + 1, y - scrollY, '_', EnumChatFormatting.DARK_GREEN);
		}
	}

	@Override
	public void backspace() {
		if(cursorX <= 0 || cursorX > command.length()){
			clampCur();
			return;
		}
		command.deleteCharAt(cursorX - 1);
		clampCur();
	}

	
	
	@Override
	public void copy() {
		gui.setClipboardString(command.toString());
	}

	@Override
	public void paste(String text) {
		command.insert(cursorX, text);
	}

	@Override
	public void enter() {
		processCommand();
	}

	@Override
	public void type(char c) {
		if(command.length() >= gui.textWidth){
			return;
		}
		command.insert(cursorX, c);
		cursorX++;
	}

	@Override
	public void arrowLeft() {
		cursorX--;
		clampCur();
	}

	@Override
	public void arrowRight() {
		cursorX++;
		clampCur();
	}
	

	public void clampCur(){
		cursorX = Common.clamp(cursorX, 0, command.length());
	}
	
	public void clampScroll(){
		scrollY = Common.clamp(scrollY, gui.console.size() + 1 - gui.textHeight, gui.console.size() + 1);
	}
}
