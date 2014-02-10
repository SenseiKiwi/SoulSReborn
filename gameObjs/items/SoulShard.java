package SoulSReborn.gameObjs.items;

import java.util.List;
import java.util.logging.Level;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import SoulSReborn.gameObjs.ObjHandler;
import SoulSReborn.utils.SoulLogger;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SoulShard extends Item
{
	@SideOnly(Side.CLIENT)
	public static Icon[] icons;
	public static String[] iName = {"unboundSS", "tier0", "tier1", "tier2", "tier3", "tier4", "tier5"};
	
	public SoulShard(int id)
	{
		super(id);
		this.setMaxStackSize(1);
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
		this.setCreativeTab(ObjHandler.soulTab);
		this.setNoRepair();
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5) 
	{
		if (!world.isRemote && stack.hasTagCompound())
		{
			boolean change = false;
			int kills = stack.stackTagCompound.getInteger("KillCount");
			int tier = stack.stackTagCompound.getInteger("Tier");
			int damage = 0;
			
			if (kills < 64 && damage != 6)
			{
				tier = 0; 
				damage = 6;
				change = true;
			}
			
			if (kills >= 64 && kills < 128 && tier != 1)
			{
				tier = 1;
				damage = 5;
				change = true;
			}
			if (kills >= 128 && kills < 256 && tier != 2)
			{	
				tier = 2;
				damage = 4;
				change = true;
			}
			if (kills >= 256 && kills < 512 && tier != 3)
			{
				tier = 3;
				damage = 3;
				change = true;
			}
			if (kills >= 512 && kills < 1024 && tier != 4)
			{	
				tier = 4;
				damage = 2;
				change = true;
			}
			if (kills == 1024 && tier != 5)
			{
				tier = 5;
				damage = 1;
				change = true;
			}
			
			if (change)
			{
				stack.stackTagCompound.setInteger("Tier", tier);
				stack.setItemDamage(damage);
			}
		}
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) 
	{
		return iName[stack.getItemDamage()];
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{
		
		return stack;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack)
	{
		return  stack.getItemDamage() == 1;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister icon) 
	{
		icons = new Icon[iName.length];
		for(int i = 0; i < icons.length; i++) 
			icons[i] = icon.registerIcon("ssr" + ":" + iName[i]);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int damage) 
	{
		return icons[damage];
	}
	
	@Override
	public boolean getIsRepairable(ItemStack stack1, ItemStack stack2)
    {
		return false;
    }
	
	@Override
	public void getSubItems(int id, CreativeTabs tab, List list) 
	{
		for(int i = 0; i < icons.length; i++) 
		{
			ItemStack stack = new ItemStack(id, 1, i);
			if (i!= 0 && !stack.hasTagCompound())
			{
				stack.setTagCompound(new NBTTagCompound());
				stack.stackTagCompound.setString("EntityType", "empty");
				switch (i)
				{
						
					case 6:
						stack.stackTagCompound.setInteger("KillCount", 0);
						stack.stackTagCompound.setInteger("Tier", 0);
						break;
					case 5:
						stack.stackTagCompound.setInteger("KillCount", 64);
						stack.stackTagCompound.setInteger("Tier", 1);
						break;
					case 4:
						stack.stackTagCompound.setInteger("KillCount", 128);
						stack.stackTagCompound.setInteger("Tier", 2);
						break;
					case 3:
						stack.stackTagCompound.setInteger("KillCount", 256);
						stack.stackTagCompound.setInteger("Tier", 3);
						break;
					case 2:
						stack.stackTagCompound.setInteger("KillCount", 512);
						stack.stackTagCompound.setInteger("Tier", 4);
						break;
					case 1:
						stack.stackTagCompound.setInteger("KillCount", 1024);
						stack.stackTagCompound.setInteger("Tier", 5);
					break;
					default:
						SoulLogger.log(Level.SEVERE, "Urgently Report to Dev. Error at getSubItems() in" +this.getClass());
				}
			}
			list.add(stack);
		}
	}
	
}
