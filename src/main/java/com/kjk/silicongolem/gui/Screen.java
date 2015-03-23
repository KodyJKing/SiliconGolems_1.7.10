package com.kjk.silicongolem.gui;

public abstract class Screen {
	
	GuiScreenConsole gui;

	public Screen(GuiScreenConsole gui){
		this.gui = gui;
	}
	
	public void backspace(){}
	
	public void copy(){}
	
	public void paste(String text){}
	
	public void enter(){}
	
	public void type(char c){}
	
	public void arrowUp(){}
	
	public void arrowDown(){}
	
	public void arrowLeft(){}
	
	public void arrowRight(){}
	
	public void draw(){}

	public void save(){}

	public void exit() {
	}
	
}
