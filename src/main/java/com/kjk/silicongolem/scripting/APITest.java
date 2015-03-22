package com.kjk.silicongolem.scripting;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class APITest extends API {
	
	public APITest(Computer comp) {
		super(comp);
	}

	public void print(String msg){
		lockThread();
		World world = comp.getWorld();
		for(Object o: world.playerEntities){
			EntityPlayer player = (EntityPlayer)o;
			player.addChatMessage(new ChatComponentText(msg));
		}
		unlockThread();
	}
	
	public void sleep(long time){
		lockThread();
		try {
			comp.thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		unlockThread();
	}
}
