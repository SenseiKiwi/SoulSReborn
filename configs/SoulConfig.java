package SoulSReborn.configs;

import java.io.File;
import java.util.HashMap;
import java.util.logging.Level;

import net.minecraftforge.common.Configuration;
import SoulSReborn.utils.EntityWhitelist;
import SoulSReborn.utils.SoulLogger;

public class SoulConfig 
{
	public static boolean autoID;
	public static boolean disallowMobs;
	public static int soulShardID;
	public static int soulCageID;
	public static int soulStealerID;
	public static HashMap<String, Boolean> blacklistMap = new HashMap<String, Boolean>();
	
	public static void init(File configFile)
	{
		Configuration config = new Configuration(configFile);
		
		try
		{
			config.load();
			autoID = config.get("IDs", "Enable the mod's dynamic ID system", true).getBoolean(true);
			disallowMobs = config.get("Misc", "Set soul shards to accept only peaceful mobs", false).getBoolean(false);
			soulShardID = config.get("IDs", "Soul Shard item ID (ignored if dynamic ID system is on)", 4097).getInt();
			soulCageID = config.get("IDs", "Soul Cage block ID (ignored if dynamic ID system is on)", 1000).getInt();
			soulStealerID = config.get("IDs", "Soul Stealer enchant ID", 85).getInt();
			
			/**Trying to create config for other mod's mobs. Does not work yet.**/
			/**EDIT will probably change to another method, as this requires other mods 
			 * to be loaded before mine.**/
			/*Iterator<String> entIter = EntityList.stringToClassMapping.keySet().iterator();
			while (entIter.hasNext())
			{
				String entName = entIter.next();
				Class ent =  (Class) EntityList.stringToClassMapping.get(entName);
				boolean isLiving = EntityLiving.class.isAssignableFrom(ent);
				boolean val;
				if (entName != null && isLiving)
				{
					if (entName.contains("Dragon") || entName.contains("Boss") || entName.equals("Mob"))
						continue;
					else
					{
						val = config.get("Entity Blacklist", entName, false).getBoolean(false);
						blacklistMap.put(entName, val);
					}
				}
				else
					SoulLogger.log(Level.INFO, "Skipping Entity: "+entName);
				val = config.get("Entity Blacklist", "Wither Skeleton", false).getBoolean(false);
				blacklistMap.put("Wither Skeleton", val);
			}*/
			for (String string : EntityWhitelist.peacefuls)
			{
				boolean val = config.get("Entity Blacklist", string, false).getBoolean(false);
				blacklistMap.put(string, val);
			}
			for (String string : EntityWhitelist.mobs)
			{
				boolean val = config.get("Entity Blacklist", string, false).getBoolean(false);
				blacklistMap.put(string, val);
			}
			SoulLogger.log(Level.INFO, "Loaded configuration files.");
		}
		catch(Exception e)
		{
			SoulLogger.log(Level.SEVERE, "Soul-Shards Reborn had a problem loading it's configuration files.");
			e.printStackTrace();
		}
		finally
		{
			if (config.hasChanged())
				config.save();
		}
		
	}
}
