package com.kjk.silicongolem;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.Random;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ContextFactory;

import com.kjk.silicongolem.entity.EntitySGolem;
import com.kjk.silicongolem.gui.GuiHandler;
import com.kjk.silicongolem.item.ItemDevTool;
import com.kjk.silicongolem.network.GolemSoureUpdate;
import com.kjk.silicongolem.proxy.CommonProxy;
import com.kjk.silicongolem.scripting.APIRegistry;
import com.kjk.silicongolem.scripting.APITest;
import com.kjk.silicongolem.scripting.sandbox.SandboxContextFactory;

import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.event.entity.living.LivingEvent;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;

@Mod(modid = SGolem.MODID, version = SGolem.VERSION, name = "Silicon Golem")
public class SGolem
{
    public static final String MODID = "silicongolem";
    public static final String VERSION = "1.0";
    
    @Instance(MODID)
    public static SGolem instance;
    
    public static Item devTool;
    
    @SidedProxy(clientSide = "com.kjk.silicongolem.proxy.ClientProxy", serverSide = "com.kjk.proxy.silicongolem.CommonProxy")
    public static CommonProxy proxy;
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    }
    
    @EventHandler
    public void preInit(FMLInitializationEvent event)
    {	
    	Network.load();
    	
    	Scripting.load();
    	
    	proxy.registerRendering();
    	registerEntity(EntitySGolem.class, "sgolem", 0xFFFFCC, 0xCCCCA3);
    	
    	devTool = new ItemDevTool();
    	GameRegistry.registerItem(devTool, "devtool");
    }
    
    public static void registerEntity(Class entityClass, String name, int primaryColor, int secondaryColor)
    {
    int entityID = EntityRegistry.findGlobalUniqueEntityId();

    EntityRegistry.registerGlobalEntityID(entityClass, name, entityID);
    EntityRegistry.registerModEntity(entityClass, name, entityID, instance, 80, 3, true);
    EntityList.entityEggs.put(Integer.valueOf(entityID), new EntityList.EntityEggInfo(entityID, primaryColor, secondaryColor));
    }
    
}
