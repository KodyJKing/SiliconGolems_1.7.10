package com.kjk.silicongolem.scripting;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class TestAPI extends ScriptAPI {

	public TestAPI(Environment env) {
		super(env);
	}
	
	public void explode(){
		Entity owner = env.getOwner();
		World world = owner.worldObj;
		world.createExplosion(owner, owner.posX, owner.posY, owner.posZ, 7.0F, true);
	}
	
	public void print(String msg){
		Entity owner = env.getOwner();
		World world = owner.worldObj;
		for(Object o: world.playerEntities){
			EntityPlayer player = (EntityPlayer)o;
			player.addChatMessage(new ChatComponentText(msg));
		}
	}
	
	public void walk(double speed){
		Entity owner = env.getOwner();
		owner.motionX = (double)(-MathHelper.sin(owner.rotationYaw / 180.0F * (float)Math.PI)) * speed;
		owner.motionZ = (double)(MathHelper.cos(owner.rotationYaw / 180.0F * (float)Math.PI)) * speed;
	}
	
	public double speed(){
		Entity owner = env.getOwner();
		return Math.sqrt(owner.motionX * owner.motionX + owner.motionZ * owner.motionZ);
	}
	
	public void turn(double amount){
		Entity owner = env.getOwner();
		owner.rotationYaw += amount;
	}
	
	public void sleep(long time){
		try {
			env.thread.sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
