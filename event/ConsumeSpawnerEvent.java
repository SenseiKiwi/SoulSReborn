package SoulSReborn.event;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntitySkeleton;
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

public class ConsumeSpawnerEvent 
{
	@ForgeSubscribe
	public void InteractEvent(PlayerInteractEvent event)
	{
		EntityPlayer player = event.entityPlayer;
		World world = player.worldObj;
		
		if (!world.isRemote && SoulConfig.canAbsorbSpawners && player != null)
			if(player.getHeldItem() != null && player.getHeldItem().getItem() == ObjHandler.soulShard)
			{
				ItemStack stack = player.getHeldItem();
				if (world.getBlockId(event.x, event.y, event.z) == Block.mobSpawner.blockID)
				{
					TileEntityMobSpawner spawner = (TileEntityMobSpawner) world.getBlockTileEntity(event.x, event.y, event.z); 
					if (spawner != null)
					{
						String ent = spawner.getSpawnerLogic().getEntityNameToSpawn();
						EntityLiving entity = getEntity(ent, world);
						if (EntityWhitelist.isEntityAccepted(entity))
						{
							if (!stack.hasTagCompound())
							{
								stack.setTagCompound(new NBTTagCompound());
								stack.stackTagCompound.setString("EntityType", "empty");
							}
							
							if ((stack.hasTagCompound() && stack.stackTagCompound.getString("EntityType").equals("empty")))
							{  
								stack.stackTagCompound.setString("EntityType", entity.getEntityName());
                                stack.stackTagCompound.setString("entId", (String)EntityList.classToStringMapping.get(entity.getClass()));
							}
							
							if (stack.hasTagCompound() && !stack.stackTagCompound.getString("EntityType").equals("empty"))
								if (!TierHandling(stack, entity.getEntityName()))
									world.setBlockToAir(event.x, event.y, event.z);
						}
					}
				}
			}
	}
	
	private EntityLiving getEntity(String string, World world)
	{
		EntityLiving result;
		if (string.equals("Wither Skeleton"))
		{
			EntitySkeleton skele = new EntitySkeleton(world);
			skele.setSkeletonType(1);
			result = skele;
		}
		else	
			result = (EntityLiving) EntityList.createEntityByName(string, world);
		return result;
	}
	
	private boolean TierHandling(ItemStack stack, String entName)
	{
		boolean flag = false;
		String stackEntName = stack.stackTagCompound.getString("EntityType");
		int kills = stack.stackTagCompound.getInteger("KillCount");
		int damage = 0;
		
		if (!stackEntName.equals(entName) || (!stackEntName.equals("empty") && kills == 1024))
			flag = true;
		else
		{
			kills += 200;
			if (kills > 1024)
				kills = 1024;
			stack.stackTagCompound.setInteger("KillCount", kills);
		}
		return flag;
	}
}
