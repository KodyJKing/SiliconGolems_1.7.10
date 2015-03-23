package com.kjk.silicongolem.scripting.computer;

import java.io.IOException;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;

import com.kjk.silicongolem.common.Common;
import com.kjk.silicongolem.network.PartialUpdate;
import com.kjk.silicongolem.scripting.Computer;

public class ComponentDrive extends Component {
	
	NBTTagCompound drive;

	public ComponentDrive(Computer comp) {
		super(comp);
		drive = new NBTTagCompound();
	}
	
	@Override
	public void onLoad() {
		requestUpdate();
	}

	public void addAPI(){
		api = new APIDrive(this);
		computer.addAPI(api, "drive");
	}
	
	public String readFile(String name){
		return drive.hasKey(name) ? drive.getString(name) : "";
	}
	
	public void appendFile(String name, String text){
		if(!drive.hasKey(name)){
			drive.setString(name, text);
		}
		else
		{
			String oldFile = drive.getString(name);
			drive.setString(name, oldFile + text);
		}
	}
	
	public void sendAPPEND(String name, String text) throws IOException{
		PacketBuffer packet = Common.emptyBuf();
		packet.writeByte(0);//APPEND
		packet.writeStringToBuffer(name);
		packet.writeStringToBuffer(text);
		computer.sendPartialUpdate(new PartialUpdate(this, packet));
	}
	
	public void recieveAPPEND(PacketBuffer buf) throws IOException{
		System.out.println("Drive recieved APPEND: " + getAddress());
		String name = buf.readStringFromBuffer(Common.MAX_STRING);
		String text = buf.readStringFromBuffer(Common.MAX_STRING);
		System.out.println("Name: " + name);
		System.out.println("Text: " + text);
		appendFile(name, text);
		System.out.println(drive);
	}
	
	public boolean clearFile(String name){
		if(drive.hasKey(name)){
			drive.removeTag(name);
			return true;
		}
		return false;
	}
	
	public void sendCLEAR(String name) throws IOException{
		PacketBuffer packet = Common.emptyBuf();
		packet.writeByte(1);
		packet.writeStringToBuffer(name);
		computer.sendPartialUpdate(new PartialUpdate(this, packet));
	}
	
	public void recieveCLEAR(PacketBuffer buf) throws IOException{
		System.out.println("Drive recieved CLEAR");
		clearFile(buf.readStringFromBuffer(Common.MAX_STRING));
	}
	
	public void partialUpdate(PacketBuffer buf){
		Byte type = buf.readByte();
		try {
			switch (type) {
			case 0: //APPEND
				recieveAPPEND(buf);
				return;
			case 1: //CLEAR
				recieveCLEAR(buf);
				return;
			default:
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void fromBytes(PacketBuffer buf) throws IOException {
		System.out.println("Drive recieved ALL");
		NBTTagCompound nbt = buf.readNBTTagCompoundFromBuffer();
		readNBT(nbt);
	}

	@Override
	public void toBytes(PacketBuffer buf) throws IOException {
		NBTTagCompound nbt = new NBTTagCompound();
		writeNBT(nbt);
		buf.writeNBTTagCompoundToBuffer(nbt);
	}

	@Override
	public void readNBT(NBTTagCompound nbt) {
		drive = nbt.getCompoundTag("drive");
	}

	@Override
	public void writeNBT(NBTTagCompound nbt) {
		System.out.println("Writing drive NBT: " + getAddress());
		nbt.setTag("drive", drive);
	}
	
	
	
}
