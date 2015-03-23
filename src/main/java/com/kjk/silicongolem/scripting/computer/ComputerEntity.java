package com.kjk.silicongolem.scripting.computer;

import com.kjk.silicongolem.scripting.Computer;

import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;

public class ComputerEntity extends Computer{

	private Entity owner;
	
	public Entity getOwner() {
		return owner;
	}

	public void setOwner(Entity owner) {
		this.owner = owner;
	}
	
	public World getWorld(){
		return owner.worldObj;
	}

}
