package SoulSReborn.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.StatCollector;

import org.apache.commons.lang3.StringUtils;

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
				if (!translation.contains("entity.") && !translation.contains(".name") && !entBlackList.contains(translation))
					entityList.add(translation);
				else if (!entBlackList.contains(translation))
				{
					translation = Normalize(translation);
					if (!translation.equals("nope"))
						entityList.add(translation);
				}
			}
		}
		entityList.add("Wither Skeleton");
	}
	
	private static String Normalize(String string)
	{
		String result = "nope";
		String subString = string;
		int index;
		int dots = StringUtils.countMatches(string, ".");
		if (dots != 0)
		{
			for (int i = 0; i < dots - 1; i++)
			{
				index = subString.indexOf('.');
				subString = subString.substring(index + 1);
			}
			index = subString.indexOf('.');
			subString = subString.substring(0, index);
			if (!subString.contains("entity.") && !subString.contains(".name"))
				result = subString;
		}
		return result;
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
