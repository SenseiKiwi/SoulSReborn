package SoulSReborn.event;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import SoulSReborn.gameObjs.ObjHandler;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

public class ToolTipEvent 
{
	@ForgeSubscribe
	@SideOnly(Side.CLIENT)
	public void itemToolTipEvent(ItemTooltipEvent event)
	{
		ItemStack stack = event.itemStack;
		if (stack.getItem() == ObjHandler.soulShard)
		{
			if (stack.hasTagCompound())
			{
				String ent = stack.stackTagCompound.getString("EntityType");
				int kills = stack.stackTagCompound.getInteger("KillCount");
				int tier = stack.stackTagCompound.getInteger("Tier");
				if (!ent.equals("empty"))
					event.toolTip.add("Bound to: "+ent);
				event.toolTip.add("Kills: "+kills);
				event.toolTip.add("Tier: " +tier);
			}
			else
				event.toolTip.add("Kill a creature to trap its soul.");
		}
	}
}
