package SoulSReborn.utils;

import net.minecraft.entity.EntityLiving;
import SoulSReborn.configs.MobBlacklist;
import SoulSReborn.configs.SoulConfig;

public class EntityWhitelist 
{
	public static String[] peacefuls = {"Pig", "Chicken", "Cow", "Mooshroom", "Sheep", "Zombie Pigman", "Iron Golem", "Snow Golem", "Villager", "Squid"};
	public static String[] mobs = {"Zombie", "Creeper", "Skeleton", "Spider", "Cave Spider", "Enderman", "Slime", "Magma Cube", "Witch", "Blaze", "Ghast", "Wither Skeleton"};
	
	public static boolean isEntityAccepted(EntityLiving ent)
	{
		boolean result = false;
		
		if (!SoulConfig.allowModMobs)
		{
			result = cicle(peacefuls, ent);
			if (!result && !SoulConfig.disallowMobs)
				result = cicle(mobs, ent);
		}
		else
		{
			if (DynamicMobMapping.entityList.contains(ent.getEntityName()) && !MobBlacklist.map.get(ent.getEntityName()))			
				result = true;
		}
		return result;
	}
	
	private static boolean cicle(String[] strings, EntityLiving ent)
	{
		boolean result = false;
		for (int i = 0; i < strings.length; i++)
		{
			boolean isInBlacklist = MobBlacklist.map.get(strings[i]);
			if (ent.getEntityName().equals(strings[i]) && !isInBlacklist)
			{
				result = true;
				break;
			}
		}
		return result;				
	}
}
