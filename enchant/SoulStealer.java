package SoulSReborn.enchant;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.item.ItemStack;

public class SoulStealer extends Enchantment
{
	
	
	public SoulStealer(int id, int weight, EnumEnchantmentType type) 
	{
		super(id, weight, type);
	}
	
	@Override
	public int getMinEnchantability(int par1)
	{
		return (par1 - 1) * 11;
	}
	
	@Override
	public int getMaxEnchantability(int par1)
	{
		return this.getMinEnchantability(par1) + 20;
	}
	
	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack)
	{
		return true;
	}
	
	@Override
	public int getMaxLevel()
    {
        return 5;
    }
	
	@Override
	public Enchantment setName(String par1Str)
	{
		this.name = par1Str;
	    return this;
	}
	
	@Override
	public String getName()
	{
		return this.name;
	}

}
