package SoulSReborn.gameObjs;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.logging.Level;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import SoulSReborn.configs.SoulConfig;
import SoulSReborn.utils.SoulLogger;

public class CageTile extends TileEntity
{
	int maxMobs = 0;
	int timer = 0;
	int timer2 = 0;
	int timerEnd = 0;
	public int tier = 0;
	public String entName = "empty";
	public String entId = "empty";
	String heldItem = "empty";
	public boolean isPowered = false;
	boolean flag = true;
	
	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setInteger("Tier", tier);
		nbt.setString("Entity", entName);
		nbt.setBoolean("Power", isPowered);
		nbt.setBoolean("Flag", flag);
		nbt.setString("entId", entId);
		nbt.setString("HeldItem", heldItem);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		tier = nbt.getInteger("Tier");
		entName = nbt.getString("Entity");
		isPowered = nbt.getBoolean("Power");
		flag = nbt.getBoolean("Flag");
		entId = nbt.getString("entId");
		heldItem = nbt.getString("HeldItem");
	}
	
	@Override
	public void updateEntity()
	{
		if (tier != 0)
		{
			if (maxMobs == 0)
				tierHandler();
			
			int metadata = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
			
			if (timer == 20)
			{
				flag = true;
				timer = 0;
				EntityLiving tempEnt = null;
				if (entName.equals("Wither Skeleton"))
				{
					EntitySkeleton skele = new EntitySkeleton(worldObj);
					skele.setSkeletonType(1);
					tempEnt = skele;
				}
				else
					tempEnt = (EntityLiving) EntityList.createEntityByName(entId, worldObj);
				
				if (tempEnt != null)
				{
					if (SoulConfig.maxNumSpawns == 0 || !hasReachedSpawnLimit(tempEnt))
					{
						if (tier == 1 || tier == 2)
						{
							if (isPlayerClose(xCoord, yCoord, zCoord) && canSpawnInLight(tempEnt, xCoord, yCoord, zCoord) && canSpawnInWorld(tempEnt))
								flag = false;
						}
						else if (tier == 3)
						{
							if (canSpawnInLight(tempEnt, xCoord, yCoord, zCoord) && canSpawnInWorld(tempEnt))
								flag = false;
						}
						else if (tier == 4)
						{
							flag = false;
						}
						else if (tier == 5)
						{
							if (isPowered)
								flag = false;
						}
					}
				}else SoulLogger.log(Level.INFO, "Entity returned null. Please report to dev!");
				
				if (!flag && metadata == 1)
				{
					worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 2, 2);

				}
				else if (flag && metadata == 2)
				{
					worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 1, 2);
				}
			}
			else
				timer += 1;
			
			if (timer2 == timerEnd)
			{
				timer2 = 0;
				
				if (!flag)
				{
					EntityLiving[] entity = new EntityLiving[maxMobs];
					
                    for (int i = 0; i < entity.length; i++)
                    {
                            if (entName.equals("Wither Skeleton"))
                            {
                                    EntitySkeleton skele = new EntitySkeleton(worldObj);
                                    skele.setSkeletonType(1);
                                    entity[i] = skele;
                            }
                            else
                            	entity[i] = (EntityLiving) EntityList.createEntityByName(entId, worldObj);
                    }
                    
					SpawnAlgo(entity);
					
					for (int i = 0; i < entity.length; i++)
						if (!entity[i].isDead)
						{
							if (entity[i] instanceof EntitySlime)
							{
								try
								{
									Class <?> c = EntitySlime.class; 
									Method method = c.getDeclaredMethod("setSlimeSize", int.class);
									method.setAccessible(true);
									method.invoke(entity[i], 1);
								}
								catch (Exception e) {SoulLogger.log(Level.INFO, "NSM Exception in slime handling. Please report to dev! " +e.getCause());}
							}
							entity[i].getEntityData().setBoolean("fromSSR", true);
							entity[i].forceSpawn = true;
							entity[i].func_110163_bv();
							if (entName.equals("Skeleton"))
								entity[i].setCurrentItemOrArmor(0, new ItemStack(Item.bow));
							else if (entName.equals("Wither Skeleton"))
								entity[i].setCurrentItemOrArmor(0, new ItemStack(Item.swordStone));
							else if (entName.equals("Zombie Pigman"))
								entity[i].setCurrentItemOrArmor(0, new ItemStack(Item.swordGold));
							worldObj.spawnEntityInWorld(entity[i]);
						}
				}
			}
			else
				timer2 += 1;
		}
	}
		
	private void tierHandler()
	{
		switch (tier)
		{
			case 1:
				maxMobs = SoulConfig.numMobs[0];
				timerEnd = SecToTick(SoulConfig.coolDown[0]);
				break;
			case 2:
				maxMobs = SoulConfig.numMobs[1];
				timerEnd = SecToTick(SoulConfig.coolDown[1]);
				break;
			case 3:
				maxMobs = SoulConfig.numMobs[2];
				timerEnd = SecToTick(SoulConfig.coolDown[2]);
				break;
			case 4:
				maxMobs = SoulConfig.numMobs[3];
				timerEnd = SecToTick(SoulConfig.coolDown[3]);
				break;
			case 5:
				maxMobs = SoulConfig.numMobs[4];
				timerEnd = SecToTick(SoulConfig.coolDown[4]);
				break;
		}	
	}
	
	private boolean isPlayerClose(int x, int y, int z)
	{
		return (worldObj.getClosestPlayer(x, y, z, 16) != null);
	}
	
	private boolean canSpawnInLight(EntityLiving ent, int x, int y, int z)
	{	
		if (ent instanceof EntityCreature || ent instanceof EntityAnimal || ent instanceof EntitySlime || ent instanceof EntityGhast || ent instanceof EntityPigZombie)
			return true;
		else if (ent instanceof EntityMob)
		{
			float light = worldObj.getChunkFromBlockCoords(x >> 4, z >> 4).getBlockLightValue(x & 0xF, y, z & 0xF, worldObj.skylightSubtracted);
			if (light <= 8)
				return true;
			else
				return false;
		}
		else return false;	
	}
	
	private boolean canSpawnInWorld(EntityLiving ent)
	{
		int dimension = worldObj.provider.dimensionId; //-1 Nether, 0 OW, 1 End
		
		if (ent instanceof EntitySkeleton)
		{
			EntitySkeleton skele = (EntitySkeleton)ent;
			if (skele.getSkeletonType() == 1 && dimension == -1)
				return true;
			else if (skele.getSkeletonType() == 0 && dimension == 0)
				return false;
			else return false;
		}		
		else if (ent instanceof EntityAnimal || ent instanceof EntityCreature)
		{
			if (dimension == 0)
				return true;
			else return false;
		}
		else if (ent instanceof EntityBlaze || ent instanceof EntityPigZombie || ent instanceof EntityGhast || ent instanceof EntityMagmaCube)
		{
			if (dimension == -1)
				return true;
			else return false;
		}
		else if (ent instanceof EntityEnderman)
		{
			if (dimension == 1)
				return true;
			else return false;
		}
		else if (ent instanceof EntityMob || ent instanceof EntitySlime)
		{
			if (dimension == 0)
				return true;
			else return false;
		}
		else return false;	
	}
	
	private boolean canSpawnAtCoords(EntityLiving ent)
	{
		return (worldObj.getCollidingBoundingBoxes(ent, ent.boundingBox).isEmpty() && !worldObj.isAnyLiquid(ent.boundingBox));
	}
	
	private boolean hasReachedSpawnLimit(EntityLiving ent)
	{
		AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(xCoord - 16, yCoord - 16, zCoord - 16, xCoord + 16, yCoord + 16, zCoord + 16);
		Iterator <EntityLiving> entIter = worldObj.getEntitiesWithinAABB(ent.getClass(), aabb).iterator();
		int mobCount = 0;
		while(entIter.hasNext())
		{
			EntityLiving entity = entIter.next();
			if (entity.getEntityData().getBoolean("fromSSR"))
				mobCount += 1;
		}
		if (mobCount <= SoulConfig.maxNumSpawns)
			return false;
		else return true;	
	}

	private int SecToTick(int seconds)
	{
		return seconds * 20;
	}
	
	private void SpawnAlgo(EntityLiving[] ents)
	{
		for (int i = 0; i < ents.length; i++)
		{
			int counter = 0;
			do
			{
				counter += 1;
				if (counter >= 10)
				{
					ents[i].setDead();
					break;
				}
				double x = xCoord + (worldObj.rand.nextDouble() - worldObj.rand.nextDouble()) * 4.0D;
				double y = yCoord + worldObj.rand.nextInt(3) - 1;
				double z = zCoord + (worldObj.rand.nextDouble() - worldObj.rand.nextDouble()) * 4.0D;
				ents[i].setPositionAndRotation(x, y, z, worldObj.rand.nextFloat() * 360.0F, 0.0F);
			}
			while (!canSpawnAtCoords(ents[i]) || counter == 5);
		}
	}
}
