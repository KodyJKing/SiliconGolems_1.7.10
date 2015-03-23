package com.kjk.silicongolem.scripting.computer;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import net.minecraft.network.PacketBuffer;

import com.kjk.silicongolem.common.Common;
import com.kjk.silicongolem.network.AddressBook;
import com.kjk.silicongolem.network.IStateful;
import com.kjk.silicongolem.scripting.Computer;

public class ComponentConsole extends Component {

	List<String> console;

	public ComponentConsole(ComputerConsole comp){
		super(comp);
		console = new LinkedList<String>();
	}
	
	void addAPI(){
		api = new APIConsole(this);
		computer.addAPI(api, "console");
	}
	public void add(String e) {
		console.add(e);
		if(console.size() > 50){
			console.remove(0);
		}
	}

	public void clear() {
		console.clear();
	}

	public String get(int index) {
		return console.get(index);
	}

	public int size() {
		return console.size();
	}
	
	public void sendALL(){
		computer.sendUpdate(this);
	}

	@Override
	public void fromBytes(PacketBuffer buf) throws IOException {
		int size = buf.readInt();
		console.clear();
		for(int i = 0; i < size; i++){
			String line = buf.readStringFromBuffer(Common.MAX_STRING);
			console.add(line);
		}
	}

	@Override
	public void toBytes(PacketBuffer buf) throws IOException {
		buf.writeInt(console.size());
		for(String line : console){
			buf.writeStringToBuffer(line);
		}
	}

	@Override
	public void partialUpdate(PacketBuffer buf) {
		
	}

}
