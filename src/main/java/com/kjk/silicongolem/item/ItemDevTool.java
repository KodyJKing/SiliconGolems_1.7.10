package com.kjk.silicongolem.item;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import com.kjk.silicongolem.SGolem;
import com.kjk.silicongolem.entity.EntitySGolem;
import com.kjk.silicongolem.gui.GuiScreenTextEditor;

import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

public class ItemDevTool extends Item {
	
	public static String name = "devtool";
	
	public ItemDevTool(){
		this.setUnlocalizedName(SGolem.MODID + "_" + name);
		this.setTextureName(SGolem.MODID + ":" + name);
		this.setCreativeTab(CreativeTabs.tabTools);
		this.setMaxStackSize(1);
	}
	
	public boolean hasEffect(ItemStack item, int pass){
		return true;
	}
	
	@Override
    public ItemStack onItemRightClick(ItemStack item, World world, EntityPlayer player)
    {	
		return item;
    }
	
    @Override
    public boolean itemInteractionForEntity(ItemStack itemstack, EntityPlayer player, EntityLivingBase entity)
    {
        if (entity.worldObj.isRemote)
        {
        	if(entity instanceof EntitySGolem){
        		openTextEditor(player.worldObj, player, (EntitySGolem) entity);
        	}
            return true;
        }
        return false;
    }
	
	public void openTextEditor(World world, EntityPlayer player, EntitySGolem golem){
		player.openGui(SGolem.instance, 0, world, 0,0,0);
	}
	
}
