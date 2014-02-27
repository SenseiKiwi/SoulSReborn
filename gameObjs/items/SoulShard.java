package SoulSReborn.gameObjs.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import SoulSReborn.gameObjs.ObjHandler;
import SoulSReborn.utils.TierHandling;
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
			int kills = stack.stackTagCompound.getInteger("KillCount");
			int tier = stack.stackTagCompound.getInteger("Tier");
			int damage = stack.getItemDamage();
			if (!TierHandling.isInBounds(tier, kills))
			{
				if (kills > TierHandling.getMax(5))
				{
					tier = 5;
					kills = TierHandling.getMax(5);
					stack.stackTagCompound.setInteger("KillCount", kills);
				}
				tier = TierHandling.updateTier(kills);
				damage = tier + 1;
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
		return  stack.getItemDamage() == 6;
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
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean bool) 
	{
		if (stack.hasTagCompound())
		{
			NBTTagCompound nbt = stack.stackTagCompound;
			String entity = nbt.getString("EntityType");
			int kills = nbt.getInteger("KillCount");
			int tier = nbt.getInteger("Tier");
			if (!entity.equals("empty"))
			{
				if (entity.endsWith(".name"))
					entity = StatCollector.translateToLocal(entity);
				list.add("Bound to: "+ entity);
			}
			list.add("Kills: "+ kills);
			list.add("Tier: " + tier);
		}
		else
			list.add("Kill a creature to trap its soul.");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public void getSubItems(int id, CreativeTabs cTab, List list)
    {
		for (int i = 0; i < iName.length; i++)
		{
			ItemStack stack = new ItemStack(this, 1, i);
			if (i != 0 && !stack.hasTagCompound())
			{
				stack.setTagCompound(new NBTTagCompound());
				stack.stackTagCompound.setString("EntityType", "empty");
				stack.stackTagCompound.setInteger("Tier", i - 1);
				stack.stackTagCompound.setInteger("KillCount", TierHandling.getMin(i - 1));
			}
			list.add(stack);
		}
    }
	
}
