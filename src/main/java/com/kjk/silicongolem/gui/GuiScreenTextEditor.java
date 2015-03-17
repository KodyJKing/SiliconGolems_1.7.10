package com.kjk.silicongolem.gui;

import java.util.HashSet;
import java.util.Set;

import org.lwjgl.input.Keyboard;

import scala.actors.threadpool.Arrays;

import com.kjk.silicongolem.SGolem;
import com.kjk.silicongolem.common.Common;
import com.kjk.silicongolem.common.Const;
import com.kjk.silicongolem.entity.EntitySGolem;
import com.kjk.silicongolem.texteditor.TextEditor;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.EnumChatFormatting;

public class GuiScreenTextEditor extends GuiScreenText{
	
	public static EntitySGolem golem;
	
	private TextEditor editor;
	
	private int scroll;
	
    public void initGui()
    {
		editor = new TextEditor(golem.getSource(), this.textWidth);
    	scroll = 0;
        Keyboard.enableRepeatEvents(true);
    }
    
    public void onGuiClosed()
    {
    	golem.setSource(editor.toString());
        Keyboard.enableRepeatEvents(false);
    }
    
 	protected void keyTyped(char character, int par2)
    {
        super.keyTyped(character, par2);
        
        if(Keyboard.isKeyDown(Keyboard.KEY_LEFT)){
        	editor.moveCursor(-1);
        }
        else if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT)){
        	editor.moveCursor(1);
        }
        else if(Keyboard.isKeyDown(Keyboard.KEY_UP)){
        	editor.moveCursorY(-1);
        }
        else if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)){
        	editor.moveCursorY(1);
        }
        else{
        	processTyping(character, par2);
        }
    }
	
	private void processTyping(char character, int par2){
		switch (character)
        {
            case 3:
        	    //copy
            	return;
            case 22:
            	String clip = GuiScreen.getClipboardString();
            	//paste
                return;
            default:
                switch (par2)
                {
                    case 14:
                    	try{
                            //backspace
                    		editor.remove(editor.getCursor() - 1);
                    		editor.moveCursor(-1);
                    	}
                    	catch(Exception e){}
                        break;
                    case 28:
                    case 156:
                    	//newline
                    	editor.insert(editor.getCursor(), "\n");
                    	editor.moveCursor(1);
                        break;
                    default:
                        if (ChatAllowedCharacters.isAllowedCharacter(character))
                        {
                        	//add character
                        	editor.insert(editor.getCursor(), Character.toString(character));
                        	editor.moveCursor(1);
                        }
                }
        }
	}
	
	public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_)
    {
		super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
		clampScroll();
		drawText(editor.toString());
		drawCursor();
    }
	
	protected void drawText(String text){
		int x = 0;
		int y = 0;
		int length = 0;
		int i = 0;
		for(char c : editor.toString().toCharArray()){
			if(y - scroll > this.textHeight){
				break;
			}
			if(c != '\n' && y - scroll >= 0){
				drawChar(x,y - scroll,c, EnumChatFormatting.GREEN);
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
		if(System.currentTimeMillis() % 1000 < 500){
			return;
		}
		drawChar(editor.getCursorX(), editor.getCursorY() - scroll, '_', EnumChatFormatting.DARK_GREEN);
	}
	
	private void clampScroll(){
		scroll = Common.clamp(scroll, editor.getCursorY() - this.textHeight, editor.getCursorY());
	}
}
