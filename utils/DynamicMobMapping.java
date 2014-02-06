package SoulSReborn.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.StatCollector;

public class DynamicMobMapping 
{
	public static List<String> entityList = new ArrayList();
	private static List<String> entBlackList = new ArrayList();
	
	public static void init()
	{
		LoadBlacklist();
		FillList();
	}
	
	private static void FillList()
	{
		Map<String, Class> map = EntityList.stringToClassMapping;
		Iterator<String> iter = map.keySet().iterator();
		while (iter.hasNext())
		{
			String name = iter.next();
			Class c = map.get(name);
			if (EntityLivingBase.class.isAssignableFrom(c) && !name.equals("Mob") && !name.contains("dragon"))
			{
				String translation = StatCollector.translateToLocal("entity." + name + ".name");
				if (!translation.contains("entity.") && !entBlackList.contains(translation))
					entityList.add(translation);
			}
		}
		entityList.add("Wither Skeleton");
	}
	
	private static void LoadBlacklist()
	{
		entBlackList.add("Bat");
		entBlackList.add("Ender Dragon");
		entBlackList.add("Giant");
		entBlackList.add("Monster");
		entBlackList.add("Ocelot");
		entBlackList.add("Wolf");
		entBlackList.add("Wither");
	}
}
