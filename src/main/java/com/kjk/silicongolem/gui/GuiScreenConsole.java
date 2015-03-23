package com.kjk.silicongolem.gui;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.EnumChatFormatting;

import com.kjk.silicongolem.common.Common;
import com.kjk.silicongolem.scripting.Computer;
import com.kjk.silicongolem.scripting.computer.ComponentConsole;
import com.kjk.silicongolem.scripting.computer.ComputerConsole;

public class GuiScreenConsole extends GuiScreenText {
	
	boolean fileOpen;
	
	ScreenConsole consoleScr;
	ScreenFile fileScr;
	ComputerConsole comp;
	ComponentConsole console;
	
	public GuiScreenConsole(ComputerConsole comp){
		this.comp = comp;
		console = comp.console;
		consoleScr = new ScreenConsole(this);
	}
	
	public void openEditor(String fileName){
		fileOpen = true;
		fileScr = new ScreenFile(this, fileName);
	}
	
	public void closeEditor(){
		fileOpen = false;
	}
	
	@Override
	public void initGui() {
		super.initGui();
		Keyboard.enableRepeatEvents(true);
	}

	@Override
	public void onGuiClosed() {
		super.onGuiClosed();
		Keyboard.enableRepeatEvents(false);
	}



	public void drawScreen(int a, int b, float c){
		super.drawScreen(a, b, c);
		if(fileOpen){
			fileScr.draw();
		} else {
			consoleScr.draw();
		}
	}
	
	protected void keyTyped(char character, int par2)
    {
        super.keyTyped(character, par2);
        
        Screen scr = fileOpen ? fileScr : consoleScr;
        
        if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)){
        	scr.arrowLeft();
        }
        else if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)){
        	scr.arrowRight();
        }
        else if(Keyboard.isKeyDown(Keyboard.KEY_UP)){
        	scr.arrowUp();
        }
        else if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)){
        	scr.arrowDown();
        }
        else{
        	processTyping(character, par2, scr);
        }
    }
	
	private void processTyping(char character, int par2, Screen scr){
		switch (character)
        {
            case 3:
        	    scr.copy();
            	return;
            case 22:
            	String clip = GuiScreen.getClipboardString();
            	scr.paste(clip);
                return;
            case 19:
            	scr.save();
            	return;
            case 17:
            	scr.exit();
            default:
                switch (par2)
                {
                    case 14:
                    	scr.backspace();
                        break;
                    case 28:
                    case 156:
                    	scr.enter();
                        break;
                    default:
                        if (ChatAllowedCharacters.isAllowedCharacter(character))
                        {
                        	scr.type(character);
                        }
                }
        }
	}
	
}
