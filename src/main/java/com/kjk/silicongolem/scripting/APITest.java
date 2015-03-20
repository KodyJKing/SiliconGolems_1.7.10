package com.kjk.silicongolem.scripting;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class APITest extends API {

	public APITest(Computer env, String name) {
		super(env, name);
	}

//	public void explode(){
//		lockThread();
//		Entity owner = env.getOwner();
//		World world = owner.worldObj;
//		world.createExplosion(null, owner.posX, owner.posY, owner.posZ, 7.0F, true);
//		unlockThread();
//	}
	
	public void print(String msg){
		lockThread();
		World world = comp.getWorld();
		for(Object o: world.playerEntities){
			EntityPlayer player = (EntityPlayer)o;
			player.addChatMessage(new ChatComponentText(msg));
		}
		unlockThread();
	}
	
//	public void walk(double speed){
//		lockThread();
//		Entity owner = env.getOwner();
//		owner.motionX = (double)(-MathHelper.sin(owner.rotationYaw / 180.0F * (float)Math.PI)) * speed;
//		owner.motionZ = (double)(MathHelper.cos(owner.rotationYaw / 180.0F * (float)Math.PI)) * speed;
//		unlockThread();
//	}
//	
//	public double speed(){
//		lockThread();
//		Entity owner = env.getOwner();
//		unlockThread();
//		return Math.sqrt(owner.motionX * owner.motionX + owner.motionZ * owner.motionZ);
//	}
//	
//	public void turn(double amount){
//		lockThread();
//		Entity owner = env.getOwner();
//		owner.rotationYaw += amount;
//		unlockThread();
//	}
	
	public void sleep(long time){
		lockThread();
		try {
			comp.thread.sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		unlockThread();
	}
}
