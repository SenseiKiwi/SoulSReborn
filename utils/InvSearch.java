package SoulSReborn.utils;

import java.util.logging.Level;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;

public class InvSearch 
{
	public static boolean hasItem(ItemStack stack, String username, String mobname)
	{
		boolean hasFound = false;
		EntityPlayerMP player = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(username);
		if (player != null)
		{
			ItemStack[] inv = player.inventory.mainInventory;
			for (int i = 0; i <= 8; i++)
			{
				if (inv[i] != null)
					if (inv[i].getItem() == stack.getItem() && !isMaxed(inv[i]))
						if (NBTHandler(inv[i], mobname))
							hasFound = true;
			}
		}
		else
			SoulLogger.log(Level.SEVERE, "Player returned NULL! Please report to dev!");
		if (hasFound)
			return true;
		else return false;
	}
	
	public static boolean hasItem(Item item, String username, String mobname)
	{
		boolean hasFound = false;
		EntityPlayerMP player = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(username);
		if (player != null)
		{
			ItemStack[] inv = player.inventory.mainInventory;
			for (int i = 0; i <= 8; i++)
			{
				if (inv[i] != null)
					if (inv[i].getItem() == item && !isMaxed(inv[i]))
						if (!inv[i].hasTagCompound() || NBTHandler(inv[i], mobname))
							hasFound = true;
			}
		}
		else
			SoulLogger.log(Level.SEVERE, "Player returned NULL! Please report to dev!");
		if (hasFound)
			return true;
		else return false;
	}
	
	public static ItemStack invItemStack(Item item, String username, String mobName)
	{
		EntityPlayer player = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(username);
		ItemStack stack = null;
		boolean hasFound = false;
		if (player != null)
		{
			ItemStack[] inv = player.inventory.mainInventory;
			for (int i = 0; i <= 8; i++)
			{
				if (inv[i] != null)
						if (inv[i].getItem() == item && !isMaxed(inv[i]))
							if (NBTHandler(inv[i], mobName))
							{
								stack = inv[i];
								hasFound = true;
								break;
							}
			}
			if (!hasFound)
				for (int i = 0; i <= 8; i++)
				{
					if (inv[i] != null)
						if (inv[i].getItem() == item && !isMaxed(inv[i]) && !inv[i].hasTagCompound())
						{
							stack = inv[i];
							break;
						}
				}
		}
		else
			SoulLogger.log(Level.SEVERE, "Player returned NULL! Please report to dev!");
		return stack;
	}
	
	public static ItemStack heldItem(String username)
	{
		ItemStack stack = null;
		EntityPlayerMP player = MinecraftServer.getServer().getConfigurationManager().getPlayerForUsername(username);
		if (player != null)
			stack = player.getHeldItem();
		else
			SoulLogger.log(Level.SEVERE, "Player returned NULL! Please report to dev!");
		return stack;
	}
	
	private static boolean isMaxed(ItemStack stack)
	{
		if (stack.hasTagCompound() && stack.stackTagCompound.getInteger("Tier") == 5 && !stack.stackTagCompound.getString("EntityType").equals("empty"))
			return true;
		else return false;
	}
	
	private static boolean NBTHandler(ItemStack stack, String mobname)
	{
		if (stack.hasTagCompound())
		{
			String tag = stack.stackTagCompound.getString("EntityType");
			if (tag.equals("empty"))
				return true;
			else if (mobname.equals("Skeleton") && tag.equals("Wither Skeleton"))
				return true;
			else if (tag.equals(mobname))
				return true;
			else return false;
		}
		else return false;
	}
}
