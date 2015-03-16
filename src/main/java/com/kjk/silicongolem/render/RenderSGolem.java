package com.kjk.silicongolem.render;

import com.kjk.silicongolem.SGolem;
import com.kjk.silicongolem.entity.EntitySGolem;
import com.kjk.silicongolem.model.ModelSGolem;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderSGolem extends RenderBiped {
	
	private static final ResourceLocation golemHappy = new ResourceLocation(SGolem.MODID + ":" + "textures/models/silicon_golem_happy.png");
	private static final ResourceLocation golemNeutral = new ResourceLocation(SGolem.MODID + ":" + "textures/models/silicon_golem_neutral.png");
	private static final ResourceLocation golemMad = new ResourceLocation(SGolem.MODID + ":" + "textures/models/silicon_golem_mad.png");

	public RenderSGolem(ModelSGolem modelSiliconGolem, float p_i1257_2_) {
		super(modelSiliconGolem, p_i1257_2_);
		// TODO Auto-generated constructor stub
	}
	
	//@Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		EntitySGolem golem = (EntitySGolem)entity;
		if(golem.attackTime > 0 || golem.hurtTime > 0){
			return golemMad;
		}
		return golemNeutral;
	}

}
