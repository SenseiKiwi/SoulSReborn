package SoulSReborn.creativeTab;

import net.minecraft.creativetab.CreativeTabs;
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
    public int getTabIconItemIndex()
    {
        return ObjHandler.soulShard.itemID;
    }
}
