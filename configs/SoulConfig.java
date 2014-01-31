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
	public static boolean canAbsorbSpawners;
	public static int soulShardID;
	public static int soulCageID;
	public static int soulStealerID;
	public static int maxNumSpawns;
	public static int coolDown[] = new int[5];
	public static int numMobs[] = new int[5];
	public static HashMap<String, Boolean> blacklistMap = new HashMap<String, Boolean>();
	
	public static void init(File configFile)
	{
		Configuration config = new Configuration(configFile);
		
		try
		{
			config.load();
			autoID = config.get("IDs", "Enable the mod's dynamic ID system", true).getBoolean(true);
			disallowMobs = config.get("Misc", "Set soul shards to accept only peaceful mobs", false).getBoolean(false);
			canAbsorbSpawners = config.get("Misc", "Allow levelling up shards by absorbing vanilla spawners", true).getBoolean(true);
			maxNumSpawns = config.get("Misc", "The max amount of mobs spawned by Soul Cages that can be alive at once (setting this to 0 sets it to unlimited)", 80).getInt(80);
			soulShardID = config.get("IDs", "Soul Shard item ID (ignored if dynamic ID system is on)", 4097).getInt(4097);
			soulCageID = config.get("IDs", "Soul Cage block ID (ignored if dynamic ID system is on)", 1000).getInt(1000);
			soulStealerID = config.get("IDs", "Soul Stealer enchant ID", 85).getInt();
			
			coolDown[0] = config.get("Tier 1 Settings", "Cool-down (in seconds)", 20).getInt(20);
			numMobs[0] = config.get("Tier 1 Settings", "Number of mobs to spawn", 2).getInt(2);
			coolDown[1] = config.get("Tier 2 Settings", "Cool-down (in seconds)", 10).getInt(10);
			numMobs[1] = config.get("Tier 2 Settings", "Number of mobs to spawn", 4).getInt(4);
			coolDown[2] = config.get("Tier 3 Settings", "Cool-down (in seconds)", 5).getInt(5);
			numMobs[2] = config.get("Tier 3 Settings", "Number of mobs to spawn", 4).getInt(4);
			coolDown[3] = config.get("Tier 4 Settings", "Cool-down (in seconds)", 5).getInt(5);
			numMobs[3] = config.get("Tier 4 Settings", "Number of mobs to spawn", 4).getInt(4);
			coolDown[4] = config.get("Tier 5 Settings", "Cool-down (in seconds)", 2).getInt(2);
			numMobs[4] = config.get("Tier 5 Settings", "Number of mobs to spawn", 6).getInt(6);
			
			if (maxNumSpawns > 150)
				maxNumSpawns = 80;
			
			for (int i = 0; i < coolDown.length; i++)
			{
				if (coolDown[i] < 2)
					coolDown[i] = 2;
				if (coolDown[i] > 60)
					coolDown[i] = 60;
			}
			
			for (int i = 0; i < numMobs.length; i++)
			{
				if (numMobs[i] < 1)
					numMobs[i] = 1;
				if (numMobs[i] > 6)
					numMobs[i] = 6;
			}

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
