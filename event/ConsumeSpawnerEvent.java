package SoulSReborn.event;

import java.util.logging.Level;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import SoulSReborn.gameObjs.ObjHandler;
import SoulSReborn.utils.EntityWhitelist;
import SoulSReborn.utils.SoulLogger;

public class ConsumeSpawnerEvent 
{
	@ForgeSubscribe
	public void InteractEvent(PlayerInteractEvent event)
	{
		EntityPlayer player = event.entityPlayer;
		World world = player.worldObj;
		
		if (!world.isRemote && player != null)
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
								stack.stackTagCompound.setString("EntityType", ent);
								stack.stackTagCompound.setString("entId", ent);
								stack.stackTagCompound.setInteger("KillCount", 64);
								stack.stackTagCompound.setInteger("Tier", 1);
								stack.setItemDamage(5);
								world.setBlockToAir(event.x, event.y, event.z);
							}
							else
							{
								if (!TierHandling(stack, ent))
									world.setBlockToAir(event.x, event.y, event.z);
							}
						}
					}
				}
			}
	}
	
	private EntityLiving getEntity(String string, World world)
	{
		EntityLiving result;
		if (string.equals("Snow Golem"))
			result = new EntitySnowman(world);
		else if (string.equals("Iron Golem"))
			result = new EntityIronGolem(world);
		else if (string.equals("Magma Cube"))
			result = new EntityMagmaCube(world);
		else	
			result = (EntityLiving) EntityList.createEntityByName(string, world);
		return result;
	}
	
	private boolean TierHandling(ItemStack stack, String entName)
	{
		boolean flag = false;
		int kills = 0;
		int tier = stack.stackTagCompound.getInteger("Tier");
		switch(tier)
		{
			case 0:
				kills = 64;
				tier = 1;
				break;
			case 1:
				kills = 128;
				tier = 2;
				break;
			case 2:
				kills = 256;
				tier = 3;
				break;
			case 3:
				kills = 512;
				tier = 4;
				break;
			case 4:
				kills = 1024;
				tier = 5;
				break;
			case 5:
				flag = true;
				break;
			default:
				SoulLogger.log(Level.INFO, "I don't even know how this happened. Please report to dev ASAP.");
		}
		if (!stack.stackTagCompound.getString("EntityType").equals(entName) && !flag)
			flag = true;
		if (!flag)
		{
			if (stack.stackTagCompound.getString("EntityType").equals("empty"))
				stack.stackTagCompound.setString("EntityType", entName);
			stack.stackTagCompound.setInteger("KillCount", kills);
			stack.stackTagCompound.setInteger("Tier", tier);
			stack.setItemDamage(stack.getItemDamage() - 1);
		}
		return flag;
	}
}
