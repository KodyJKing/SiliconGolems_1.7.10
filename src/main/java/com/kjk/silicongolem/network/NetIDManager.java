package com.kjk.silicongolem.network;

import java.util.HashMap;
import java.util.Map;

import com.kjk.silicongolem.entity.EntitySGolem;

import net.minecraft.entity.Entity;

public class NetIDManager {
	public static Map<Integer, Entity> Entities = new HashMap<Integer, Entity>();
	
	
	public static Entity remove(Object key) {
		return Entities.remove(key);
	}

	public static Entity get(Object key) {
		return Entities.get(key);
	}

	public static Entity put(Integer key, Entity value) {
		return Entities.put(key, value);
	}

	public static void genNetId(int watcherChannel, Entity e){
		int randId;
		do{
			randId = e.worldObj.rand.nextInt();
		}while(Entities.containsKey(randId));
		e.getDataWatcher().updateObject(watcherChannel, randId);
		Entities.put(randId, e);
	}
}
