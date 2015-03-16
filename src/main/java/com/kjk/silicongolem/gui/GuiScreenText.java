package com.kjk.silicongolem.gui;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.kjk.silicongolem.SGolem;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public abstract class GuiScreenText extends GuiScreen {
	private static final ResourceLocation textGuiTextures = new ResourceLocation(SGolem.MODID, "textures/gui/text_editor.png");
	
    private int editorWidth = 248;
    private int editorHeight = 166;
    private int boarderWidth = 8;
    protected int textWidth = 37;
    protected int textHeight = 17;
    private int charWidth = 6;
    private int charHeight = 8;
    
    private int cornerX(){
    	return (this.width - this.editorWidth) / 2;
    }
    
    private int cornerY(){
    	return 2;
    }
    
    private int textCornerX(){
    	return cornerX() + boarderWidth;
    }
    
    private int textCornerY(){
    	return cornerY() + boarderWidth;
    }
    
    protected int cellX(int textX){
    	return textCornerX() + textX * charWidth;
    }
    
    protected int cellY(int textY){
    	return textCornerY() + textY * charHeight;
    }
    
    protected void drawChar(int x, int y, char c){
		drawChar(x, y, c, EnumChatFormatting.WHITE);
    }
    
    protected void drawChar(int x, int y, char c, EnumChatFormatting color){
    	int xAdjust = isThin(c) ? 2 : 0;
		this.fontRendererObj.drawString(
				color + Character.toString(c),
				cellX(x) + xAdjust,
				cellY(y),
				8);
    }
    
	protected boolean isThin(char c) {
		return "il|!t".contains(Character.toString(c));
	}
    
	public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_)
	{
	    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	    this.mc.getTextureManager().bindTexture(textGuiTextures);
	    int x = (this.width - this.editorWidth) / 2;
	    int y = 2;
	    this.drawTexturedModalRect(x, y, 0, 0, this.editorWidth, this.editorHeight);
	        
	    super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
	 }
}
