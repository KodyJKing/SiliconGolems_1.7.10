package com.kjk.silicongolem.entity;

import java.util.HashMap;
import java.util.Map;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import com.kjk.silicongolem.SGolem;
import com.kjk.silicongolem.network.NetIDManager;
import com.kjk.silicongolem.scripting.Environment;

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
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntitySGolem extends EntityGolem {
	
	static int SOURCE_CHANNEL = 31;
	static int NET_ID_CHANNEL = 12;
	static String SOURCE_TAG = "source";
	
	public Environment env;
	
	
	public EntitySGolem(World world) {
		super(world);
		
		env = new Environment();
		env.setOwner(this);
		
		dataWatcher.addObject(NET_ID_CHANNEL, 0);
		dataWatcher.addObject(SOURCE_CHANNEL, "");
		setSource("test.explode()");
		
		if(!hasCustomNameTag()){
			setCustomNameTag("Ted " + (worldObj.rand.nextInt() % 1000 + 2000));
		}
		
		if(!worldObj.isRemote){
			NetIDManager.genNetId(12, this);
		}
		this.getEntityId();
	}
	
	public void runSource(){
		env.run(getSource());
	}
	

	public int getNetId() {
		return dataWatcher.getWatchableObjectInt(NET_ID_CHANNEL);
	}

	private void setNetId(int netId) {
		dataWatcher.updateObject(NET_ID_CHANNEL, netId);
	}
	
	public String getSource() {
		return dataWatcher.getWatchableObjectString(SOURCE_CHANNEL);
	}

	public void setSource(String source) {
		dataWatcher.updateObject(SOURCE_CHANNEL, source);
	}
	

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt){
		super.readEntityFromNBT(nbt);
		setSource(nbt.getString(SOURCE_TAG));
		System.out.println("Read Golem NBT: " + nbt.toString());
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound nbt){
		super.writeEntityToNBT(nbt);
		nbt.setString(SOURCE_TAG, getSource());
		System.out.println("Write Golem NBT: " + nbt.toString());
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
    	this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(1.0D);
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
    
    @Override
    public void onDeath(DamageSource ds){
    	super.onDeath(ds);
    	NetIDManager.remove(getNetId());
    }
    
    /**
     * Returns the sound this mob makes when it is hurt.
     */
    protected String getHurtSound()
    {
        return "mob.irongolem.hit";
    }

    /**
     * Returns the sound this mob makes on death.
     */
    protected String getDeathSound()
    {
        return "mob.irongolem.death";
    }
    
    protected void func_145780_a(int p_145780_1_, int p_145780_2_, int p_145780_3_, Block p_145780_4_)
    {
        this.playSound("mob.irongolem.walk", 1.0F, 1.0F);
    }
}
