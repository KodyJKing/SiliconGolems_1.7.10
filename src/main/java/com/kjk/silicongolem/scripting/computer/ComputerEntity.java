package com.kjk.silicongolem.scripting.computer;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class ComputerEntity extends ComputerConsole{

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
