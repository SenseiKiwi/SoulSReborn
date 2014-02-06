package SoulSReborn.enchant;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;

public class SoulStealer extends Enchantment
{
	
	
	public SoulStealer(int id, int weight) 
	{
		super(id, weight, EnumEnchantmentType.weapon);
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
