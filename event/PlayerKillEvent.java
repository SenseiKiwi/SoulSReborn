package SoulSReborn.event;

import java.util.logging.Level;

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
import SoulSReborn.utils.SoulLogger;

public class PlayerKillEvent 
{
	@ForgeSubscribe
	public void playerKillEvent(LivingDeathEvent event)
	{	
		if (event.source.getEntity() instanceof EntityPlayer && event.entityLiving instanceof EntityLiving) 
		{
			EntityLiving ent = (EntityLiving) event.entityLiving;
			if (EntityWhitelist.isEntityAccepted(ent) && ent.getEntityData().getBoolean("fromSSR") != true)
			{
				String username = event.source.getEntity().getEntityName();
				String mobName = ent.getEntityName();
				
				if (mobName.equals("Skeleton"))
				{
					EntitySkeleton skele = (EntitySkeleton)ent;
					if (skele.getSkeletonType() == 1)
						mobName = "Wither Skeleton";
				}
				
				if (InvSearch.hasItem(ObjHandler.soulShard, username, mobName))
				{
					ItemStack stack = InvSearch.invItemStack(ObjHandler.soulShard, username, mobName);
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
						stack.stackTagCompound.setString("entId", (String)EntityList.classToStringMapping.get(ent.getClass()));
					}
					
					String name = stack.stackTagCompound.getString("EntityType");
					int kills = stack.stackTagCompound.getInteger("KillCount");
					
					if (mobName.equals(name) && kills < 1024)
						{
							ItemStack heldItem = InvSearch.heldItem(username);
							if (heldItem != null && SoulBonus(heldItem) != 0)
							{
								kills += SoulBonus(InvSearch.heldItem(username));
								if (kills > 1024)
								kills = 1024;
							}
							else
								kills += 1;
							stack.stackTagCompound.setInteger("KillCount", kills);
							TierHandler(stack);
						}
				}
			}
		}
	}
	
	private int SoulBonus(ItemStack stack)
	{
		return EnchantmentHelper.getEnchantmentLevel(ObjHandler.soulStealer.effectId, stack);
	}
	
	private void TierHandler(ItemStack stack)
	{
		int kills = stack.stackTagCompound.getInteger("KillCount");
		int tier = stack.stackTagCompound.getInteger("Tier");
		if (kills > 0 && kills < 64)
			stack.setItemDamage(6);
		else if (kills >= 64 && kills < 128)
		{
			stack.stackTagCompound.setInteger("Tier", 1);
			stack.setItemDamage(5);
		}
		else if (kills >= 128 && kills < 256)
		{
			stack.stackTagCompound.setInteger("Tier", 2);
			stack.setItemDamage(4);
		}
		else if (kills >= 256 && kills < 512)
		{
			stack.stackTagCompound.setInteger("Tier", 3);
			stack.setItemDamage(3);
		}
		else if (kills >= 512 && kills < 1024)
		{
			stack.stackTagCompound.setInteger("Tier", 4);
			stack.setItemDamage(2);
		}
		else if (kills == 1024)
		{
			stack.stackTagCompound.setInteger("Tier", 5);
			stack.setItemDamage(1);
		}
		else
			SoulLogger.log(Level.SEVERE, "How did you break this?? Error at TierHandler in " +this.getClass());		
	}
}
