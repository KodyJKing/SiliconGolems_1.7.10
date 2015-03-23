package com.kjk.silicongolem;

import org.mozilla.javascript.ContextFactory;

import com.kjk.silicongolem.gui.GuiHandler;
import com.kjk.silicongolem.network.GolemSoureUpdate;
import com.kjk.silicongolem.network.PartialUpdate;
import com.kjk.silicongolem.network.Request;
import com.kjk.silicongolem.network.Update;
import com.kjk.silicongolem.scripting.sandbox.SandboxContextFactory;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class Network {
	
	public static final SimpleNetworkWrapper network = NetworkRegistry.INSTANCE.newSimpleChannel(SGolem.MODID);

	public static void load(){
		
		ContextFactory.initGlobal(new SandboxContextFactory());
		
    	Network.network.registerMessage(GolemSoureUpdate.Handler.class, GolemSoureUpdate.class, 35, Side.SERVER);
    	
    	Network.network.registerMessage(Update.Handler.class, Update.class, 77, Side.SERVER);
    	Network.network.registerMessage(Update.Handler.class, Update.class, 21, Side.CLIENT);
    	
    	Network.network.registerMessage(PartialUpdate.Handler.class, PartialUpdate.class, 84, Side.SERVER);
    	Network.network.registerMessage(PartialUpdate.Handler.class, PartialUpdate.class, 28, Side.CLIENT);
    	
    	Network.network.registerMessage(Request.Handler.class, Request.class, 7, Side.SERVER);
    	
    	NetworkRegistry.INSTANCE.registerGuiHandler(SGolem.INSTANCE, new GuiHandler());
		
	}
	
}
