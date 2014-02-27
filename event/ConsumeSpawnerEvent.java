package SoulSReborn.event;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import SoulSReborn.configs.SoulConfig;
import SoulSReborn.gameObjs.ObjHandler;
import SoulSReborn.utils.EntityWhitelist;
import SoulSReborn.utils.TierHandling;

public class ConsumeSpawnerEvent 
{
	@ForgeSubscribe
	public void InteractEvent(PlayerInteractEvent event)
	{
		EntityPlayer player = event.entityPlayer;
		World world = player.worldObj;
		
		if (!world.isRemote && SoulConfig.canAbsorbSpawners && player != null)
		{
			if(player.getHeldItem() != null && player.getHeldItem().getItem() == ObjHandler.soulShard)
			{
				ItemStack stack = player.getHeldItem();
				if (world.getBlockId(event.x, event.y, event.z) == Block.mobSpawner.blockID)
				{
					TileEntityMobSpawner spawner = (TileEntityMobSpawner) world.getBlockTileEntity(event.x, event.y, event.z); 
					if (spawner != null)
					{
						String entId = spawner.getSpawnerLogic().getEntityNameToSpawn();
						Entity entity = EntityList.createEntityByName(entId, world);
						spawner.getSpawnerLogic().func_98265_a(entity);
						
						if (EntityWhitelist.isEntityAccepted(entity))
						{
							if (!stack.hasTagCompound())
							{
								stack.setTagCompound(new NBTTagCompound());
								stack.stackTagCompound.setString("EntityType", "empty");
							}
							
							NBTTagCompound nbt = stack.stackTagCompound;
							String nbtName = nbt.getString("EntityType");
							int kills = nbt.getInteger("KillCount");
							
							if (nbtName.equals("empty") || nbtName.equals(entity.getEntityName()) && kills < TierHandling.getMax(5))
							{
								kills += 200;
								if (kills > 1024)
									kills = 1024;
								nbt.setInteger("KillCount", kills);
								if (nbtName.equals("empty"))
								{
									nbt.setString("EntityType", entity.getEntityName());
	                                nbt.setString("entId", entId);
	                                EntityLiving entLiv = (EntityLiving)entity;
	                                ItemStack heldItem = entLiv.getCurrentItemOrArmor(0);
	                                if (heldItem != null)
	                                {
	                                	nbt.setBoolean("HasItem", true);
	                                	NBTTagCompound nbt2 = new NBTTagCompound();
	                                	heldItem.writeToNBT(nbt2);
	                                	nbt.setTag("Item", nbt2);
	                                }
								}
								world.setBlockToAir(event.x, event.y, event.z);
							}
						}
					}
				}
			}
		}
	}
}
