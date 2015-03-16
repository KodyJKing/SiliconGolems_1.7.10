package com.kjk.silicongolem.network;

import java.io.IOException;
import java.util.Queue;

import com.kjk.silicongolem.SGolem;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;

public class SharedNBTUpdate implements IMessage{
	
	public static class UpdateHandler implements IMessageHandler<SharedNBTUpdate, IMessage> {
		@Override
		public IMessage onMessage(SharedNBTUpdate message, MessageContext ctx) {
			SharedNBT nbt = SharedNBTManager.sharedResources.get(message.netId);
			nbt.sharedNBT = message.nbt;
			if(ctx.side == Side.SERVER){
				SharedNBTManager.network.sendToAll(message);
			}
			for(EmptyCallback c: nbt.updateCallbacks){
				c.run();
			}
			nbt.updateCallbacks.clear();
			return null;
		}
	 }

	public String netId;
	public NBTTagCompound nbt;
	
	public SharedNBTUpdate(){
	}
	
	public SharedNBTUpdate(SharedNBT s){
		netId = s.netId;
		nbt = s.sharedNBT;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		PacketBuffer pb = new PacketBuffer(buf);
		try {
			netId = pb.readStringFromBuffer(32767);
			nbt = pb.readNBTTagCompoundFromBuffer(); 
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void toBytes(ByteBuf buf) {
		PacketBuffer pb = new PacketBuffer(buf);
		try {
			pb.writeStringToBuffer(netId);
			pb.writeNBTTagCompoundToBuffer(nbt);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
