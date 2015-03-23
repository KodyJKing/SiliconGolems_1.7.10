package com.kjk.silicongolem.network;

import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;

import com.kjk.silicongolem.Network;
import com.kjk.silicongolem.common.Common;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;

public class Request implements IMessage {

	IStateful destination;
	
	public Request(){}
	
	public Request(IStateful destination){
		this.destination = destination;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		PacketBuffer pb = new PacketBuffer(buf);
		try {
			destination = AddressBook.get(pb.readStringFromBuffer(Common.MAX_STRING));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		PacketBuffer pb = new PacketBuffer(buf);
		try {
			pb.writeStringToBuffer(destination.getAddress());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static class Handler implements IMessageHandler<Request, IMessage> {
	    @Override
	    public IMessage onMessage(Request message, MessageContext  ctx) {
	    	Network.network.sendTo(new Update(message.destination), ctx.getServerHandler().playerEntity);
	        return null;
	    }
	}

}
