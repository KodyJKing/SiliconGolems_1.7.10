package com.kjk.silicongolem.entity;

import java.util.HashMap;
import java.util.Map;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import com.kjk.silicongolem.SGolem;
import com.kjk.silicongolem.gui.GuiHandler;
import com.kjk.silicongolem.scripting.Computer;
import com.kjk.silicongolem.scripting.computer.ComputerEntity;

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
	static String SOURCE_TAG = "source";
	
	public ComputerEntity comp;	
	
	public EntitySGolem(World world) {
		super(world);
		
		comp = new ComputerEntity();
		comp.setOwner(this);
		comp.setAddress(Integer.toString(this.getEntityId()));
		System.out.println("Loaded computer with id: " + comp.getAddress());
		comp.onLoad();
		
		dataWatcher.addObject(SOURCE_CHANNEL, "");
		setSource("");
		
		if(!hasCustomNameTag()){
			setCustomNameTag("Ted " + (worldObj.rand.nextInt() % 9000 + 1000));
		}
		
		this.getEntityId();
	}
	
	public void runSource(){
		comp.run(getSource());
	}
	
	public String getSource() {
		return dataWatcher.getWatchableObjectString(SOURCE_CHANNEL);
	}

	public void setSource(String source) {
		dataWatcher.updateObject(SOURCE_CHANNEL, source);
	}
	
	public boolean interact(EntityPlayer player){
		if(player.worldObj.isRemote && (player.getHeldItem() == null || player.getHeldItem().getItem() != SGolem.devTool)){
			GuiHandler.setOpenGolem(this);
			player.openGui(SGolem.INSTANCE, 1, player.worldObj, (int)player.posX, (int)player.posY, (int)player.posZ);
			return true;
		} else{
			return super.interact(player);
		}
	}
	

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt){
		super.readEntityFromNBT(nbt);
		setSource(nbt.getString(SOURCE_TAG));
		comp.readNBT(nbt);
		System.out.println("Read Golem NBT: " + nbt.toString());
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound nbt){
		super.writeEntityToNBT(nbt);
		nbt.setString(SOURCE_TAG, getSource());
		comp.writeNBT(nbt);
		System.out.println("Write Golem NBT: " + nbt.toString());
	}
	
	// BOILER PLATE:
	
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
    	comp.kill();
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
