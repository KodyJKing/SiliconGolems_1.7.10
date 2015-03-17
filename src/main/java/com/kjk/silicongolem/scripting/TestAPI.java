package com.kjk.silicongolem.scripting;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class TestAPI extends ScriptAPI {

	public TestAPI(Environment env) {
		super(env);
	}
	
	public void explode(){
		Entity owner = env.getOwner();
		World world = owner.worldObj;
		world.createExplosion(owner, owner.posX, owner.posY, owner.posZ, 7.0F, true);
	}
}
