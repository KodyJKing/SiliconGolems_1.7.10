package com.kjk.silicongolem.gui;

import java.io.IOException;

import net.minecraft.util.EnumChatFormatting;

import com.kjk.silicongolem.common.Common;
import com.kjk.silicongolem.scripting.computer.ComponentDrive;
import com.kjk.silicongolem.texteditor.TextEditor;

public class ScreenFile extends Screen{
	
	ComponentDrive drive;
	TextEditor editor;
	String fileName;
	
	private int scrollY, scrollX;
	
	public ScreenFile(GuiScreenConsole gui, String fileName){
		super(gui);
		this.fileName = fileName;
		drive = gui.comp.drive;
		editor = new TextEditor(drive.readFile(fileName), gui.textWidth, false);
	}
	
	public void draw(){
		clampScroll();
		drawText();
		drawCursor();
	}
	
	protected void drawText(){
		int x = 0;
		int y = 0;
		int length = 0;
		int i = 0;
		for(char c : editor.toString().toCharArray()){
			if(y - scrollY > gui.textHeight){
				break;
			}
			int displayedX = x - scrollX;
			if(c != '\n' && y - scrollY >= 0 && displayedX >= 0 && displayedX <= gui.textWidth){
				gui.drawChar(x - scrollX ,y - scrollY,c, EnumChatFormatting.GREEN);
			}
			length++;
			x++;
			if(editor.shouldBreak(i, length))
			{
				x = 0;
				length = 0;
				y++;
			}
			i++;
		}
	}
	
	private void drawCursor(){
		if(Common.blink(1000,500)){
			return;
		}
		gui.drawChar(editor.getCursorX() - scrollX, editor.getCursorY() - scrollY, '_', EnumChatFormatting.DARK_GREEN);
	}
	
	private void clampScroll(){
		scrollY = Common.clamp(scrollY, editor.getCursorY() - gui.textHeight, editor.getCursorY());
		scrollX = Common.clamp(scrollX, editor.getCursorX() - gui.textWidth, editor.getCursorX());
	}

	@Override
	public void backspace() {
		editor.remove(editor.getCursor() - 1);
		editor.moveCursor(-1);
	}

	@Override
	public void copy() {
	}

	@Override
	public void paste(String text) {
		editor.insert(editor.getCursor(), text);
    	editor.moveCursor(text.length());
	}

	@Override
	public void enter() {
    	editor.insert(editor.getCursor(), "\n");
    	editor.moveCursor(1);
	}

	@Override
	public void type(char c) {
		editor.insert(editor.getCursor(), Character.toString(c));
    	editor.moveCursor(1);
	}

	@Override
	public void arrowUp() {
		editor.moveCursorY(-1);
	}

	@Override
	public void arrowDown() {
		editor.moveCursorY(1);
	}

	@Override
	public void arrowLeft() {
		editor.moveCursor(-1);
	}

	@Override
	public void arrowRight() {
		editor.moveCursor(1);
	}

	@Override
	public void save() {
		String file = editor.toString();
		drive.clearFile(fileName);
		drive.appendFile(fileName, file);
		try {
			drive.sendCLEAR(fileName);
			drive.sendAPPEND(fileName, file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void exit() {
		gui.closeEditor();
	}
	
	
	
}
