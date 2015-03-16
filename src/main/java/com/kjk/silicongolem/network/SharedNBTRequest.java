package com.kjk.silicongolem.network;

import java.io.IOException;

import com.kjk.silicongolem.SGolem;

import net.minecraft.network.PacketBuffer;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class SharedNBTRequest implements IMessage{
	
	public static class RequestHandler implements IMessageHandler<SharedNBTRequest, IMessage> {
		@Override
		public IMessage onMessage(SharedNBTRequest message, MessageContext ctx) {
			SharedNBT nbt = SharedNBTManager.sharedResources.get(message.netId);
			SharedNBTManager.network.sendTo(new SharedNBTUpdate(nbt), ctx.getServerHandler().playerEntity);
			return null;
		}
	 }

	String netId;
	
	public SharedNBTRequest(){
	}
	
	public SharedNBTRequest(SharedNBT s){
		netId = s.netId;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		PacketBuffer pb = new PacketBuffer(buf);
		
		try {
			netId = pb.readStringFromBuffer(32767);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		PacketBuffer pb = new PacketBuffer(buf);
		
		try {
			pb.writeStringToBuffer(netId);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
