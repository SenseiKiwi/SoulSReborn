package SoulSReborn.utils;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class DynamicIds 
{
	private static Item[] item = Item.itemsList;
	private static Block[] block = Block.blocksList;
	
	public static int Items()
	{
		int result = 0;
		for (int i = 4097; i < item.length; i++)
		{
			if (item[i] != null)
				continue;
			else 
			{
				result = i - 256;
				break;
			}
		}
		return result;
	}
	
	public static int Blocks()
	{
		int result = 0;
		for (int i = 1000; i < block.length; i++)
		{
			if (block[i] != null)
				continue;
			else
			{
				result = i;
				break;
			}
		}
		return result;
	}
}
