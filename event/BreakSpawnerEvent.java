package SoulSReborn.event;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.world.BlockEvent;
import SoulSReborn.gameObjs.CageTile;
import SoulSReborn.gameObjs.ObjHandler;

public class BreakSpawnerEvent 
{
	@ForgeSubscribe
	public void BreakBlockEvent(BlockEvent.BreakEvent event)
	{
		World world = event.world;
		if (!world.isRemote)
		{
			int x = event.x;
			int y = event.y;
			int z = event.z;
			if (event.block.blockID == ObjHandler.soulCage.blockID)
			{
				CageTile tile = (CageTile) world.getBlockTileEntity(x, y, z);
				if (tile != null)
				{
					if (!tile.entName.equals("empty") && tile.tier > 0)
					{
						String ent = tile.entName;
						String entId = tile.entId;
						int kills = 0;
						int tier = tile.tier;
						int meta = 0;
						switch(tier)
						{
						case 1:
							meta = 5;
							kills = 64;
							break;
						case 2:
							meta = 4;
							kills = 128;
							break;
						case 3:
							meta = 3;
							kills = 256;
							break;
						case 4:
							meta = 2;
							kills = 512;
							break;
						case 5:
							meta = 1;
							kills = 1024;
							break;
						}
						ItemStack stack = new ItemStack(ObjHandler.soulShard, 1, meta);
						if (!stack.hasTagCompound())
						{
							stack.setTagCompound(new NBTTagCompound());
							stack.stackTagCompound.setInteger("KillCount", kills);
							stack.stackTagCompound.setInteger("Tier", tier);
							stack.stackTagCompound.setString("EntityType", ent);
							stack.stackTagCompound.setString("entId", entId);
						}
						world.spawnEntityInWorld(new EntityItem(world, x, y, z, stack));
					}
				}
			}
		}
	}
}
