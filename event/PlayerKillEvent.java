package SoulSReborn.event;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import SoulSReborn.gameObjs.ObjHandler;
import SoulSReborn.utils.EntityWhitelist;
import SoulSReborn.utils.InvSearch;
import SoulSReborn.utils.TierHandling;

public class PlayerKillEvent 
{
	@ForgeSubscribe
	public void playerKillEvent(LivingDeathEvent event)
	{
		if (event.source.getEntity() instanceof EntityPlayer && event.entityLiving instanceof EntityLiving) 
		{
			EntityLiving ent = (EntityLiving) event.entityLiving;
			EntityPlayer player = (EntityPlayer) event.source.getEntity();
			
			if (EntityWhitelist.isEntityAccepted(ent) && !ent.getEntityData().getBoolean("fromSSR"))
			{
				String mobName = ent.getEntityName();
				String mobId = EntityList.getEntityString(ent);
				
				if (mobName.equals("Skeleton") && ent instanceof EntitySkeleton)
				{
					EntitySkeleton skele = (EntitySkeleton)ent;
					if (skele.getSkeletonType() == 1)
						mobName = "Wither Skeleton";
				}
				
				if (InvSearch.hasItem(ObjHandler.soulShard, player.username, mobName))
				{
					ItemStack stack = InvSearch.invItemStack(ObjHandler.soulShard, player.username, mobName);
					if (stack != null && !stack.hasTagCompound())
					{
						stack.setTagCompound(new NBTTagCompound());
						stack.stackTagCompound.setString("EntityType", "empty");
						stack.stackTagCompound.setInteger("EntityID", 0);
						stack.stackTagCompound.setInteger("KillCount", 0);
						stack.stackTagCompound.setInteger("Tier", 0);
						stack.stackTagCompound.setString("entId", "empty");
					}
					
					if (stack.stackTagCompound.getString("EntityType").equals("empty"))
					{
						stack.stackTagCompound.setString("EntityType", mobName);
						stack.stackTagCompound.setString("entId", mobId);
						ItemStack heldItem = ent.getCurrentItemOrArmor(0);
						if ((!mobName.equals("Zombie") || !mobName.equals("Enderman")) && heldItem != null)
						{
							stack.stackTagCompound.setBoolean("HasItem", true);
							NBTTagCompound nbt2 = new NBTTagCompound();
							heldItem.writeToNBT(nbt2);
							stack.stackTagCompound.setTag("Item", nbt2);
						}
					}
					
					String name = stack.stackTagCompound.getString("EntityType");
					int kills = stack.stackTagCompound.getInteger("KillCount");
					
					if (mobName.equals(name) && kills < 1024)
					{
						ItemStack heldItem = player.getHeldItem();
						if (heldItem != null && SoulBonus(heldItem) != 0)
						{
							kills += SoulBonus(heldItem) + 1;
							kills = kills > TierHandling.getMax(5) ? TierHandling.getMax(5) : kills;
						}
						else
							kills += 1;
						stack.stackTagCompound.setInteger("KillCount", kills);
					}
				}
			}
		}
	}
	
	private int SoulBonus(ItemStack stack)
	{
		return EnchantmentHelper.getEnchantmentLevel(ObjHandler.soulStealer.effectId, stack);
	}
}
