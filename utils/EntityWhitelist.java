package SoulSReborn.utils;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import SoulSReborn.configs.MobBlacklist;
import SoulSReborn.configs.SoulConfig;

public class EntityWhitelist 
{
	public static List<String> peacefuls = new ArrayList();
	public static List<String> mobs = new ArrayList();
	
	public static void init()
	{
		peacefuls.add("Pig");
		peacefuls.add("Chicken");
		peacefuls.add("Cow");
		peacefuls.add("Mooshroom");
		peacefuls.add("Sheep");
		peacefuls.add("Zombie Pigman");
		peacefuls.add("Iron Golem");
		peacefuls.add("Snow Golem");
		peacefuls.add("Villager");
		peacefuls.add("Squid");
		mobs.add("Zombie");
		mobs.add("Creeper");
		mobs.add("Skeleton");
		mobs.add("Spider");
		mobs.add("Cave Spider");
		mobs.add("Enderman");
		mobs.add("Slime");
		mobs.add("Magma Cube");
		mobs.add("Witch");
		mobs.add("Blaze");
		mobs.add("Ghast");
		mobs.add("Wither Skeleton");
	}
	
	public static boolean isEntityAccepted(String entName)
	{
		boolean result = false;
		
		if (!SoulConfig.allowModMobs)
		{
			if (!MobBlacklist.list.contains(entName))
			{
				result = peacefuls.contains(entName);
				if (!result && !SoulConfig.disallowMobs)
					result = mobs.contains(entName);
			}
		}
		else
		{
			if (DynamicMobMapping.entityList.contains(entName) && !MobBlacklist.list.contains(entName))			
				result = true;
		}
		return result;
	}
	
	public static boolean isEntityAccepted(Entity ent)
	{
		boolean result = false;
		String name = EntityList.getEntityString(ent);

		if (!SoulConfig.allowModMobs)
		{
			if (!MobBlacklist.list.contains(name))
			{
				result = peacefuls.contains(name);
				if (!result && !SoulConfig.disallowMobs)
					result = mobs.contains(name);
			}
		}
		else
		{
			if (DynamicMobMapping.entityList.contains(name) && !MobBlacklist.list.contains(name))			
				result = true;
		}
		return result;
	}
}