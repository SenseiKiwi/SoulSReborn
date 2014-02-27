package SoulSReborn.configs;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;

import net.minecraftforge.common.Configuration;
import SoulSReborn.utils.DynamicMobMapping;
import SoulSReborn.utils.SoulLogger;

public class MobBlacklist 
{
	public static List<String>list = new ArrayList();
	
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
				if (val) list.add(name);
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
