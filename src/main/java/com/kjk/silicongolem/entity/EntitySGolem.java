package com.kjk.silicongolem.entity;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import com.kjk.silicongolem.SGolem;
import com.kjk.silicongolem.network.SharedNBT;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntitySGolem extends EntityGolem {
	
	public EntitySGolem(World world) {
		super(world);
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound nbt){
		super.readEntityFromNBT(nbt);
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound nbt){
		super.writeEntityToNBT(nbt);
	}
	
	public void addTestAI(){
		this.getNavigator().setAvoidsWater(true);
		this.tasks.addTask(0 , new EntityAISwimming(this));
		this.tasks.addTask(2 , new EntityAIAttackOnCollide(this, EntityLivingBase.class, 1.0D, false));
		this.tasks.addTask(3, new EntityAIWander(this, 1.0D));
		this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityLivingBase.class, 8.0F));
		this.tasks.addTask(4, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityLivingBase.class, 0, true));
	}
	
    public boolean isAIEnabled()
    {
        return true;
    }
    
    public boolean canAttackClass(Class p_70686_1_)
    {
        return true;
    }

    
    public void applyEntityAttributes(){
    	super.applyEntityAttributes();
    	this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(80.0D);
    	this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
    }
    
    public void onLivingUpdate()
    {
        super.onLivingUpdate();

        if (this.attackTime > 0)
        {
            --this.attackTime;
        }
        
    }
    
    @SideOnly(Side.CLIENT)
    public void handleHealthUpdate(byte p_70103_1_)
    {
        if (p_70103_1_ == 4)
        {
            attackTime = 20;
            this.playSound("mob.irongolem.throw", 1.0F, 1.0F);
        }
        else{
        	super.handleHealthUpdate(p_70103_1_);
        }
    }
    
    public boolean attackEntityAsMob(Entity entity)
    {
    	attackTime = 20;
        this.worldObj.setEntityState(this, (byte)4);
        boolean flag = entity.attackEntityFrom(DamageSource.causeMobDamage(this), (float)(7 + this.rand.nextInt(15)));

        if (flag)
        {
            entity.motionY += 0.4000000059604645D;
        }

        this.playSound("mob.irongolem.throw", 1.0F, 1.0F);
        return flag;
    }
}
