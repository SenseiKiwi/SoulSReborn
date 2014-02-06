package SoulSReborn.configs;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;

import net.minecraftforge.common.Configuration;
import SoulSReborn.utils.DynamicMobMapping;
import SoulSReborn.utils.SoulLogger;

public class MobBlacklist 
{
	
	public static HashMap<String, Boolean> map = new HashMap();
	
	public static void init(File configFile)
	{
		Configuration config = new Configuration(configFile);
		
		try
		{
			config.load();
			Iterator<String> iter = DynamicMobMapping.entityList.iterator();
			while (iter.hasNext())
			{
				String name = iter.next();
				boolean val = config.get("Mob Blacklist",  name, false).getBoolean(false);
				map.put(name, val);
			}
			
			SoulLogger.log(Level.INFO, "Loaded Entity Blacklist configuration.");
		}
		
		catch(Exception e)
		{
			SoulLogger.log(Level.INFO, "Soul Shards Reborn had a problem loading the entity blacklist config!");
		}
		
		finally
		{
			if (config.hasChanged())
				config.save();
		}
	}
}
