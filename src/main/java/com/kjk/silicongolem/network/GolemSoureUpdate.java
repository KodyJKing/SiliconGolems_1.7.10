package com.kjk.silicongolem.network;

import java.io.IOException;

import com.kjk.silicongolem.common.Common;
import com.kjk.silicongolem.entity.EntitySGolem;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ChatComponentText;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class GolemSoureUpdate implements IMessage {

	int entityId;
	String source;
	public GolemSoureUpdate(){
	}
	
	public GolemSoureUpdate(EntitySGolem golem){
		entityId = golem.getEntityId();
		source = golem.getSource();
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		PacketBuffer pb = new PacketBuffer(buf);
		try {
			entityId = pb.readInt();
			source = pb.readStringFromBuffer(Common.MAX_STRING);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		PacketBuffer pb = new PacketBuffer(buf);
		try {
			pb.writeInt(entityId);
			pb.writeStringToBuffer(source);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static class Handler implements IMessageHandler<GolemSoureUpdate, IMessage> {
	    @Override
	    public IMessage onMessage(GolemSoureUpdate message, MessageContext  ctx) {
	    	EntityPlayer player =  ctx.getServerHandler().playerEntity;
	    	EntitySGolem golem = (EntitySGolem) player.worldObj.getEntityByID(message.entityId);
	    	golem.setSource(message.source);
	        return null;
	    }
	}

}
