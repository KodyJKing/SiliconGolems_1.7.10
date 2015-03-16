package com.kjk.silicongolem;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.Random;

import org.mozilla.javascript.Context;

import com.kjk.silicongolem.entity.EntitySGolem;
import com.kjk.silicongolem.gui.GuiHandler;
import com.kjk.silicongolem.item.ItemDevTool;
import com.kjk.silicongolem.network.SharedNBTManager;
import com.kjk.silicongolem.network.SharedNBTRequest;
import com.kjk.silicongolem.network.SharedNBTUpdate;
import com.kjk.silicongolem.proxy.CommonProxy;
import com.kjk.silicongolem.scripting.APIList;

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
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
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
    
    public static APIList golemAPI;
    
    @SidedProxy(clientSide = "com.kjk.silicongolem.proxy.ClientProxy", serverSide = "com.kjk.proxy.silicongolem.CommonProxy")
    public static CommonProxy proxy;
    
    public static final SimpleNetworkWrapper network = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);
    
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    }
    
    @EventHandler
    public void preInit(FMLInitializationEvent event)
    {	
    	network.registerMessage(SharedNBTRequest.RequestHandler.class, SharedNBTRequest.class, 0, Side.SERVER);
    	network.registerMessage(SharedNBTUpdate.UpdateHandler.class, SharedNBTUpdate.class, 0, Side.SERVER);
    	network.registerMessage(SharedNBTUpdate.UpdateHandler.class, SharedNBTUpdate.class, 0, Side.CLIENT);
    	
    	SharedNBTManager.network = network;

    	NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
    	
    	proxy.registerRendering();
    	
    	golemAPI = new APIList();
    	
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
