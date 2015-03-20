package com.kjk.silicongolem.gui;

import com.kjk.silicongolem.entity.EntitySGolem;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {
	
	private static EntitySGolem openGolem;

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		if(ID == -1){
			return new GuiScreenConsole(getOpenGolem().comp);
		}
		return new GuiScreenTextEditor(getOpenGolem());
	}

	public static EntitySGolem getOpenGolem() {
		return openGolem;
	}

	public static void setOpenGolem(EntitySGolem golem) {
		openGolem = golem;
	}

}
