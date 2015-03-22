package com.kjk.silicongolem.network;

import java.io.IOException;

import com.kjk.silicongolem.Network;
import com.kjk.silicongolem.common.Common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;

public class PartialUpdate implements IMessage {
	
	IStateful destination;
	PacketBuffer packet;
	
	public PartialUpdate(){}
	
	public PartialUpdate(IStateful destination, PacketBuffer buf){
		this.destination = destination;
		packet = buf;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		PacketBuffer pb = new PacketBuffer(buf);
		try {
			destination = AddressBook.get(pb.readStringFromBuffer(Common.MAX_STRING));
			packet = Common.emptyBuf();
			packet.writeBytes(buf);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void toBytes(ByteBuf buf) {
		PacketBuffer pb = new PacketBuffer(buf);
		try {
			pb.writeStringToBuffer(destination.getAddress());
			pb.writeBytes(packet.array());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static class Handler implements IMessageHandler<PartialUpdate, IMessage> {
	    @Override
	    public IMessage onMessage(PartialUpdate message, MessageContext  ctx) {
	    	message.destination.partialUpdate(message.packet);
	    	if(ctx.side == Side.SERVER){
	    		EntityPlayer sender = ctx.getServerHandler().playerEntity;
	    		World world = sender.worldObj;
	    		for(Object other : world.playerEntities){
	    			EntityPlayer otherPlayer = (EntityPlayer)other;
	    			if(otherPlayer.getEntityId() != sender.getEntityId()){
	    				Network.network.sendTo(message, (EntityPlayerMP) otherPlayer);
	    			}
	    		}
	    	}
	        return null;
	    }
	}

}
