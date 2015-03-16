package com.kjk.silicongolem.common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;

public class Common {
	
	public static int clamp(int val, int min, int max){
		if(val < min){
			return min;
		}
		if(val > max){
			return max;
		}
		return val;
	}
	
	public static void msg(EntityPlayer player, String msg){
		player.addChatMessage(new ChatComponentText(msg));
	}
	
	public static void broadcast(String msg){
		MinecraftServer.getServer().addChatMessage(new ChatComponentText(msg));
	}
	
}
