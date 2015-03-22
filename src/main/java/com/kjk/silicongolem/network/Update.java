package com.kjk.silicongolem.network;

import java.io.IOException;

import com.kjk.silicongolem.Network;
import com.kjk.silicongolem.common.Common;
import com.kjk.silicongolem.entity.EntitySGolem;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;

public class Update implements IMessage {

	IStateful destination;
	PacketBuffer packet;
	
	public Update(){
	}
	
	public Update(IStateful destination){
		this.destination = destination;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		PacketBuffer pb = new PacketBuffer(buf);
		try {
			destination = AddressBook.get(pb.readStringFromBuffer(Common.MAX_STRING));
			packet = new PacketBuffer(Unpooled.buffer());
			packet.writeBytes(buf);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		PacketBuffer packet = new PacketBuffer(buf);
		try {
			packet.writeStringToBuffer(destination.getAddress());
			destination.toBytes(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static class Handler implements IMessageHandler<Update, IMessage> {
	    @Override
	    public IMessage onMessage(Update message, MessageContext  ctx) {
	    	try {
				message.destination.fromBytes(message.packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
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
