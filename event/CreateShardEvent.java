package SoulSReborn.event;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import SoulSReborn.gameObjs.ObjHandler;

public class CreateShardEvent
{
	@ForgeSubscribe
	public void createShard(PlayerInteractEvent event)
	{
		World world = event.entityPlayer.worldObj;
		EntityPlayer player = event.entityPlayer;
		if (!world.isRemote && player != null)
		{
			int block;
			if (player.getHeldItem() != null && player.getHeldItem().getItem() == Item.diamond)
			{
				block = world.getBlockId(event.x, event.y, event.z);
				if (block == Block.glowStone.blockID && isFormed (world, event.x, event.y, event.z))
				{
					if (!player.capabilities.isCreativeMode)
						player.getHeldItem().stackSize -= 1;
					world.spawnEntityInWorld(new EntityItem(world, event.x, event.y, event.z, new ItemStack(ObjHandler.soulShard)));
					world.setBlockToAir(event.x, event.y, event.z);
				}
			}
		}
	}
	
	private boolean isFormed(World world, int x, int y, int z)
	{

		boolean result = true;
		int blocks1[] = {world.getBlockId(x + 1, y, z), world.getBlockId(x - 1, y, z), world.getBlockId(x, y, z + 1), world.getBlockId(x, y, z - 1)};
		for (int i = 0; i < blocks1.length - 1; i++)
		{
			int j = i + 1;
			if (blocks1[i] != blocks1[j] || blocks1[i] != Block.netherrack.blockID)
			{
				result = false;
				break;
			}
		}
		
		if (result)
		{
			int blocks2[] = {world.getBlockId(x + 2, y, z), world.getBlockId(x -2 , y, z), world.getBlockId(x, y, z + 2), world.getBlockId(x, y, z - 2), 
					world.getBlockId(x + 1, y, z + 1), world.getBlockId(x + 1, y, z - 1), world.getBlockId(x - 1, y, z + 1), world.getBlockId(x - 1, y, z - 1)};
			for (int i = 0; i < blocks2.length - 1; i++)
			{
				int j = i + 1;
				if (blocks2[i] != blocks2[j] || blocks2[i] != Block.whiteStone.blockID)
				{
					result = false;
					break;
				}
			}
		}
	
		return result;
	}
}
