package SoulSReborn.creativeTab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import SoulSReborn.gameObjs.ObjHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class SoulTab extends CreativeTabs 
{

	public SoulTab(String label) 
	{
		super(label);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public ItemStack getIconItemStack() 
	{
		return new ItemStack(ObjHandler.soulShard, 1, 6);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public int getTabIconItemIndex()
    {
        return 0;
    }
}
