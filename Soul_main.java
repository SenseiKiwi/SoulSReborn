package SoulSReborn;

import java.io.File;
import java.util.logging.Level;

import SoulSReborn.configs.MobBlacklist;
import SoulSReborn.configs.SoulConfig;
import SoulSReborn.event.EventHandler;
import SoulSReborn.gameObjs.ObjHandler;
import SoulSReborn.proxies.CommonProxy;
import SoulSReborn.utils.DynamicMobMapping;
import SoulSReborn.utils.EntityWhitelist;
import SoulSReborn.utils.SoulLogger;
import SoulSReborn.utils.SoulPacket;
import SoulSReborn.utils.TierHandling;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod(modid = "SSR", name = "Soul Shards Reborn", version = "Alpha 0.7a")
@NetworkMod(clientSideRequired = true, serverSideRequired = false, 
		channels = {"SoulShardsReborn"}, packetHandler = SoulPacket.class)

public class Soul_main 
{
	@Instance("SSR")
	public static Soul_main instance;
	
	@SidedProxy( clientSide = "SoulSReborn.proxies" + ".ClientProxy", serverSide = "SoulSReborn.proxies" + ".CommonProxy" )
	public static CommonProxy proxy;
	
	String configDir;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		SoulLogger.init();
		SoulLogger.log(Level.INFO, "Loading mod.");
		configDir = event.getModConfigurationDirectory() + "/Soul Shards Reborn/";
		SoulConfig.init(new File(configDir + "Main.cfg"));
		TierHandling.init();
		EntityWhitelist.init();
	}
	 
	@Mod.EventHandler
	public void load(FMLInitializationEvent event)
	{
		ObjHandler.init();
		EventHandler.init();
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		DynamicMobMapping.init();
		SoulLogger.log(Level.INFO, "Mapped a total of "+DynamicMobMapping.entityList.size()+" entities.");
		MobBlacklist.init(new File(configDir + "Entity Blacklist.cfg"));
		SoulLogger.log(Level.INFO, "Mod loaded.");
	}
}
