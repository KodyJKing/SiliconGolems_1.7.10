package com.kjk.silicongolem.common;

import io.netty.buffer.Unpooled;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;

public class Common {
	
	public static int MAX_STRING = 32767;
	
	public static PacketBuffer emptyBuf(){
		return new PacketBuffer(Unpooled.buffer());
	}
	
	public static boolean blink(int period, int duration){
		return System.currentTimeMillis() % period < duration;
	}
	
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
