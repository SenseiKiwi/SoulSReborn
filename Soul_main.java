package SoulSReborn;

import java.util.logging.Level;

import SoulSReborn.configs.SoulConfig;
import SoulSReborn.event.EventHandler;
import SoulSReborn.gameObjs.ObjHandler;
import SoulSReborn.proxies.CommonProxy;
import SoulSReborn.utils.SoulLogger;
import SoulSReborn.utils.SoulPacket;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid = "SSR", name = "Soul Shards Reborn", version = "Alpha 0.4a")
@NetworkMod(clientSideRequired = true, serverSideRequired = false, 
		channels = {"SoulShardsReborn"}, packetHandler = SoulPacket.class)

public class Soul_main 
{
	@Instance("SSR")
	public static Soul_main instance;
	
	@SidedProxy( clientSide = "SoulSReborn.proxies" + ".ClientProxy", serverSide = "SoulSReborn.proxies" + ".CommonProxy" )
	public static CommonProxy proxy;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		SoulLogger.init();
		SoulLogger.log(Level.INFO, "Loading mod.");
		SoulConfig.init(event.getSuggestedConfigurationFile());
		proxy.initRenderers();
		EventHandler.init();
		ObjHandler.init();
	}
	 
	@Mod.EventHandler
	public void load(FMLInitializationEvent event)
	{
		SoulLogger.log(Level.INFO, "Mod loaded.");
	}
}
