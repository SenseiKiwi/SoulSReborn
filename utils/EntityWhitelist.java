package SoulSReborn.utils;

import net.minecraft.entity.EntityLiving;
import SoulSReborn.configs.SoulConfig;

public class EntityWhitelist 
{
	public static String[] peacefuls = {"Pig", "Chicken", "Cow", "Mooshroom", "Sheep", "Zombie Pigman", "Iron Golem", "Snow Golem"};
	public static String[] mobs = {"Zombie", "Creeper", "Skeleton", "Spider", "Cave Spider", "Enderman", "Slime", "Magma Cube", "Witch", "Blaze", "Ghast", "Wither Skeleton"};
	
	public static boolean isEntityAccepted(EntityLiving ent)
	{
		boolean result = false;
		result = cicle(peacefuls, ent);
		if (!result && !SoulConfig.disallowMobs)
			result = cicle(mobs, ent);
		return result;
		
	}
	
	private static boolean cicle(String[] strings, EntityLiving ent)
	{
		boolean result = false;
		for (int i = 0; i < strings.length; i++)
		{
			boolean isInBlacklist = SoulConfig.blacklistMap.get(strings[i]);
			if (ent.getEntityName().equals(strings[i]) && !isInBlacklist)
			{
				result = true;
				break;
			}
		}
		return result;
				
	}
}
