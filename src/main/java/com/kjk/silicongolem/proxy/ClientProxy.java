package com.kjk.silicongolem.proxy;

import net.minecraft.client.model.ModelBiped;

import com.kjk.silicongolem.entity.EntitySGolem;
import com.kjk.silicongolem.model.ModelSGolem;
import com.kjk.silicongolem.render.RenderSGolem;

import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy{
	
	@Override
	public void registerRendering(){
		RenderingRegistry.registerEntityRenderingHandler(EntitySGolem.class, new RenderSGolem(new ModelSGolem(), 0.5F));
	}
}
