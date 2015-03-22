package com.kjk.silicongolem.item;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import com.kjk.silicongolem.SGolem;
import com.kjk.silicongolem.common.Common;
import com.kjk.silicongolem.entity.EntitySGolem;
import com.kjk.silicongolem.gui.GuiHandler;
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
	
	public static int guiMode = 1;
	
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
		if(!world.isRemote){
			return item;
		}
//		guiMode *= -1;
//		Common.msg(player, "Gui mode: " + guiMode);
		return item;
    }
	
    @Override
    public boolean itemInteractionForEntity(ItemStack itemstack, EntityPlayer player, EntityLivingBase entity)
    {
        if (entity.worldObj.isRemote)
        {
        	if(player.isSneaking() &&entity instanceof EntitySGolem){
        		EntitySGolem golem = (EntitySGolem) entity;
        		openTextEditor(player.worldObj, player, golem);
        	}
            return true;
        }
        else
        {
        	if(!player.isSneaking() && entity instanceof EntitySGolem){
    		   EntitySGolem golem = (EntitySGolem) entity;
    		   golem.runSource();
    	    }        	
        }

        return false;
    }
	
	public void openTextEditor(final World world, final EntityPlayer player, EntitySGolem golem){
		GuiHandler.setOpenGolem(golem);
		player.openGui(SGolem.instance, guiMode, world, 0,0,0);
	}
	
}
